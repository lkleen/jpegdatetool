package larsworks.datetool.test.image;

import larsworks.datetool.image.IndexSuffix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Lars Kleen
 * @since ?version
 *        Date: 24.06.13
 *        Time: 09:49
 */
@RunWith(Parameterized.class)
public class IndexSuffixTest {

    private final int numImages;
    private final int index;
    private final String expected;

    public IndexSuffixTest(int numImages, int index, String expected) {
        this.numImages = numImages;
        this.index = index;
        this.expected = expected;
    }

    @Test
    public void test() {
        assertEquals("_" + expected, new IndexSuffix(numImages, index).getSuffix());
    }

    @Parameterized.Parameters
    public static List<Object[]> params() {
        List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[] {
                100, 1, "001"
        });
        params.add(new Object[] {
                1, 0, "0"
        });
        params.add(new Object[] {
                9, 1, "1"
        });
        params.add(new Object[] {
                10, 1, "01"
        });
        params.add(new Object[] {
                99, 1, "01"
        });
        params.add(new Object[] {
                100, 9, "009"
        });
        params.add(new Object[] {
                999, 9, "009"
        });
        params.add(new Object[] {
                1000, 9, "0009"
        });
        params.add(new Object[] {
                999, 90, "090"
        });
        params.add(new Object[] {
                1000, 90, "0090"
        });
        params.add(new Object[] {
                1001, 1000, "1000"
        });
        return params;
    }

}
