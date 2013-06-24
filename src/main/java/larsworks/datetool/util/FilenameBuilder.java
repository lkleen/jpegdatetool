package larsworks.datetool.util;

import larsworks.datetool.image.Suffix;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lars Kleen
 * @since ?version
 *        Date: 24.06.13
 *        Time: 10:10
 */
public abstract class FilenameBuilder {

    private final List<Suffix> suffixes = new ArrayList<Suffix>();

    /**
     * returns filename without prefix and suffix
     * @return
     */
    public abstract String getFilename();

    public void addSuffix(Suffix suffix) {
        suffixes.add(suffix);
    }

    /**
     * returns complete filename
     * @return
     */
    public StringBuilder build() {
        StringBuilder sb = new StringBuilder(getFilename());
        for(Suffix suffix : suffixes) {
            sb.append(suffix.getSuffix());
        }
        return sb;
    }

}
