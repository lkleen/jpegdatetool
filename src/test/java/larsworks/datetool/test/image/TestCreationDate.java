package larsworks.datetool.test.image;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import larsworks.datetool.image.DTJpegImage;
import larsworks.datetool.image.JpegImage;

/**
 * tests if the creation date is set correctly when 
 * loading an image 
 */
@RunWith(Parameterized.class)
public class TestCreationDate extends Assert {
	
	private final String path;
	private final Calendar expected;
	
	public TestCreationDate(String path, Calendar expected) {
		super();
		this.path = path;
		this.expected = expected;
	}

	@Parameters
	public static List<Object[]> params() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[]{"/IMG_1643.jpg", new GregorianCalendar(2009, 8, 21, 17, 32, 51)});
		return params;
	}
	
	@Test
	public void test() throws URISyntaxException {
		InputStream is = getClass().getResourceAsStream(path);
		JpegImage img = new DTJpegImage(is, null);
		Calendar date = img.getCreationDate();
		assertEquals(expected.getTimeInMillis(), date.getTimeInMillis());
	}
	
}
