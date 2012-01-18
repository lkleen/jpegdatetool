package larsworks.datetool.ui.preview;

import java.io.File;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.configuration.ImageSize;
import larsworks.datetool.configuration.xml.XMLImageSize;
import larsworks.datetool.image.DTImageResizer;
import larsworks.datetool.ui.ImagePreview;
import larsworks.datetool.util.IOUtil;

public class DTImagePreview implements ImagePreview {

	private final File file;
	private final ImageSize size;
	private Image resized;
	
	
	public DTImagePreview(File file, Configuration conf) {
        this.file = file;
		size = conf.getThumbnailConfiguration().getPreviewSize();
		resized = resize(size.getWidth(), size.getHeight());
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
		return new DTImageResizer(file).getResized(new XMLImageSize(width, height));
	}
	
}
