package larsworks.datetool.date;

import java.util.Calendar;

public enum CalendarTimeUnit {
	YEAR(Calendar.YEAR),
	MONTH(Calendar.MONTH),
	DAY(Calendar.DAY_OF_YEAR),
	HOUR(Calendar.HOUR_OF_DAY),
	MINUTE(Calendar.MINUTE),
	SECOND(Calendar.SECOND);

	private final int field;

	private CalendarTimeUnit(int field) {
		this.field = field;
	}

	public int getField() {
		return field;
	}

}
