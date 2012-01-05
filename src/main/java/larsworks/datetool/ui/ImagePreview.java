package larsworks.datetool.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

public interface ImagePreview {
	void setZoom(int percent);
	void setWidth(int width);
	void setHeigth(int height);
	void setBounds(Rectangle bounds);
	int getWidth();
	int getHeight();
	Rectangle getBounds();
	Image getImage();
}
