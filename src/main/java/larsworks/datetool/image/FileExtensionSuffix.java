package larsworks.datetool.image;

/**
 * @author Lars Kleen
 * @since 0.0.1
 *        Date: 24.06.13
 *        Time: 10:24
 */
public class FileExtensionSuffix extends Suffix {

    private static final char SEPARATOR = '.';

    private final String extension;

    public FileExtensionSuffix(String extension) {
        this.extension = extension;
    }

    @Override
    public String getSuffix() {
        return SEPARATOR + extension;
    }
}
