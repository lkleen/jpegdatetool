package larsworks.datetool.image;

import larsworks.datetool.configuration.ImageSize;
import larsworks.datetool.util.IOUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;

import java.io.File;

/**
 * TODO: fix resizing of images with vertical orientation
 */
public class DTImageResizer implements ImageResizer {

    private static enum Orientation {
        horizontal, vertical
    }

    private final File file;


    public DTImageResizer(File file) {
        this.file = file;
    }

    private Image getResized(Image original, ImageSize newSize) {
        Orientation orientation = getOrientation(original);
        Rectangle bounds = original.getBounds();
        int width = newSize.getWidth();
        int height = newSize.getHeight();

        if (orientation == Orientation.vertical) {
            float ratio = (float) width / bounds.width;
            height = Math.round(bounds.height * ratio);
        } else {
            float ratio = (float) height / bounds.height;
            width = Math.round(bounds.width * ratio);
        }

        ImageData id = original.getImageData().scaledTo(width, height);
        return new Image(null, id);
    }

    @Override
    public Image getResized(ImageSize newSize) {
        Image original = IOUtil.loadImage(file);
        Image resized  = getResized(original, newSize);
        original.dispose();
        return resized;
    }

    private Orientation getOrientation(Image original) {
        float aspectRatio = original.getBounds().width / original.getBounds().height;
        return (aspectRatio > 1) ? Orientation.horizontal : Orientation.vertical;
    }

}
