package larsworks.datetool.tasks;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

import larsworks.datetool.configuration.ImageSize;

import larsworks.datetool.image.DTImageResizer;
import org.eclipse.swt.graphics.Image;

public class ImageResizerTask implements Callable<Image> {

	private final File file;
	private final ImageSize size;
	
	public ImageResizerTask(File file, ImageSize size) {
		this.file = file;
		this.size = size;
	}

	@Override
	public Image call() throws Exception {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		return new DTImageResizer(file).getResized(size);
	}

}
