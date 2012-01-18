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

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class DTFileListData implements FileListData {

	private static final Logger logger = Logger.getLogger(DTFileListData.class);

	private static final ExecutorService executor;

	private final Table table;
	private final Set<JpegImage> images;
	private final Configuration conf;
	private ImageSet<JpegImage> imageSet;
	private Calendar startDate;

	static {
		int numProcessors = Runtime.getRuntime().availableProcessors() / 2;
		numProcessors = (numProcessors == 0) ? 1 : numProcessors;
        executor = Executors.newFixedThreadPool(numProcessors);
	}

	private static class AddResizedTask implements Runnable {

		private final TableItem ti;
		private final JpegImage image;
		private final ImageSize size;

		public AddResizedTask(TableItem ti, JpegImage image, ImageSize size) {
			super();
			this.ti = ti;
			this.image = image;
			this.size = size;
		}

		@Override
		public void run() {
			try {
				ti.setImage(0, image.getSWTImage(size));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

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

	private void addImage(JpegImage image) {
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

        Display.getCurrent().asyncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    ti.setImage(0, future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ExecutionException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
