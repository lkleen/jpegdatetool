package larsworks.datetool.image;

import java.util.SortedSet;

public interface JpegReader {
	/**
	 * reads images from the underlying datastructure (files, db, whatever)
	 * @return
	 */
	SortedSet<JpegImage> read();
}
