package larsworks.datetool.ui.fileselection;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import larsworks.datetool.image.ImageSet;

public interface FileListData {
	void clear();
	void update();
	void setStartDate(Calendar startDate);
	Calendar getStartDate();
	void addFiles(File... files);
	void addFiles(List<File> files);
	void removeFiles(File... files);
	void removeFiles(List<File> files);
	
	ImageSet<?> getImageSet();
}
