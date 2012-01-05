package larsworks.datetool.ui.preview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;

import larsworks.datetool.ui.ImagePreview;

public class DTImagePreview implements ImagePreview {

	private static Logger logger = Logger.getLogger(DTImagePreview.class);
	
	private final Image original;
	private Image resized;
	
	public DTImagePreview(File file) {
		original = loadImage(file);
		resized = original;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHeigth(int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBounds(Rectangle bounds) {
		ImageData id = original.getImageData().scaledTo(bounds.width, bounds.height);
		resized = new Image(null, id);
	} 
	
}
