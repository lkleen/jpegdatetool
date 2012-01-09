package larsworks.datetool.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import larsworks.datetool.configuration.ThumbSize;

public class DTThumbnail implements Thumb {

	private final java.awt.Image image; 
	
	public DTThumbnail(JpegImage image, ThumbSize size) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(image.getFile());
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("could not create image from file");
		}
		this.image = img.getScaledInstance(size.getWidth(), size.getHeight(), 0);
	}

	@Override
	public Image getImage() {
		return image;
	}

	
}
