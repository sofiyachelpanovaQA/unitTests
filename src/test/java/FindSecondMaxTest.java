import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindSecondMaxTest extends NumberAndStringHelperTest {
    /*
    * testcases:
    *       positive:
    *       обычные массивы
    *       - [3, 5, 10, 25, 120, 121] -> 120
    *       - [-3, -5, -10, -25, -120, -121] -> -5
    *       массивы с одинаковыми элементами:
    *       - [100, 100, 120, 90] -> 100
    *
    *       negative:
    *       - [100] -> exception
    *       - [100, 100, 100] -> exception
    *
     */

    public static Stream<Arguments> arraysToFindSecondMax() {
        return Stream.of(
                //positive:
                Arguments.of(new int[]{3, 5, 10, 25, 120, 121}, 120),
                Arguments.of(new int[]{-3, -5, -10, -25, -120, -121}, -5),
                Arguments.of(new int[]{100, 100, 120, 90}, 100) //тест падает, нужна доработка метода
        );
    }

    @ParameterizedTest
    @MethodSource("arraysToFindSecondMax")
    public void userCanFindMax(int[] inputArray, int expectedSecondMax) {
        int foundMax = numberAndStringHelper.findSecondMax(inputArray);
        assertEquals(expectedSecondMax, foundMax);
    }

    @Test
    public void userCannotFindMaxInArrayWithIdenticalElements() {
        assertThrows(NoSuchElementException.class, () -> numberAndStringHelper.findSecondMax(new int[]{100, 100, 100}));
    }

    @Test
    public void userCannotFindMaxInArrayWithOneElement() {
        assertThrows(IllegalArgumentException.class, () -> numberAndStringHelper.findSecondMax(new int[]{100}));
    }
}
