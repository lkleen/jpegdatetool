package larsworks.datetool.image;

import larsworks.datetool.configuration.ImageSize;

import org.eclipse.swt.graphics.Image;

public interface ImageResizer {
	Image getResized(ImageSize newSize);
}
