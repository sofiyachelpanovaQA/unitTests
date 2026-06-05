import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class FactorialTest extends NumberAndStringHelperTest {
    /*
    * positive
    *   1! = 1
    *   5! = 120
    *   7! = 5040
    *   corner:
    *   0! = 1
    * negative:
    *   -1! -> exception
     */

    public static Stream<Arguments> numbersToFactorial() {
        return Stream.of(
                // positive
                //1! = 1
                Arguments.of(1, 1),
                // 5! = 120
                Arguments.of(5, 120),
                // 7! = 5040
                Arguments.of(7, 5040),
                // corner 0! = 1
                Arguments.of(0, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("numbersToFactorial")
    public void userCanFindFactorial(int num, int factorial) {
        int countedFactorial = numberAndStringHelper.factorial(num);
        assertEquals(countedFactorial, factorial);
    }

    @Test
    public void userCannotFindFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> numberAndStringHelper.factorial(-1));
    }
}
