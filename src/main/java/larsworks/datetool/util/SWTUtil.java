package larsworks.datetool.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;

/**
 * swt utilities  
 *
 */
public class SWTUtil {

	protected static final Logger logger = Logger.getLogger(SWTUtil.class);
	
	private static final Map<Program, Image> fileIconCache = 
		new HashMap<Program, Image>();
	private static final Map<java.awt.Image, Image> objectIconCache = 
		new HashMap<java.awt.Image, Image>();

	/**
	 * sets text, data and image for the given tree node based
	 * on the given file.   
	**/
	private static class ChildAdder implements Runnable {
		
		private final File file;
		private final TreeItem item;
		
		protected ChildAdder(TreeItem item, File file) {
			this.file = file;
			this.item = item;
		}
		
		@Override
		public void run() {
			try {
				if(!item.isDisposed()) {
					TreeItem newItem = new TreeItem(item, SWT.NONE);
					setTreeItemFile(newItem, file);
				}
			} catch(Exception e) {
				logger.error("item: " + item + " file " + file, e);
			}
		}
	}
	
	/**
	 * sets text, data and image for the given tree node based
	 * on the given file.   
	 * @param item item to setup
	 * @param file item file or folder for of item
	 */
	public static void setTreeItemFile(TreeItem item, File file) {
		item.setText(file.getName());
		item.setData(file);
		item.setImage(getSystemIcon(file));
	}
	
	/**
	 * adds childs for items which contains folders. blocks until
	 * all childs are added.
	 * @param item
	 */
	public static void addChilds(TreeItem item) {
		if(item.getItemCount() != 0) return;
		File dir = (File)item.getData();
		if(dir.isDirectory() && dir.listFiles() != null) {
			for(File file : dir.listFiles()) {
				TreeItem newItem = new TreeItem(item, SWT.NONE);
				setTreeItemFile(newItem, file);
			}
		}
	}
	
	/**
	 * adds childs for items which contains folders asynchronous. 
	 * @param item
	 */
	public synchronized static void addChildsAsync(TreeItem item) {
		if(item.getItemCount() != 0) return;
		File dir = (File)item.getData();
		if(dir.isDirectory() && dir.listFiles() != null) {
			for(File file : dir.listFiles()) {
				ChildAdder ca = new ChildAdder(item, file);
				item.getDisplay().asyncExec(ca);
			}
		}
	}

	/**
	 * returns the system icon for the given file. icons will not
	 * be loaded twice, if the icon of a certain type has been 
	 * loaded before the cached image will bereturned  
	 * @param file file or directory
	 * @return the system icon image for the given file or folder
	 */
	public static Image getSystemIcon(File file) {
		String filename = file.getName();
		if(file.isFile() && filename.contains(".")) {
			String fileEnding = filename.substring(filename.lastIndexOf('.'));
			Program program = Program.findProgram(fileEnding);
			if(program != null) {
				Image image = fileIconCache.get(program);
				if(image == null) {
					ImageData iconData = program.getImageData();
					image = new Image(Display.getCurrent(), iconData);
					fileIconCache.put(program, image);
				}
				return image;
			}
		} 
	    ImageIcon systemIcon = 
	    	(ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file);
	    if(systemIcon == null) {
	    	return null;
	    }
	    java.awt.Image awtImage = systemIcon.getImage();
	    Image swtImage = objectIconCache.get(awtImage);  
	    if(swtImage == null) {
		    if(awtImage instanceof BufferedImage) {
		    	swtImage = 
		    		new Image(
		    			Display.getCurrent(), 
		    			AWTBufferedImageSWTImage.convertToSWT((BufferedImage)awtImage));
		    } else {
			    int width = awtImage.getWidth(null);
			    int height = awtImage.getHeight(null);
			    BufferedImage bufferedImage = 
			    	new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			    Graphics2D g2d = bufferedImage.createGraphics();
			    g2d.drawImage(awtImage, 0, 0, null);
			    g2d.dispose();
			    swtImage = 
			    	new Image(
			    		Display.getCurrent(), 
			    		AWTBufferedImageSWTImage.convertToSWT(bufferedImage));
		    }
		    objectIconCache.put(awtImage, swtImage);
	    }
	    return swtImage;
	}

}
