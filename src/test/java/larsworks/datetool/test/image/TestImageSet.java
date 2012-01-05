package larsworks.datetool.test.image;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import larsworks.datetool.image.DTJpegImage;
import larsworks.datetool.image.DTJpegImageSet;
import larsworks.datetool.image.ImageSet;
import larsworks.datetool.image.JpegImage;

@RunWith(Parameterized.class)
public abstract class TestImageSet extends Assert {

	protected ImageSet<JpegImage> imageSet; 
	
	@Before
	public void setUp() {
		Set<JpegImage> images = new HashSet<JpegImage>();
		Calendar date_01_01_1000_0000 = new GregorianCalendar(1000,  1,  1, 00, 00);
		Calendar date_01_03_1981_1800 = new GregorianCalendar(1981,  3,  1, 18, 00);
		Calendar date_23_02_2009_1100 = new GregorianCalendar(2009,  2, 23, 11, 00);
		Calendar date_13_04_2011_1132 = new GregorianCalendar(2011,  4, 13, 11, 32);
		images.add(new DTJpegImage(date_23_02_2009_1100));
		images.add(new DTJpegImage(date_01_03_1981_1800));
		images.add(new DTJpegImage(date_13_04_2011_1132));
		images.add(new DTJpegImage(date_01_01_1000_0000));
		imageSet = new DTJpegImageSet(images);
	}
	
}
