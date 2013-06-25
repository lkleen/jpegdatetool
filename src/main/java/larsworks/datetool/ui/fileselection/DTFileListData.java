package larsworks.datetool.ui.fileselection;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.image.DTJpegImage;
import larsworks.datetool.image.DTJpegImageSet;
import larsworks.datetool.image.ImageSet;
import larsworks.datetool.image.JpegImage;
import larsworks.datetool.ui.widgets.ImageTableHandler;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Table;

import java.io.File;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

public class DTFileListData implements FileListData {

    private static final Logger logger = Logger.getLogger(DTFileListData.class);

    private final ImageTableHandler tableHandler;
    private Set<JpegImage> imageSet = new TreeSet<JpegImage>();
    private Calendar startDate;

    public DTFileListData(Table table, Configuration conf) {
        this.tableHandler = new ImageTableHandler(conf, table);
    }

    @Override
    public void addFile(File file) {
        JpegImage image = getImage(file);
        if (image != null) {
            addImage(image);
        }
    }

    @Override
    public void clear() {
        tableHandler.clearAll();
    }

    @Override
    public void removeFile(File file) {
        removeImage(file);
        System.gc();
    }

    private void removeImage(File file) {
        DTJpegImage image = new DTJpegImage(file);
        tableHandler.removeImage(image);
        imageSet.remove(image);
    }

    private void addImage(final JpegImage image) {
        tableHandler.addImage(image);
        imageSet.add(image);
    }



    private static JpegImage getImage(File file) {
        JpegImage image;
        try {
            image = new DTJpegImage(file);
        } catch (IllegalArgumentException e) {
            image = null;
            logger.error(
                    "error creating image object from file "
                            + file.getAbsolutePath(), e);
        }
        return image;
    }

    @Override
    public Calendar getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    @Override
    public ImageSet<JpegImage> getImageSet() {
        return new DTJpegImageSet(imageSet);
    }

}
