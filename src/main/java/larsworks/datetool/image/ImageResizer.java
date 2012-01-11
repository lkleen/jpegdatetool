package larsworks.datetool.image;

import java.awt.Dimension;

import org.eclipse.swt.graphics.Image;

public interface ImageResizer {
	Image getResized(Dimension newSize);
}
