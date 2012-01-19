package larsworks.datetool.ui.fileselection;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.*;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.configuration.ImageSize;
import larsworks.datetool.date.DateFormatter;
import larsworks.datetool.date.SimpleDateFormatter;
import larsworks.datetool.image.DTImageResizerTask;
import larsworks.datetool.image.DTJpegImage;
import larsworks.datetool.image.DTJpegImageSet;
import larsworks.datetool.image.ImageSet;
import larsworks.datetool.image.JpegImage;

import larsworks.datetool.ui.ImagePreviewShell;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

public class DTFileListData implements FileListData {

	private static final Logger logger = Logger.getLogger(DTFileListData.class);

	private static final ExecutorService executor;

	private final Table table;
	private final Set<JpegImage> images;
	private final Configuration conf;
	private ImageSet<JpegImage> imageSet;
	private Calendar startDate;

	static {
		int numProcessors = Runtime.getRuntime().availableProcessors();
		numProcessors = (numProcessors == 0) ? 1 : numProcessors;
        executor = Executors.newFixedThreadPool(numProcessors);
	}

	public DTFileListData(Table table, Configuration conf) {
		this.table = table;
		this.conf = conf;
		this.images = new TreeSet<JpegImage>();
	}

	@Override
	public void addFiles(File... files) {
		for (File file : files) {
			JpegImage image = getImage(file);
			if (image != null) {
				images.add(image);
			}
		}
		updateTable();
	}

	@Override
	public void clear() {
		images.clear();
	}

	@Override
	public void removeFiles(File... files) {
		Set<JpegImage> trash = new HashSet<JpegImage>();
		for (JpegImage img : images) {
			for (File file : files) {
				if (img.getFile().equals(file)) {
					trash.add(img);
				}
			}
		}
		images.removeAll(trash);
		if (images.isEmpty()) {
			setStartDate(null);
		}
		updateTable();
	}

	private void updateTable() {
		table.removeAll();
		imageSet = new DTJpegImageSet(images);
		if (startDate != null) {
			imageSet.setStartDate(getStartDate());
		}
		for (JpegImage img : imageSet.getImages()) {
			addImage(img);
		}
	}

	private void addImage(final JpegImage image) {
		String[] str;
		final TableItem ti = new TableItem(table, SWT.NONE);
		ti.setData(image);
		str = new String[3];
		str[0] = "";
		str[1] = getDateString(image.getCreationDate());
		str[2] = image.getFile().getAbsolutePath();
		ti.setText(str);

        Callable<Image> task = new DTImageResizerTask(image, conf.getThumbnailConfiguration().getIconSize());
		final Future<Image> future = executor.submit(task);

        ti.addListener(SWT.MouseDoubleClick, new Listener() {
            @Override
            public void handleEvent(Event event) {
                logger.info("click");

                new ImagePreviewShell(table.getDisplay(), image.getFile(), conf);
            }
        });
        addTableItemDisposeListener(ti, future);
        setTableItemImageAsync(ti, future);
    }

    private void addTableItemDisposeListener(TableItem ti, final Future<Image> future) {
        ti.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent disposeEvent) {
                future.cancel(true);
            }
        });
    }

    private void setTableItemImageAsync(final TableItem ti, final Future<Image> future) {
        Display.getCurrent().asyncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    Image img = future.get();
                    ti.setImage(0, img);
                    table.getColumn(0).setWidth(img.getBounds().width);
                } catch (InterruptedException e) {
                    logger.error(e);
                } catch (ExecutionException e) {
                    logger.error(e);
                }
            }
        });
    }

    private static String getDateString(Calendar c) {
		DateFormatter df = new SimpleDateFormatter(c);
		return df.getDateString();
	}

	private static JpegImage getImage(File file) {
		JpegImage image;
		try {
			image = new DTJpegImage(file);
		} catch (IllegalArgumentException e) {
			image = null;
			logger.error(
					"error creating image object from file "
							+ file.getAbsolutePath(), e);
		}
		return image;
	}

	@Override
	public void addFiles(List<File> files) {
		addFiles(files.toArray(new File[files.size()]));
	}

	@Override
	public void removeFiles(List<File> files) {
		removeFiles(files.toArray(new File[files.size()]));
	}

	@Override
	public void update() {
		updateTable();
	}

	@Override
	public Calendar getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	@Override
	public ImageSet<JpegImage> getImageSet() {
		return imageSet;
	}

}
