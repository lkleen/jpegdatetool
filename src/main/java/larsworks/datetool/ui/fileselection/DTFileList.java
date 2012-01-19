package larsworks.datetool.ui.fileselection;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.image.Image;
import larsworks.datetool.ui.AbstractFileList;
import larsworks.datetool.ui.ImagePreviewShell;
import larsworks.datetool.ui.dnd.DropTargetFileList;
import larsworks.datetool.ui.dnd.StringArrayTransfer;

import org.apache.log4j.Logger;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class DTFileList extends AbstractFileList {

    private static final Logger logger = Logger.getLogger(DTFileList.class);
    
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
        getWidget().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent mouseEvent) {
                Table t = (Table) mouseEvent.getSource();
                for (TableItem ti : t.getSelection()) {
                    Image img = (Image) ti.getData();
                    new ImagePreviewShell(table.getDisplay(), img.getFile(), conf);
                }
            }
        });
		return new DTFileListData(getWidget(), conf);
	}
}
