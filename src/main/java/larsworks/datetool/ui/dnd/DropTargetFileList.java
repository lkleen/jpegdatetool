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

	protected final FileListData fileListData;
	
	public DropTargetFileList(FileListData fileListData) {
		super();
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
    	File[] files = IOUtil.getJpegFiles(new File(path));
        log.debug(path);
        for(File file : files) {
            fileListData.addFile(file);
        }
	}

}
