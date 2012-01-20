package larsworks.datetool.tasks;

import larsworks.datetool.util.IOUtil;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Image;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * User: lars
 * Date: 20.01.12
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
public class LoadImageTask implements Callable<org.eclipse.swt.graphics.Image> {
    
    private final File file;

    public LoadImageTask(File file) {
        this.file = file;
    }

    @Override
    public Image call() throws Exception {
        return IOUtil.loadImage(file);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
