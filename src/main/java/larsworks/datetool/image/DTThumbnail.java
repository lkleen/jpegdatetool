package larsworks.datetool.image;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import larsworks.datetool.configuration.ThumbConfiguration;
import larsworks.datetool.configuration.ThumbSize;
import larsworks.datetool.configuration.ThumbSize.Size;

public class DTThumbnail implements Thumb {

	private final Map<Size, BufferedImage> images = new HashMap<Size, BufferedImage>();
	
	public DTThumbnail(JpegImage image, ThumbConfiguration conf) {
		for(ThumbSize thumbSize : conf.getSizes()) {
			images.put(thumbSize.getSize(), null);
		}
	}

	@Override
	public BufferedImage getImage(Size size) {
		return images.get(size);
	}
	
}
