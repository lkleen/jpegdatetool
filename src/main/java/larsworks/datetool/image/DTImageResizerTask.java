package larsworks.datetool.image;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

import larsworks.datetool.configuration.ImageSize;

import org.eclipse.swt.graphics.Image;

public class DTImageResizerTask implements Callable<Image> {

	private final larsworks.datetool.image.Image image;
	private final ImageSize size;
	
	public DTImageResizerTask(larsworks.datetool.image.Image image, ImageSize size) {
		this.image = image;
		this.size = size;
	}

	@Override
	public Image call() throws Exception {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		return image.getSWTImage(size);
	}

}
