package larsworks.datetool.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import larsworks.datetool.ui.fileselection.DTTargetSelector;
import larsworks.datetool.ui.fileselection.TargetSelector;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.date.DateFormatter;
import larsworks.datetool.date.SimpleDateFormatter;
import larsworks.datetool.image.Image;
import larsworks.datetool.image.ImageSet;
import larsworks.datetool.image.JpegImage;
import larsworks.datetool.ui.dnd.DragSourceFileChooser;
import larsworks.datetool.ui.fileselection.DTFileList;
import larsworks.datetool.ui.fileselection.FileListData;

public class MainWindow {

	protected static Logger logger = Logger.getLogger(MainWindow.class);
	
	public static final GridData fitHorizontal;
	public static final GridData fill;
	public static final GridData fillHorizontal;
	public static final GridData fillVertical;
	
	private final Configuration configuration;
	private final Display display;
	private final Shell shell;
	private Text txtSourceDir;
	private Button btnRemoveImages;
	private Text txtTargetDir;
	private AbstractFileList fileList;
	
	static {
		fitHorizontal = new GridData(SWT.FILL);
		fitHorizontal.grabExcessHorizontalSpace = false;
		fitHorizontal.horizontalAlignment = SWT.FILL;
		
		fill = new GridData(SWT.FILL);
		fill.grabExcessHorizontalSpace = true;
		fill.grabExcessVerticalSpace = true;
		fill.horizontalAlignment = SWT.FILL;
		fill.verticalAlignment = SWT.FILL;
		
		fillHorizontal = new GridData(SWT.FILL);
		fillHorizontal.grabExcessHorizontalSpace = true;
		fillHorizontal.horizontalAlignment = SWT.FILL;
		fillHorizontal.verticalAlignment = SWT.FILL;
		
		fillVertical = new GridData(SWT.FILL);
		fillVertical.grabExcessVerticalSpace = false;
		fillVertical.verticalAlignment = SWT.FILL;
	}
	
	public MainWindow(Configuration configuration) {
		super();
		this.configuration = configuration;
		display = new Display();
		shell = new Shell(display);
		GridLayout shellLayout = new GridLayout(1, false);
		shellLayout.marginHeight = 0;
		shellLayout.marginWidth = 0;
		shell.setLayout(shellLayout);
		shell.setLayoutData(fill);
		
		createFileView(configuration);
		
		GridLayout layout = new GridLayout(1, false);
		Composite options = new Composite(shell, SWT.NONE);
		options.setLayout(layout);
		options.setLayoutData(fill);
		
		TabFolder folder = new TabFolder(options, SWT.NONE);
		folder.setLayoutData(fill);
		
		createSettingsTab(folder);
		
		TabItem tab2 = new TabItem(folder, SWT.NONE);
		tab2.setText("test2");
		
		
		
		shell.open();
		while (!shell.isDisposed()) {
			display.readAndDispatch();
		}
		display.close();
		display.dispose();
		
		configuration.store();
	}

	private void createSettingsTab(TabFolder folder) {

		TabItem tab1 = new TabItem(folder, SWT.NONE);
		tab1.setText("settings");
		
		Composite settings = new Composite(folder, SWT.NONE);
		settings.setLayout(new GridLayout(2, false));
		settings.setLayoutData(fill);

		final CalendarSelector calendarSelector = new DTCalendarSelector(settings);
		final TargetSelector targetSelector = new DTTargetSelector(configuration, settings);
		
		Composite mainButtons = new Composite(settings, SWT.NONE);
		mainButtons.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd = new GridData(SWT.FILL);
		gd.horizontalSpan = 2;
		
		Button btnSetDate = new Button(mainButtons, SWT.NONE);
		btnSetDate.setText("set date");
		btnSetDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent event) {
				Calendar date = calendarSelector.getSelection();
				fileList.getFileListData().setStartDate(date);
				fileList.getFileListData().update();
			}
			
		});

		Button btnCopy = new Button(mainButtons, SWT.NONE);
		btnCopy.setText("copy images");
		btnCopy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
                copyImagesToTarget();
			}
		});
		tab1.setControl(settings);
	}

    private void copyImagesToTarget() {
        ImageSet<?> images = fileList.getFileListData().getImageSet();
        for(Image image :images.getImages()) {
            DateFormatter df = new SimpleDateFormatter(image.getCreationDate());
            StringBuilder filename = new StringBuilder();
            filename
                .append(df.getDateString())
                .append(configuration.getAppConfiguration().getOutputSuffix())
                .append(".")
                .append(images.getFileExtension());
            StringBuilder outDir = new StringBuilder(configuration.getAppConfiguration().getOutputDir());
            if(outDir.equals("")) {
                throw new IllegalArgumentException("no output dir set");
            }
            outDir
                .append(File.separatorChar)
                .append(filename);
            File target = new File(outDir.toString());
            image.writeTo(target);
        }
    }


    private void createFileView(Configuration configuration) {
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		Composite files = new Composite(shell, SWT.NONE);
		files.setLayout(new GridLayout(2, true));
		files.setLayoutData(fill);
		
		Composite source = new Composite(files, SWT.BORDER);
		source.setLayout(layout);
		source.setLayoutData(fill);
		
		AbstractFileChooser fc = new DragSourceFileChooser(source, configuration);
		fc.getWidget().setLayoutData(fill);

		txtSourceDir = new Text(source, SWT.BORDER);
		GridData fillGridHorizontal = new GridData();
		fillGridHorizontal.grabExcessHorizontalSpace = true;
		fillGridHorizontal.horizontalAlignment = SWT.FILL;
		txtSourceDir.setLayoutData(fillGridHorizontal);
		txtSourceDir.setEditable(false);
		
		addEventListener(fc, txtSourceDir);
		
		Composite target = new Composite(files, SWT.BORDER);
		layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		target.setLayout(layout);
		target.setLayoutData(fill);
		fileList = new DTFileList(target, configuration);
		final Table table = fileList.getWidget();
		table.setLayoutData(fill);
		
		Composite targetSelection = new Composite(target, SWT.NONE);
		targetSelection.setLayout(new GridLayout(2, false));
		targetSelection.setLayoutData(fillGridHorizontal);
		
		btnRemoveImages = new Button(targetSelection, SWT.NONE);
		btnRemoveImages.setText("remove");
		btnRemoveImages.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				removeSelection(table);
			}
		});
		
		txtTargetDir = new Text(targetSelection, SWT.BORDER);
		txtTargetDir.setEditable(false);
		txtTargetDir.setLayoutData(fillGridHorizontal);
	}
	
	private void removeSelection(final Table table) {
		TableItem[] items = table.getSelection();
		List<File> files = new ArrayList<File>();
		for(TableItem item : items) {
			JpegImage img = (JpegImage)item.getData();
			files.add(img.getFile());
		}
		FileListData fld = fileList.getFileListData();
		fld.removeFiles(files);
	}
	
	
	private void addEventListener(AbstractFileChooser fileChooser, final Text text) {
		fileChooser.getWidget().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				TreeItem item = (TreeItem)event.item;
				File file = (File)item.getData();
				text.setText(file.getAbsolutePath());
			}
		});
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public Shell getShell() {
		return shell;
	}
}
