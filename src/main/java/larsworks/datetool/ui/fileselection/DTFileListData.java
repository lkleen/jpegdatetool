package larsworks.datetool.ui.fileselection;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.date.DateFormatter;
import larsworks.datetool.date.SimpleDateFormatter;
import larsworks.datetool.image.*;
import larsworks.datetool.tasks.ImageResizerTask;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class DTFileListData implements FileListData {

	private static final Logger logger = Logger.getLogger(DTFileListData.class);

	private final Table table;
	private final Set<JpegImage> images;
	private final Configuration conf;
	private ImageSet<JpegImage> imageSet;
	private Calendar startDate;

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
        List<File> fileList = Arrays.asList(files);
        for(int i = 0; i < table.getItemCount(); i++) {
            TableItem ti = table.getItem(i);
            larsworks.datetool.image.Image image = (larsworks.datetool.image.Image) ti.getData();
            if(fileList.contains(image.getFile())) {
                table.remove(i);
                images.remove(image);
            }
        }
        System.gc();
        /*
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
		*/
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

		final Future<Image> future = image.getSWTImage(conf.getThumbnailConfiguration().getIconSize());

        setTableItemImageAsync(ti, future);
    }

    private void setTableItemImageAsync(final TableItem ti, final Future<Image> future) {
        Display.getCurrent().asyncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!ti.isDisposed()) {
                        Image img = future.get();
                        ti.setImage(0, img);
                        table.getColumn(0).setWidth(img.getBounds().width);
                    }
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
