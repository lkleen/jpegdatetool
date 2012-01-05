package larsworks.datetool.image;

import java.util.Calendar;
import java.util.SortedSet;

import larsworks.datetool.date.CalendarTimeUnit;

public interface ImageSet<I extends Image> {

	/**
	 * sets the startdate for this image set
	 * @param startDate
	 */
	void setStartDate(Calendar startDate);

	Calendar getStartDate();

	/**
	 * modifies the dates of all images
	 * @param unit unit to modify
	 * @param amount 
	 */
	void modifyDates(CalendarTimeUnit unit, int amount);

	SortedSet<I> getImages();

	/**
	 * @return the amount of images in this set
	 */
	int size();
	
	/**
	 * @return file extension like "jpg, png, ..."
	 */
	String getFileExtension();
}
