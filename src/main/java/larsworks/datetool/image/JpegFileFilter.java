package larsworks.datetool.image;

import java.io.File;
import java.io.FileFilter;

public class JpegFileFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		if(!pathname.isFile()) return false;
		String name = pathname.getName(); 
		if(!name.contains(".")) return false;
		String extension = name.substring(name.lastIndexOf('.')).toLowerCase();
		if(extension.equals(".jpg") || extension.equals(".jpeg")) return true;
		return false;
	}

}
