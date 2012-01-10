package larsworks.datetool.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

public interface ImagePreview {
	void resize(Rectangle bounds);
	void setZoom(int percent);
	int getWidth();
	int getHeight();
	Rectangle getBounds();
	Image getImage();
}
