package larsworks.datetool.image;

import java.io.File;
import java.util.Calendar;

import larsworks.datetool.configuration.ImageSize;

public interface Image {
	File getFile();

	/**
	 * @return the given creation date for this image
	 */
	Calendar getCreationDate();

	void setCreationDate(Calendar date);

	/**
	 * @return the unmodifiable original date of this image
	 */
	Calendar getOriginalDate();

	/**
	 * write the image with the possibly modified creation date
	 * 
	 * @param file
	 */
	void writeTo(File file);

	/**
	 * @return swt image with original size
	 */
	org.eclipse.swt.graphics.Image getSWTImage();

	/**
	 * @return swt image resized to {@link ImageSize}
	 */
	org.eclipse.swt.graphics.Image getSWTImage(ImageSize size);
}
