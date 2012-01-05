package larsworks.datetool.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import larsworks.datetool.configuration.ReaderConfiguration;

import org.apache.log4j.Logger;

public class DTJpegReader implements JpegReader {

	private static Logger logger = Logger.getLogger(DTJpegReader.class);
	private final List<File> files;
	private final SortedSet<JpegImage> images;
	private final ReaderConfiguration conf;
	
	public DTJpegReader(ReaderConfiguration conf, File... files) {
		this.conf = conf;
		this.files = new ArrayList<File>();
		this.images = new TreeSet<JpegImage>();
		for(File file : files) {
			if(file.isDirectory()) {
				addDir(file);
			} else {
				addFile(file);
			}
		}
	}
	
	private void addDir(File dir) {
		for(String path : dir.list()) {
			File file = new File(dir.getAbsolutePath() + "/" + path);
			if(file.isDirectory()) {
				if(conf.isRecursive()) addDir(file);
			} else {
				addFile(file);
			}
		}
	}
	
	private void addFile(File file) {
		if(!hasJpegExtension(file)) {
			logger.warn("tried to add unsupported filetype");
		} else {
			this.files.add(file);
		}		
	}
	
	public SortedSet<JpegImage> read() {
		if(images.isEmpty()) {
			for(File f : files) {
				try {
					JpegImage i = new DTJpegImage(f.getAbsolutePath());
					images.add(i);
				} catch (FileNotFoundException e) {
					logger.error("error when reading file " + f.getAbsolutePath(), e);
				} catch(IllegalArgumentException e) {
					logger.warn("could not add file " + f.getAbsolutePath(), e);
				}
			}
		}
		return new TreeSet<JpegImage>(images);
	}

	private static boolean hasJpegExtension(File file) {
		if(!file.getName().contains(".")) return false;
		String ext = file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase();
		if(ext.equals(".jpg") || ext.equals(".jpeg")) return true;
		return false;
	}
	
}
