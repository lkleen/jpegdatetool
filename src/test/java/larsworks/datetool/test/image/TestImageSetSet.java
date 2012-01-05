package larsworks.datetool.test.image;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

/**
 * test if the start and end date is set correctly when 
 * setting the start date
 */
public class TestImageSetSet extends TestImageSet {

	private final Calendar newStartDate;
	private final Calendar expectedFirst;
	private final Calendar expectedLast;

	public TestImageSetSet(
			Calendar newStartDate, 
			Calendar expectedFirst,
			Calendar expectedLast) {
		super();
		this.newStartDate = newStartDate;
		this.expectedFirst = expectedFirst;
		this.expectedLast = expectedLast;
	}

	@Parameters
	public static List<Object[]> params() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[]{
				new GregorianCalendar(1001,  1,  1, 00, 00),
				new GregorianCalendar(1001,  1,  1, 00, 00), 
				new GregorianCalendar(2012,  4, 13, 11, 32)});
		params.add(new Object[]{
				new GregorianCalendar(1010,  2,  2, 00, 00),
				new GregorianCalendar(1010,  2,  2, 00, 00), 
				new GregorianCalendar(2021,  5, 14, 11, 32)});
		params.add(new Object[]{
				new GregorianCalendar(1001,  2,  2, 10, 10),
				new GregorianCalendar(1001,  2,  2, 10, 10), 
				new GregorianCalendar(2012,  5, 14, 21, 42)});
		params.add(new Object[]{
				new GregorianCalendar(1000, 14,  1, 00, 00),
				new GregorianCalendar(1001,  2,  1, 00, 00), 
				new GregorianCalendar(2012,  5, 13, 11, 32)});
		params.add(new Object[]{
				new GregorianCalendar(1100, 13, 20,  8, 30, 45),
				new GregorianCalendar(1101,  1, 20,  8, 30, 45), 
				new GregorianCalendar(2112,  5,  1, 20, 02, 45)});
		return params;
	}
	
	@Test
	public void test() {
		imageSet.setStartDate(newStartDate);
		assertEquals(
				"first failed", 
				expectedFirst, 
				imageSet.getImages().first().getCreationDate());
		assertEquals(
				"last failed", 
				expectedLast, 
				imageSet.getImages().last().getCreationDate());
	}
}