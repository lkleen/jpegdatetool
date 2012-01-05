package larsworks.datetool.ui.fileselection;

import java.io.File;

import larsworks.datetool.ui.DTWidget;

import org.eclipse.swt.widgets.Composite;

public interface TargetSelector extends DTWidget<Composite> {
	void setTarget(File dir);
	File getTarget();
}
