package larsworks.datetool.image;

import java.awt.Dimension;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;

public class DTImageResizer implements ImageResizer {

	private static enum Orientation {
		horizontal,
		vertical
	}
	
	private final Image original;
	
	
	public DTImageResizer(Image original) {
		this.original = original;
	}
	
	@Override
	public Image getResized(Dimension newSize) {
		Orientation orientation = getOrientation(original);
		Rectangle bounds = original.getBounds();
		int width  = newSize.width;
		int height = newSize.height;
		
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
	
	private Orientation getOrientation(Image original) {
		float aspectRatio = original.getBounds().width / original.getBounds().height;
		return (aspectRatio > 1) ? Orientation.horizontal : Orientation.vertical;
	} 

}
