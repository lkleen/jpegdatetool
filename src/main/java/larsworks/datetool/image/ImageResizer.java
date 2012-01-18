package larsworks.datetool.image;

import larsworks.datetool.configuration.ImageSize;

import org.eclipse.swt.graphics.Image;

import java.io.File;

public interface ImageResizer {
    Image getResized(ImageSize newSize);
}
