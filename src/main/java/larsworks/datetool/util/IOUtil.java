package larsworks.datetool.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import larsworks.datetool.image.JpegFileFilter;

public class IOUtil {

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
	
}
