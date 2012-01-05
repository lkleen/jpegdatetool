package larsworks.datetool.ui;

import java.io.File;

import larsworks.datetool.ui.preview.DTImagePreview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ImagePreviewShell {

	private final Shell shell;
	private final ImagePreview image;
	
	public ImagePreviewShell(Display display, File file) {
		super();
		shell = new Shell(display);
		image = new DTImagePreview(file);
		shell.setLayout(new GridLayout(1, false));
		final ScrolledComposite sCmp = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		sCmp.setLayoutData(gd);
		final Composite cmp = new Composite(sCmp, SWT.NONE);
		cmp.setLayout(new FillLayout());
		final Label lbl = new Label(cmp, SWT.NONE);
		image.setBounds(shell.getBounds());
		lbl.setImage(image.getImage());
		sCmp.setContent(lbl);
		cmp.pack();
		shell.pack();
		shell.open();
		shell.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseScrolled(MouseEvent event) {
				System.out.println(event.count);
				
			}
		});

	}
	
}
