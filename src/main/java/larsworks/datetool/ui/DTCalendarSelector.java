package larsworks.datetool.ui;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;

import static larsworks.datetool.ui.MainWindow.*;

public class DTCalendarSelector implements CalendarSelector {

	private final Calendar calendar;
	private final Composite cmp;
	
	private final DateTime date;
	private final DateTime time;
	
	public DTCalendarSelector(Composite parent) {
		calendar = new GregorianCalendar();
		cmp = new Composite(parent, SWT.NONE);
		cmp.setLayout(new GridLayout(1, false));
		date = new DateTime(cmp, SWT.CALENDAR);
		date.setLayoutData(fill);
		time = new DateTime(cmp, SWT.TIME);
		time.setLayoutData(fillHorizontal);
		
		date.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				setTime();
			}
		});
		
		time.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setTime();				
			}
		});
	}
	
	@Override
	public Calendar getSelection() {
		return calendar;
	}

	@Override
	public Composite getWidget() {
		return cmp;
	}

	@Override
	public void setDate(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		this.calendar.set(year, month, day, hour, minute, second);
		date.setYear(year);
		date.setMonth(month);
		date.setDay(day);
		time.setHours(hour);
		time.setMinutes(minute);
		time.setSeconds(second);
	}
	
	private void setTime() {
		calendar.set(
				date.getYear(), 
				date.getMonth() + 1, 
				date.getDay(),
				time.getHours(),
				time.getMinutes(),
				time.getSeconds());
	}

}
