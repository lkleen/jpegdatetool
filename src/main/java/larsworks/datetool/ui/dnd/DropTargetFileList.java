package larsworks.datetool.ui.dnd;

import java.io.File;

import larsworks.datetool.ui.fileselection.FileListData;
import larsworks.datetool.util.IOUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class DropTargetFileList extends DropTargetAdapter {

    static final Logger log = Logger.getLogger(DropTargetFileList.class);

	protected final Table table;
	protected final FileListData fileListData;
	
	public DropTargetFileList(Table table, FileListData fileListData) {
		super();
		this.table = table;
		this.fileListData = fileListData;
	}

	@Override
	public void drop(DropTargetEvent event) {
		StringArrayType sat = (StringArrayType)event.data;
		for(String path : sat.getList()) {
			addFiles(path);
		}
    }

	private void addFiles(String path) {
		File file = new File(path);
    	log.debug(path);
    	File[] files = IOUtil.getJpegFiles(file);
    	if(files.length > 0) {
    		fileListData.addFiles(IOUtil.getJpegFiles(file));
    		for(TableColumn col : table.getColumns()) {
    			col.pack();
    		}
    	}
	}

}
