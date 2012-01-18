package larsworks.datetool.test.image;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import larsworks.datetool.image.DTJpegImage;
import larsworks.datetool.image.JpegImage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * tests if the creation date is set correctly when loading an image
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
		params.add(new Object[] { "/IMG_1643.jpg",
				new GregorianCalendar(2009, 8, 21, 17, 32, 51) });
		return params;
	}

	@Test
	public void test() throws URISyntaxException, FileNotFoundException,
			IllegalArgumentException {
		URL url = getClass().getResource(path);
		JpegImage img = new DTJpegImage(url.getFile());
		Calendar date = img.getCreationDate();
		assertEquals(expected.getTimeInMillis(), date.getTimeInMillis());
	}

}
