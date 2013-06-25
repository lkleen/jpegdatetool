package larsworks.datetool.ui.fileselection;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.date.DateFormatter;
import larsworks.datetool.date.SimpleDateFormatter;
import larsworks.datetool.image.DTJpegImage;
import larsworks.datetool.image.ImageSet;
import larsworks.datetool.image.JpegImage;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DTFileListData implements FileListData {

    private static final Logger logger = Logger.getLogger(DTFileListData.class);

    private final Table table;
    private final Configuration conf;
    private ImageSet<JpegImage> imageSet;
    private Calendar startDate;

    public DTFileListData(Table table, Configuration conf) {
        this.table = table;
        this.conf = conf;
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
        table.clearAll();
    }

    @Override
    public void removeFile(File file) {
        for (int i = 0; i < table.getItemCount(); i++) {
            TableItem ti = table.getItem(i);
            larsworks.datetool.image.Image image = (larsworks.datetool.image.Image) ti.getData();
            if (image.getFile().equals(file)) {
                table.remove(i);
            }
        }
        System.gc();
    }

    private void addImage(final JpegImage image) {
        String[] str;
        final TableItem ti = new TableItem(table, SWT.NONE);
        ti.setData(image);
        str = new String[3];
        str[0] = "";
        str[1] = getDateString(image.getCreationDate());
        str[2] = image.getFile().getAbsolutePath();
        ti.setText(str);

        final Future<Image> future = image.getSWTImage(conf.getThumbnailConfiguration().getIconSize());
        setTableItemImageAsync(ti, future);
    }

    private void setTableItemImageAsync(final TableItem ti, final Future<Image> future) {
        Display.getCurrent().asyncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!ti.isDisposed()) {
                        Image img = future.get();
                        ti.setImage(0, img);
                        table.getColumn(0).setWidth(img.getBounds().width);
                    }
                } catch (InterruptedException e) {
                    logger.error(e);
                } catch (ExecutionException e) {
                    logger.error(e);
                }
            }
        });
    }

    private static String getDateString(Calendar c) {
        DateFormatter df = new SimpleDateFormatter(c);
        return df.getDateString();
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
        return imageSet;
    }

}
