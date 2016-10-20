package larsworks.datetool.image;

import larsworks.datetool.configuration.ImageSize;
import larsworks.datetool.tasks.CopyTask;
import larsworks.datetool.tasks.ImageResizerTask;
import larsworks.datetool.tasks.LoadImageTask;
import org.apache.log4j.Logger;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.eclipse.swt.graphics.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * TODO: add a scheduled executor which removes finished future tasks from the futures list
 */
public class DTJpegImage implements JpegImage {

    private static final Logger logger = Logger.getLogger(DTJpegImage.class);
    private final File file;
    private final Calendar originalDate;
    private Calendar creationDate;

    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    private final List<Future<?>> futures = new ArrayList<Future<?>>();

    public DTJpegImage(Calendar creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("creationDate == null");
        } else {
            this.creationDate = creationDate;
            this.originalDate = creationDate;
            file = null;
        }
    }

    /**
     * creates a new image and reads the creation date from the exif header
     *
     * @param path the path to the file to read from
     * @throws FileNotFoundException    if file does not exist
     * @throws IllegalArgumentException if date cannot be parsed from file
     */
    public DTJpegImage(String path) throws FileNotFoundException,
            IllegalArgumentException {
        this(new File(path));
    }

    /**
     * creates a new image and reads the creation date from the exif header
     *
     * @param file the file to create the image from
     * @throws IllegalArgumentException if date could not be parsed from jpeg metadata
     */
    public DTJpegImage(File file) throws IllegalArgumentException {
        this.file = file;
        Calendar creationDate = readCreationDate();
        if (creationDate == null) {
            throw new IllegalArgumentException(
                    "could not read creation date from matadata");
        }
        this.creationDate = creationDate;
        this.originalDate = creationDate;
    }

    @Override
    public Calendar getCreationDate() {
        return creationDate;
    }

    private Calendar readCreationDate() {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            IImageMetadata metadata = Sanselan.getMetadata(is, null);
            if (metadata instanceof JpegImageMetadata) {
                JpegImageMetadata jim = (JpegImageMetadata) metadata;
                TiffField field = jim
                        .findEXIFValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                String time = (field == null) ? "" : (String) field.getValue();
                long millis = parseDate(time);
                Calendar date = new GregorianCalendar();
                date.setTimeInMillis(millis);
                return date;
            } else {
                logger.warn("error reading metadata for file "
                        + file.getAbsolutePath() + " metadata: " + metadata);
                return null;
            }
        } catch (IOException e) {
            logger.error(e);
            throw new IllegalArgumentException("could not process inputStream "
                    + this);
        } catch (ImageReadException e) {
            logger.error(e);
            throw new IllegalArgumentException("could not process inputStream "
                    + this);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("could not close inputstream " + is);
            }
        }
    }

    private static long parseDate(String date) {

        date = date.replace(" ", ":");
        String[] tokens = date.split(":");

        if (tokens.length < 6)
            throw new IllegalArgumentException("could not parse date " + date);

        for (int i = 0; i < 6; i++) {
            tokens[i] = tokens[i].replaceAll("[^0-9]", "");
        }

        int year = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int day = Integer.parseInt(tokens[2]);
        int hour = Integer.parseInt(tokens[3]);
        int minute = Integer.parseInt(tokens[4]);
        int second = Integer.parseInt(tokens[5]);

        Calendar c = new GregorianCalendar(year, month, day, hour, minute,
                second);

        return c.getTime().getTime();

    }

    @Override
    public int compareTo(JpegImage o) {
        if (creationDate.before(o.getCreationDate())) {
            return -1;
        } else if (creationDate.after(o.getCreationDate())) {
            return 1;
        }
        if (file == null || o.getFile() == null) {
            return -1;
        }
        if (file.getAbsolutePath().equals(o.getFile().getAbsolutePath())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void setCreationDate(Calendar date) {
        creationDate = date;
    }

    @Override
    public void writeTo(File file) {
        CopyTask ct = new CopyTask(getFile(), file);
        executor.submit(ct);
    }

    @Override
    public Calendar getOriginalDate() {
        return originalDate;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public Future<Image> getSWTImage(ImageSize size) {
        ImageResizerTask task = new ImageResizerTask(file, size);
        Future<Image> future = executor.submit(task);
        futures.add(future);
        return future;
    }

    @Override
    public Future<Image> getSWTImage() {
        LoadImageTask task = new LoadImageTask(file);
        Future<Image> future = executor.submit(task);
        futures.add(future);
        return future;
    }

    @Override
    public void finalize() {
        logger.info("finalizing " + this);
        for (Future<?> future : futures) {
            if (!future.isDone() && !future.isCancelled()) {
                future.cancel(true);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DTJpegImage)) return false;

        DTJpegImage that = (DTJpegImage) o;

        if (file != null ? !file.equals(that.file) : that.file != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return file != null ? file.hashCode() : 0;
    }
}
