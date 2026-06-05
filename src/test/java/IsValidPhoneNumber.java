import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class IsValidPhoneNumber extends NumberAndStringHelperTest {
    /*
    * positive:
        +1 1234567890 -> true
        +12 1234567890 -> true
        +123 1234567890 -> true
    * negative:
    *   12345 -> false
    *   "invalid" -> false
    *   1 1234567890 -> false
    *   +11234567890 -> false
    *   corner:
    *   +7 12345678901 -> false
    *   +7 -> false
    *   "" -> false
    *   null -> NullPointerException
     */

    //positive
    @ParameterizedTest
    @ValueSource(strings = {
            "+1 1234567890",
            "+12 1234567890",
            "+123 1234567890"
    })
    public void userCanCheckValidNumber(String number) {
        assertTrue(numberAndStringHelper.isValidPhoneNumber(number));
    }

    //negative:
    @ParameterizedTest
    @ValueSource(strings = {
            "12345",
            "invalid",
            "1 1234567890",
            "11234567890",
            //corner:
            "+7 12345678901",
            "+7",
            ""
    })
    public void userCanCheckInvalidNumber(String number) {
        assertFalse(numberAndStringHelper.isValidPhoneNumber(number));
    }

    //negative null:
    @Test
    public void userCannotCheckNullNumber() {
        assertThrows(NullPointerException.class, () -> numberAndStringHelper.isValidPhoneNumber(null));
    }
}
