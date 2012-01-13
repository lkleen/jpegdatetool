package larsworks.datetool.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import larsworks.datetool.configuration.ImageSize;
import larsworks.datetool.configuration.xml.XMLImageSize;
import larsworks.datetool.util.IOUtil;

import org.apache.log4j.Logger;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

public class DTJpegImage implements JpegImage {

	private static final Logger logger = Logger.getLogger(DTJpegImage.class);
	private final InputStream is;
	private final File file;
	private final Calendar originalDate;
	private final Map<ImageSize, Image> images = new HashMap<ImageSize, Image>();
	private Calendar creationDate;
	private Image original;
	private ImageSize originalSize;

	private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); 
	
	private static class CopyTask implements Runnable {

		private final File source;
		private final File target;

		public CopyTask(File source, File target) {
			super();
			this.source = source;
			this.target = target;

			if (!source.exists()) {
				throw new IllegalArgumentException("source file not found");
			}

			if (target.exists()) {
				throw new IllegalArgumentException("target file already exists");
			}

		}

		@Override
		public void run() {
			try {
				logger.info(target.getAbsolutePath());
				target.createNewFile();

				FileInputStream fin = new FileInputStream(source);
				FileOutputStream fout = new FileOutputStream(target);

				byte[] buffer = new byte[1024];
				int length;

				while ((length = fin.read(buffer)) > 0) {
					fout.write(buffer, 0, length);
				}

				fin.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public DTJpegImage(Calendar creationDate) {
		if (creationDate == null) {
			throw new IllegalArgumentException("creationDate == null");
		} else {
			this.creationDate = creationDate;
			this.originalDate = creationDate;
			is = null;
			file = null;
		}
	}

	/**
	 * creates a new image and reads the creation date from the exif header
	 * 
	 * @param path
	 *            the path to the file to read from
	 * @throws FileNotFoundException
	 */
	public DTJpegImage(String path) throws FileNotFoundException, IllegalArgumentException {
		this(new File(path));
	}

	/**
	 * creates a new image and reads the creation date from the exif header
	 * 
	 * @param file
	 *            the file to read from
	 * @throws FileNotFoundException
	 */
	public DTJpegImage(File file) throws FileNotFoundException, IllegalArgumentException {
		this(new FileInputStream(file), file);
	}

	/**
	 * creates a new image and reads the creation date from the exif header
	 * 
	 * @param is
	 * @param file
	 */
	public DTJpegImage(InputStream is, File file) throws IllegalArgumentException {
		this.is = is;
		this.file = file;
		Calendar creationDate = readCreationDate();
		if(creationDate == null) {
			throw new IllegalArgumentException("could not read creation date from matadata");
		}
		this.creationDate = creationDate;
		this.originalDate = creationDate;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	private Calendar readCreationDate() {
		try {
			IImageMetadata metadata = Sanselan.getMetadata(is, null);
			if (metadata instanceof JpegImageMetadata) {
				JpegImageMetadata jim = (JpegImageMetadata) metadata;
				TiffField field = jim
						.findEXIFValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
				String time = (String) field.getValue();
				long millis = parseDate(time);
				Calendar date = new GregorianCalendar();
				date.setTimeInMillis(millis);
				return date;
			} else {
				logger.warn("error reading metadata for file " + file.getAbsolutePath() + " metadata: " + metadata);
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

	public int compareTo(JpegImage o) {
		if(creationDate.before(o.getCreationDate())) {
			return -1;
		} else if (creationDate.after(o.getCreationDate())) {
			return 1;
		}
		if(file == null || o.getFile() == null) {
			return -1;
		}
		if(file.getAbsolutePath().equals(o.getFile().getAbsolutePath())) {
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

	public File getFile() {
		return file;
	}

	@Override
	public Image getSWTImage(ImageSize size) {
		if(!images.containsKey(size)) {
			original = (original == null) ? getSWTImage() : original;
			if(size.equals(getOriginalSize())) {
				return original;
			}
			Image resized = new DTImageResizer(original).getResized(size);
			images.put(size, resized);
		}
		return images.get(size);
	}

	@Override
	public Image getSWTImage() {
		if(original == null) {
			original = IOUtil.loadImage(file);
			images.put(getOriginalSize(), original);
		}
		return original;
	}

	private ImageSize getOriginalSize() {
		if(originalSize == null) {
			Rectangle bounds = original.getBounds();
			originalSize = new XMLImageSize(bounds.width, bounds.height);
		}
		return originalSize; 
	}
}
