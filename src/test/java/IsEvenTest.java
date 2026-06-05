import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class IsEvenTest extends NumberAndStringHelperTest {
    /*
    * test cases
    * positive:
    *   - even number 10 -> true
    *   - even negative number -8 -> true
    *   corner:
    *   - 0 -> true
    * negative:
    *   - uneven number 9 -> false
    *   - uneven negative number -7 -> false
     */

    @ParameterizedTest
    //positive:
    @ValueSource(ints = {10, -8, 0})
    public void userCanCheckEvenNumber(int number) {
        assertTrue(numberAndStringHelper.isEven(number));
    }

    @ParameterizedTest
    //negative:
    @ValueSource(ints = {9, -7})
    public void userCanCheckUnevenNumber(int number) {
        assertFalse(numberAndStringHelper.isEven(number));
    }


}
