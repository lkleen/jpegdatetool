package larsworks.datetool.tasks;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: lars
 * Date: 20.01.12
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class CopyTask implements Runnable {

    private static final Logger logger = Logger.getLogger(CopyTask.class);

    private final File source;
    private final File target;

    public CopyTask(File source, File target) {
        super();
        this.source = source;
        this.target = target;

        if (!source.exists()) {
            throw new IllegalArgumentException("source file not found");
        }

        if (target.exists()) {
            throw new IllegalArgumentException("target file already exists");
        }

    }

    @Override
    public void run() {
        try {
            logger.info(target.getAbsolutePath());
            if (!target.createNewFile()) {
                throw new IllegalStateException("cannot create existing file " + target.getAbsolutePath());
            }

            FileInputStream fin = new FileInputStream(source);
            FileOutputStream fout = new FileOutputStream(target);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fin.read(buffer)) > 0) {
                fout.write(buffer, 0, length);
            }

            fin.close();
            fout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
