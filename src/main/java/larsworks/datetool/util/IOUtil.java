package larsworks.datetool.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;

import larsworks.datetool.image.JpegFileFilter;

public class IOUtil {

	private static Logger logger = Logger.getLogger(IOUtil.class);
	
	public static File[] getJpegFiles(File file) {
		List<File> files = new ArrayList<File>();
		FileFilter filter = new JpegFileFilter();
		if(file.isDirectory()) {
			for(File f : file.listFiles(filter)) {
				files.add(f);
			}
		} else if(filter.accept(file)) {
			files.add(file);
		}
		return files.toArray(new File[files.size()]);
	}
	
	public static Image loadImage(File file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			return new Image(null, fis);
		} catch (FileNotFoundException e) {
			logger.error("file not found " + file.getAbsolutePath(), e);
			return null;
		}
	}
	
}
