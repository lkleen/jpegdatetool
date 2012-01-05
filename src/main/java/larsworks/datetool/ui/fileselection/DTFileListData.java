package larsworks.datetool.ui.fileselection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import larsworks.datetool.date.DateFormatter;
import larsworks.datetool.date.SimpleDateFormatter;
import larsworks.datetool.image.DTJpegImage;
import larsworks.datetool.image.DTJpegImageSet;
import larsworks.datetool.image.ImageSet;
import larsworks.datetool.image.JpegImage;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class DTFileListData implements FileListData {
	
	private static final Logger logger = Logger.getLogger(DTFileListData.class);

	private final Table table;
	private final Set<JpegImage> images;
	private ImageSet<JpegImage> imageSet;
	private Calendar startDate;
	
	public DTFileListData(Table table) {
		this.table = table;
		this.images = new TreeSet<JpegImage>();
	}
	
	@Override
	public void addFiles(File... files) {
		for(File file : files) {
			JpegImage image = getImage(file);
			if(image != null) {
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
		for(JpegImage img : images) {
			for(File file : files) {
				if(img.getFile().equals(file)) {
					trash.add(img);
				}
			}
		}
		images.removeAll(trash);
		if(images.isEmpty()) {
			setStartDate(null);
		}
		updateTable();
	}

	private void updateTable() {
		table.removeAll();
		imageSet = new DTJpegImageSet(images);
		if(startDate != null) {
			imageSet.setStartDate(getStartDate());
		}
		for(JpegImage img : imageSet.getImages()) {
			addImage(img);
		}
	}

	private void addImage(JpegImage image) {
		String[] str;
		TableItem ti = new TableItem(table, SWT.NONE);
		ti.setData(image);
		str = new String[3];
		str[0] = "";
		str[1] = getDateString(image.getCreationDate());
		str[2] = image.getFile().getAbsolutePath();
		ti.setText(str);
	}
	
	private static String getDateString(Calendar c) {
		DateFormatter df = new SimpleDateFormatter(c);
		return df.getDateString();
	}
	
	private static JpegImage getImage(File file) {
		JpegImage image;
		try {
			image = new DTJpegImage(file);
		} catch (FileNotFoundException e) {
			image = null;
			logger.error("file not found " + file.getAbsolutePath(), e);
		} catch(IllegalArgumentException e) {
			image = null;
			logger.error("error creating image object from file " + file.getAbsolutePath(), e);
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
