package larsworks.datetool.test.image;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import larsworks.datetool.image.DTJpegReader;
import larsworks.datetool.image.JpegImage;
import larsworks.datetool.image.JpegReader;
import larsworks.datetool.test.configuration.TestReaderConfiguration;

import org.junit.Assert;
import org.junit.Test;

/**
 * checks the amount of returned test images depending 
 * on the given path and reader settings.  
 */
public class TestJpegReader extends Assert {

	@Test
	public void test() throws URISyntaxException {
		URL dirUrl = getClass().getResource("/images");
		URL fileUrl = getClass().getResource("/IMG_1643.jpg");
		
		TestReaderConfiguration trc = new TestReaderConfiguration();
		
		JpegReader reader = new DTJpegReader(trc, new File(fileUrl.toURI()));
		Set<JpegImage> set = reader.read();
		assertEquals(1, reader.read().size());

		reader = new DTJpegReader(trc, new File(dirUrl.toURI()));
		set = reader.read();
		assertEquals(3, set.size());

		reader = new DTJpegReader(trc, new File(dirUrl.toURI()), new File(fileUrl.toURI()));
		assertEquals(4, reader.read().size());
		
		trc.setRecursive(false);
		reader = new DTJpegReader(trc, new File(dirUrl.toURI()), new File(fileUrl.toURI()));
		assertEquals(3, reader.read().size());
	}
	
}
