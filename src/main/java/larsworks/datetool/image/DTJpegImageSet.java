package larsworks.datetool.image;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import larsworks.datetool.date.CalendarTimeUnit;

/**
 * base class for image set operations.
 */
public class DTJpegImageSet implements ImageSet<JpegImage> {
	
	private static final int calendarFields[] = {
			Calendar.YEAR,
			Calendar.MONTH,
			Calendar.DAY_OF_MONTH,
			Calendar.HOUR_OF_DAY,
			Calendar.MINUTE,
			Calendar.SECOND
	};
	
	private static final String fileExtension = "jpg";
	
	private final SortedSet<JpegImage> images;
	private Calendar startDate; 
	
	/**
	 * creates a new image set. The images will be sorted by 
	 * creation date. 
	 * @param images
	 */
	public DTJpegImageSet(Set<JpegImage> images) {
		this.images = new TreeSet<JpegImage>(images);
		if(this.images.size() > 0) {
			this.startDate = this.images.first().getCreationDate();
		} else {
			this.startDate = null;
		}
	}
	
	@Override
	public SortedSet<JpegImage> getImages() {
		return images;
	}

	@Override
	public Calendar getStartDate() {
		return startDate;
	}

	@Override
	public void modifyDates(CalendarTimeUnit unit, int amount) {
		for(JpegImage image : images) {
			add(unit, amount, image);
		}
		startDate = images.first().getCreationDate();
	}

	/**
	 * sets the start date for the first picture of the set and adds
	 * the time difference between the old and the new date of the 
	 * first image to all following images 
	 */
	@Override
	public void setStartDate(Calendar startDate) {
		Map<Integer, Integer> diff = new HashMap<Integer, Integer>();
		for(int i : calendarFields) {
			diff.put(i, diff(this.startDate, startDate, i));
		}
		for(JpegImage image : images) {
			for(int key : diff.keySet()) {
				add(key, diff.get(key), image);
			}
		}
		this.startDate = startDate;
	}
	
	private static int diff(Calendar oldDate, Calendar newDate, int field) {
		return newDate.get(field) - oldDate.get(field);
	}

	private static void add(CalendarTimeUnit unit, int amount, JpegImage image) {
		image.getCreationDate().add(unit.getField(), amount);
	}
	
	private static void add(int field, int amount, JpegImage image) {
		image.getCreationDate().add(field, amount);
	}

	@Override
	public int size() {
		return images.size();
	}

	@Override
	public String getFileExtension() {
		return fileExtension;
	}
}
