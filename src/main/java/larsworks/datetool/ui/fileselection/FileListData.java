package larsworks.datetool.ui.fileselection;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import larsworks.datetool.image.ImageSet;

public interface FileListData {
	void clear();
	void setStartDate(Calendar startDate);
	Calendar getStartDate();
    void addFile(File file);
    void removeFile(File file);
	ImageSet<?> getImageSet();
}
