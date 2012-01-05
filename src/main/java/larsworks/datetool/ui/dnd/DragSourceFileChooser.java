package larsworks.datetool.ui.dnd;

import java.io.File;

import larsworks.datetool.configuration.AppConfiguration;
import larsworks.datetool.ui.AbstractFileChooser;
import larsworks.datetool.util.SWTUtil;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

public class DragSourceFileChooser extends AbstractFileChooser {

	public DragSourceFileChooser(Composite parent, AppConfiguration conf) {
		super(parent, conf);
		if(conf != null) {
			expandPath(tree.getItems(), conf.getBaseDir());
		}
	}

	@Override
	protected void addDragSource() {
		DragSource ds = new DragSource(tree, DND.DROP_MOVE);
		ds.setTransfer(new Transfer[] {StringArrayTransfer.getInstance()});
		ds.addDragListener(new DragSourceAdapter() {
			@Override
		    public void dragSetData(DragSourceEvent event) {
				StringArrayType sat = new StringArrayType();
				for(TreeItem item : tree.getSelection()) {
					File file = (File)item.getData();
					sat.add(file.getAbsolutePath());
				}
				event.data = sat;
			}
		});
	}
	
}
