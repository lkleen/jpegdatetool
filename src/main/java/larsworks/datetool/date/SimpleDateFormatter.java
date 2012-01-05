package larsworks.datetool.date;

import java.util.Calendar;

public class SimpleDateFormatter implements DateFormatter {

	private final String dateString;
	
	public SimpleDateFormatter(Calendar date) {
		StringBuilder sb = new StringBuilder();
		
		int year   = date.get( Calendar.YEAR );
		int month  = date.get( Calendar.MONTH );
		int day    = date.get( Calendar.DAY_OF_MONTH );
		int hour   = date.get( Calendar.HOUR_OF_DAY );
		int minute = date.get( Calendar.MINUTE );
		int second = date.get( Calendar.SECOND );
		
		String strYear   = year   < 10 ? "0" + year   : "" + year;
		String strMonth  = month  < 10 ? "0" + month  : "" + month;
		String strDay    = day    < 10 ? "0" + day    : "" + day;
		String strHour   = hour   < 10 ? "0" + hour   : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;

		sb
		.append(strYear).append("-")
		.append(strMonth).append("-")
		.append(strDay).append("_")
		.append(strHour).append("")
		.append(strMinute).append("")
		.append(strSecond);
		
		dateString = sb.toString();
	}
	
	public String getDateString() {
		return dateString;
	}

}
