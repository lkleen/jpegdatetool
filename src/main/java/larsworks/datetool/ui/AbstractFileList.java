package larsworks.datetool.ui;


import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.ui.fileselection.FileListData;

import larsworks.datetool.ui.widgets.ImageTableHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public abstract class AbstractFileList implements DTWidget<Table> {

	protected final Table table;
	protected final FileListData fileListData;
	protected final Configuration conf;
	
	public AbstractFileList(Composite parent, Configuration conf) {
		super();
		this.conf = conf;
		table = new Table(parent, SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		fileListData = createFileListData();
	}

	@Override
	public Table getWidget() {
		return table;
	}
	
	public FileListData getFileListData() {
		return fileListData;
	} 
	
	protected abstract FileListData createFileListData();

}
