package larsworks.datetool.util;

import larsworks.datetool.date.DateFormatter;
import larsworks.datetool.image.Image;

/**
 * @author Lars Kleen
 * @since 0.0.1
 *        Date: 24.06.13
 *        Time: 10:18
 */
public class DataTimeImageFilenameBuilder extends FilenameBuilder {

    private final DateFormatter dateFormatter;

    public DataTimeImageFilenameBuilder(DateFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public String getFilename() {
        return dateFormatter.getDateString();
    }

}
