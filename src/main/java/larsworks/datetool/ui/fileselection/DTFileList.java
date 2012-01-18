package larsworks.datetool.ui.fileselection;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.ui.AbstractFileList;
import larsworks.datetool.ui.dnd.DropTargetFileList;
import larsworks.datetool.ui.dnd.StringArrayTransfer;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;

public class DTFileList extends AbstractFileList {

	public DTFileList(Composite parent, Configuration conf) {
		super(parent, conf);
		addDropTarget();
	}

	private void addDropTarget() {
		DropTarget dt = new DropTarget(table, DND.DROP_MOVE);
		dt.setTransfer(new Transfer[] { StringArrayTransfer.getInstance() });
		dt.addDropListener(new DropTargetFileList(table, fileListData));
	}

	@Override
	protected FileListData createFileListData() {
		return new DTFileListData(getWidget(), conf);
	}
}
