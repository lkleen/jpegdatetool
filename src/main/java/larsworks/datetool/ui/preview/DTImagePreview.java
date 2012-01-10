package larsworks.datetool.ui.preview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.configuration.ThumbSize;
import larsworks.datetool.ui.ImagePreview;

public class DTImagePreview implements ImagePreview {

	private static Logger logger = Logger.getLogger(DTImagePreview.class);
	
	private static enum Orientation {
		horizontal,
		vertical
	}
	
	private final Image original;
	private final float aspectRatio;
	private final Rectangle bounds; 
	private final Orientation orientation;
	private final ThumbSize size;
	private Image resized;
	
	
	public DTImagePreview(File file, Configuration conf) {
		size = conf.getThumbnailConfiguration().getPreviewSize();
		original = loadImage(file);
		aspectRatio = original.getBounds().width / original.getBounds().height;
		orientation = (aspectRatio > 1) ? Orientation.horizontal : Orientation.vertical;
		bounds = original.getBounds();
		resized = resize(size.getWidth(), size.getHeight());
	}
	
	private static Image loadImage(File file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			return new Image(null, fis);
		} catch (FileNotFoundException e) {
			logger.error("file not found " + file.getAbsolutePath(), e);
			return null;
		}
	}

	@Override
	public Image getImage() {
		return resized;
	}

	@Override
	public void setZoom(int percent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHeight() {
		return resized.getBounds().height;
	}

	@Override
	public int getWidth() {
		return resized.getBounds().width;
	}

	@Override
	public Rectangle getBounds() {
		return resized.getBounds();
	}

	@Override
	public void resize(Rectangle bounds) {
		resized = resize(bounds.width, bounds.height);
	}
	
	private Image resize(int width, int height) {
		if(orientation == Orientation.vertical) {
			float ratio = (float)width / bounds.width;
			height = Math.round(bounds.height * ratio);
		} else {
			float ratio = (float)height / bounds.height;
			width = Math.round(bounds.width * ratio);
		}
		ImageData id = original.getImageData().scaledTo(width, height);
		
		return new Image(null, id);
	}
	
}
