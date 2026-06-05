import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class FindMaxTest extends NumberAndStringHelperTest {
    /*
    * test cases
    *       positive:
    *       - [3, 5, 7, 2] -> 7
    *       - [5] -> 5
    *       - [-3, -5, 0, 2] -> 2
    *       - [-5, -10, -4, -11] -> -4
    *       negative:
    *       - [] -> exception
     */

    public static Stream<Arguments> arraysToFindMax() {
        return Stream.of(
                //positive:
                Arguments.of(new int[] {3, 5, 7, 2}, 7),
                Arguments.of(new int[] {5}, 5),
                Arguments.of(new int[] {-3, -5, 0, 2}, 2),
                Arguments.of(new int[] {-5, -10, -4, -11}, -4)
        );
    }

    @ParameterizedTest
    @MethodSource("arraysToFindMax")
    public void userCanFindMax(int[] inputArray, int expectedMax) {
        int foundMax = numberAndStringHelper.findMax(inputArray);
        assertEquals(expectedMax, foundMax);
    }

    @Test
    // negative: [] -> exception
    public void userCannotFindMaxInNullArray() {
        assertThrows(NoSuchElementException.class, () -> numberAndStringHelper.findMax(new int[0]));
    }
}
