package larsworks.datetool.test.image;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import larsworks.datetool.date.CalendarTimeUnit;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

public class TestImageSetAdd extends TestImageSet {

	private final CalendarTimeUnit unit;
	private final int amount;
	private final Calendar expectedFirst;
	private final Calendar expectedLast;
	

	public TestImageSetAdd(
			CalendarTimeUnit unit, 
			int amount,
			Calendar expectedFirst, 
			Calendar expectedLast) {
		super();
		this.unit = unit;
		this.amount = amount;
		this.expectedFirst = expectedFirst;
		this.expectedLast = expectedLast;
	}

	@Parameters
	public static List<Object[]> params() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[]{
				CalendarTimeUnit.YEAR, 
				1, 
				new GregorianCalendar(1001,  1,  1, 00, 00), 
				new GregorianCalendar(2012,  4, 13, 11, 32)});
		params.add(new Object[]{
				CalendarTimeUnit.YEAR, 
				-1, 
				new GregorianCalendar( 999,  1,  1, 00, 00), 
				new GregorianCalendar(2010,  4, 13, 11, 32)});
		params.add(new Object[]{
				CalendarTimeUnit.MONTH, 
				8, 
				new GregorianCalendar(1000,  9,  1, 00, 00), 
				new GregorianCalendar(2011, 12, 13, 11, 32)});
		params.add(new Object[]{
				CalendarTimeUnit.MONTH, 
				-25, 
				new GregorianCalendar( 997, 12,  1, 00, 00), 
				new GregorianCalendar(2009,  3, 13, 11, 32)});
		params.add(new Object[]{
				CalendarTimeUnit.SECOND, 
				3661, 
				new GregorianCalendar(1000,  1,  1, 01, 01, 01), 
				new GregorianCalendar(2011,  4, 13, 12, 33, 01)});
		return params;
	}
	
	@Test
	public void test() {
		imageSet.modifyDates(unit, amount);
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
