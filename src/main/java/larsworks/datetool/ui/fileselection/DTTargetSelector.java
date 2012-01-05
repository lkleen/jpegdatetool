package larsworks.datetool.ui.fileselection;

import java.io.File;

import larsworks.datetool.configuration.Configuration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import static larsworks.datetool.ui.MainWindow.*;

public class DTTargetSelector implements TargetSelector {

	private final Configuration configuration;
	private final Composite cmp;
	private final Text txtTarget;
	private final Button btnSelect;
	
	private final Label lblSuffix;
	private final Text txtSuffix;
	private File dir;
	
	public DTTargetSelector(final Configuration configuration, final Composite parent) {
		this.configuration = configuration;
		cmp = new Composite(parent, SWT.BORDER);
		cmp.setLayout(new GridLayout(3, false));
		cmp.setLayoutData(fillHorizontal);
		Label lblTarget = new Label(cmp, SWT.NONE);
		txtTarget = new Text(cmp, SWT.BORDER);
		txtTarget.setText(configuration.getAppConfiguration().getOutputDir());
		lblTarget.setLayoutData(fitHorizontal);
		txtTarget.setLayoutData(fillHorizontal);
		txtTarget.setEditable(false);
		lblTarget.setText("output folder");
		btnSelect = new Button(cmp, SWT.NONE);
		btnSelect.setLayoutData(fillVertical);
		btnSelect.setText("select");

		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent event) {
				Configuration conf = DTTargetSelector.this.configuration; 
				DirectoryDialog dialog = new DirectoryDialog(parent.getShell());
				dialog.setFilterPath(conf.getAppConfiguration().getOutputDir());
				String path = dialog.open();
				if(path != null) {
					dir = new File(path);
					txtTarget.setText(path);
					conf.getAppConfiguration().setOutputDir(path);
					conf.store();
				}
			}
		});
		
		lblSuffix = new Label(cmp, SWT.NONE);
		txtSuffix = new Text(cmp, SWT.BORDER);
		
		GridData gd = new GridData(SWT.FILL);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.verticalSpan = 2;
		
		lblSuffix.setLayoutData(fitHorizontal);
		txtSuffix.setLayoutData(gd);
		
		lblSuffix.setText("Suffix");
		txtSuffix.setText(configuration.getAppConfiguration().getOutputSuffix());
		
		txtSuffix.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				DTTargetSelector.this.configuration.getAppConfiguration().setOutputSuffix(txtSuffix.getText());
				DTTargetSelector.this.configuration.store();
			}
		});
		
	}
	
	@Override
	public Composite getWidget() {
		return cmp;
	}

	@Override
	public File getTarget() {
		return dir;
	}

	@Override
	public void setTarget(File dir) {
		txtTarget.setText(dir.getAbsolutePath());
		this.dir = dir;
	}
	


}
