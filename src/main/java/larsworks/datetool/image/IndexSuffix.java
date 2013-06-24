package larsworks.datetool.image;

import java.util.Arrays;

/**
 * @author Lars Kleen
 * @since 0.0.1
 *        Date: 09.06.13
 *        Time: 14:51
 */
public class IndexSuffix extends Suffix {

    private static final char SEPARATOR = '_';

    private final int numImagesExponent;
    private final int index;
    private final int indexExponent;

    public IndexSuffix(int numImages, int index) {
        if(numImages <= index) {
            throw new IllegalArgumentException("index must be lower than number of images. numImages: " + numImages + " index " + index);
        }
        this.numImagesExponent = exponent(numImages);
        this.index = index;
        this.indexExponent = exponent(index);
    }

    @Override
    public String getSuffix() {
        int numTrailingZeroes = numImagesExponent - indexExponent;
        char[] zeroes = new char[numTrailingZeroes];
        Arrays.fill(zeroes, '0');
        StringBuilder sb = new StringBuilder();
        return sb
                .append(SEPARATOR)
                .append(zeroes)
                .append(index)
                .toString();
    }

    private static int exponent(int value) {
        if(value == 0) {
            return 0;
        }
        return (int) Math.log10(value);
    }
}
