package larsworks.datetool.tasks;

import java.io.File;
import java.util.concurrent.Callable;

import larsworks.datetool.configuration.ImageSize;

import larsworks.datetool.image.DTImageResizer;
import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;

public class ImageResizerTask implements Callable<Image> {

    private static final Logger log = Logger.getLogger(ImageResizerTask.class);

	private final File file;
	private final ImageSize size;
	
	public ImageResizerTask(File file, ImageSize size) {
		this.file = file;
		this.size = size;
	}

	@Override
	public Image call() throws Exception {
        log.debug("submitted " + this.hashCode());
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        Image img = new DTImageResizer(file).getResized(size);
        log.debug("calculated " + this.hashCode());
		return img;
	}

}
