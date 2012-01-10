package larsworks.datetool.ui;


import java.io.File;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.util.SWTUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * base class for file and directory tree views
 */
public abstract class AbstractFileChooser implements DTWidget<Tree> {

	protected static final Logger logger = Logger.getLogger(AbstractFileChooser.class);
	
	protected final Tree tree;
	protected final Configuration conf;
	
	/**
	 * adds all tree nodes to the given path  
	 */
	private static class Expander implements Runnable {

		private final String path;
		private final Tree tree;
		private final TreeItem[] items;
		private final int depth;
		
		public Expander(Tree tree, String path, TreeItem[] items, int depth) {
			super();
			this.tree = tree;
			this.path = path;
			this.items = items;
			this.depth = depth;
		}

		@Override
		public void run() {
			if(path.length() == 0) {
				return;
			}
			for(TreeItem item : items) {
				if(path.startsWith(item.getText())) {
					SWTUtil.addChilds(item);
					item.setExpanded(true);
					tree.setSelection(item);
					int length = (depth == 0) 
						? (item.getText().length()) 
						: (item.getText().length()+1);
					if(length > path.length()) {
						continue;
					}
					String nextPath = path.substring(length, path.length());
					Expander ae = new Expander(
							tree, 
							nextPath, 
							item.getItems(), 
							length);
					ae.run();
				} else {
					SWTUtil.addChildsAsync(item);
				}
			}
		}
		
	}
	
	public AbstractFileChooser(Composite parent, Configuration conf) {
		super();
		tree = new Tree(parent, SWT.MULTI);
		this.conf = conf;
		addDragSource();
		addExpandListener();
		addDoubleClickListener();
		listRoots(tree);
	}
	
	/**
	 * expand the given path
	 * @param items
	 * @param path
	 */
	protected void expandPath(TreeItem[] items, String path) {
		expandPath(items, path, 0);
	}
	
	private void expandPath(
			final TreeItem[] items, 
			final String path, 
			final int depth) {
		Expander ex = new Expander(tree, path, items, depth);
		tree.getDisplay().asyncExec(ex);
	} 	

	/**
	 * add childs to expanded nodes
	 */
	private void addExpandListener() {
		tree.addListener(SWT.Expand, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem item = (TreeItem)event.item;
				File dir = (File)item.getData();
				conf.getAppConfiguration().setBaseDir(dir.getAbsolutePath());
				for(TreeItem ti : item.getItems()) {
					if(ti.getItemCount() == 0) {
						SWTUtil.addChildsAsync(ti);
					} else {
						return;
					}
				}
			}
		});
	}
	
	/**
	 * open image preview
	 */
	private void addDoubleClickListener() {
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				if(tree.getSelectionCount() == 1) {
					TreeItem item = tree.getSelection()[0];
					File file = (File)item.getData();
					new ImagePreviewShell(tree.getDisplay(), file, conf);
				}
			}
		});
	}

	protected abstract void addDragSource();

	/**
	 * list all root nodes. eg. disk drives on windows
	 * or root dir on unix systems 
	 * @param tree
	 */
	protected void listRoots(final Tree tree) {
		File[] drives = File.listRoots();
		for(File drive : drives) {
			TreeItem ti = new TreeItem(tree, SWT.NONE, 0);
			SWTUtil.setTreeItemFile(ti, drive);
			ti.setData(drive);
			ti.setText(drive.getAbsolutePath());
			SWTUtil.addChildsAsync(ti);
		}
	}
	
	public Tree getWidget() {
		return tree;
	}

}
