package larsworks.datetool.image;

/**
 * @author Lars Kleen
 * @since 0.0.1
 *        Date: 24.06.13
 *        Time: 10:32
 */
public class StringSuffix extends Suffix {

    private final String suffix;

    public StringSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }
}
