package larsworks.datetool.ui;

import java.util.Calendar;

import org.eclipse.swt.widgets.Composite;

public interface CalendarSelector extends DTWidget<Composite> {
	public Calendar getSelection();
	public void setDate(Calendar date);
}
