package larsworks.datetool.ui;


import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.ui.fileselection.FileListData;

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
		addColumns();
	}

	@Override
	public Table getWidget() {
		return table;
	}
	
	public FileListData getFileListData() {
		return fileListData;
	} 
	
	protected abstract FileListData createFileListData();
	
	private void addColumns() {
		final TableColumn col1 = new TableColumn(table, SWT.LEFT);
		final TableColumn col2 = new TableColumn(table, SWT.BORDER);
		final TableColumn col3 = new TableColumn(table, SWT.LEFT);
		col1.setText("left");
		col2.setText("center");
		col3.setText("right");
		col3.setAlignment(SWT.RIGHT);
		col1.pack();
		col2.pack();
		col3.pack();
	}
}
