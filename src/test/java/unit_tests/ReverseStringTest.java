package unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class ReverseStringTest extends NumberAndStringHelperTest {
    /*
    * test cases:
    *   positive:
    *       - "java" -> "avaj"
    *       - "HeLlO" -> "OlLeH"
    *       corner:
    *       - "q" -> "q"
    *       - "" -> ""
    *       - "  " -> "  "
    *   negative:
    *       - null -> null
     */

    public static Stream<Arguments> validStringToReverse() {
        return Stream.of(
                 //      - "java" -> "avaj"
                Arguments.of("java","avaj"),
                //       - "HeLlO" -> "OlLeH"
                Arguments.of("HeLlO","OlLeH"),
                //       - "q" -> "q"
                Arguments.of("q","q"),
                //       - "" -> ""
                Arguments.of("",""),
                //       - "  " -> "  "
                Arguments.of("  ","  ")
        );
    }

    @ParameterizedTest
    @MethodSource("validStringToReverse")
    public void userCanReverseValidString(String initialString, String expectedString) {
        String reversedString = numberAndStringHelper.reverse(initialString);
        assertEquals(expectedString, reversedString);
    }

    @Test
    public void userCanReverseNullString() {
        assertNull(numberAndStringHelper.reverse(null));
    }
}
