package larsworks.datetool.ui.widgets;

import larsworks.datetool.configuration.Configuration;
import larsworks.datetool.date.DateFormatter;
import larsworks.datetool.date.SimpleDateFormatter;
import larsworks.datetool.image.Image;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Lars Kleen
 * @since ?version
 *        Date: 25.06.13
 *        Time: 22:56
 */
public class ImageTableHandler {

    private static final Logger logger = Logger.getLogger(ImageTableHandler.class);
    private final Configuration conf;
    private final Table table;

    public ImageTableHandler(Configuration conf, Table table) {
        this.conf = conf;
        this.table = table;
    }

    public void addImage(Image image) {
        String[] str;
        final TableItem ti = new TableItem(table, SWT.NONE);
        ti.setData(image);
        str = new String[3];
        str[0] = "";
        str[1] = getDateString(image.getCreationDate());
        str[2] = image.getFile().getAbsolutePath();
        ti.setText(str);
        final Future<org.eclipse.swt.graphics.Image> future = image.getSWTImage(conf.getThumbnailConfiguration().getIconSize());
        setTableItemImageAsync(ti, future);
    }

    public void clearAll() {
        table.clearAll();
    }

    public void removeImage(Image image) {
        for (int i = 0; i < table.getItemCount(); i++) {
            TableItem ti = table.getItem(i);
            Image tableItemData = (Image) ti.getData();
            if (image.equals(tableItemData)) {
                table.remove(i);
            }
        }
    }

    private void setTableItemImageAsync(final TableItem ti, final Future<org.eclipse.swt.graphics.Image> future) {
        Display.getCurrent().asyncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!ti.isDisposed()) {
                        org.eclipse.swt.graphics.Image img = future.get();
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
}
