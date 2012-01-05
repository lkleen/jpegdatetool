package larsworks.datetool.image;

import java.awt.image.BufferedImage;

import larsworks.datetool.configuration.ThumbSize.Size;

public interface Thumb {
	BufferedImage getImage(Size size);
}
