import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class IsLeapYearTest extends NumberAndStringHelperTest {
    /*
    * test cases:
    *   positive:
    *       - 2020 -> true
    *       - 2000 -> true
    *       - 1600 -> true
    *   negative:
    *       - 2026 -> false
    *       - 2025 -> false
    *       corner:
    *       - 1700 -> false
    *       - 1900 -> false
    *       - 0 -> false
    *
    *
     */

    @ParameterizedTest
    // positive:
    @ValueSource(ints = {2020, 2000, 1600})
    public void userCanCheckLeapYear(int year) {
        assertTrue(numberAndStringHelper.isLeapYear(year));
    }

    @ParameterizedTest
    // negative:
    @ValueSource(ints = {2026, 2025, 1700, 1900, 0}) // нужна доработка в методе для обработки 0, так как такого года не существует
    public void userCanCheckNotLeapYear(int year) {
        assertFalse(numberAndStringHelper.isLeapYear(year));
    }
 }
