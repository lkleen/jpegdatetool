package larsworks.datetool.image;

import java.io.File;
import java.util.Calendar;

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
	 * @param file
	 */
	 void writeTo(File file);
	
	/**
	 * @return the thumbnail for this image
	 */
	Thumb getThumb();
}
