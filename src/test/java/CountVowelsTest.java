import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class CountVowelsTest extends NumberAndStringHelperTest {
    /*
    * test cases:
    *   positive:
    *       the string contains only vowels
    *       - "a" -> 1
    *       - "aeiou" - > 5
    *       - "AEIOU" - > 5
    *       the strings contains vowels and consonant
    *       - "hello" -> 2
    *       - "java" - > 2
    *       - HEllo - > 2
    *   negative:
    *       the string doesn't contain vowels
    *       - "bcdgf" - > 0
    *       - "xyz" -> 0
    *       corner:
    *       - null -> IllegalArgumentException
    *       - "" -> 0
    *       - "   " -> 0
     */

    public static Stream<Arguments> validStringsToCountVowels() {
        return Stream.of(
                /*
                 *   positive:
                 *       the string contains only vowels
                 *       - "a" -> 1
                 *       - "aeiou" - > 5
                 *       - "AEIOU" - > 5
                 *       the strings contains vowels and consonant
                 *       - "hello" -> 2
                 *       - "java" - > 2
                 *       - HEllo - > 2
                 */
                Arguments.of("a", 1),
                Arguments.of("aeiou", 5),
                Arguments.of("AEIOU", 5),
                Arguments.of("hello", 2),
                Arguments.of("java", 2),
                Arguments.of("HEllo", 2)
        );
    }

    @ParameterizedTest
    @MethodSource("validStringsToCountVowels")
    public void userCanCountVowelsPositiveTest(String inputString, int count) {
        int countedVowels = numberAndStringHelper.countVowels(inputString);
        assertEquals(count, countedVowels);
    }

    public static Stream<Arguments> invalidStringsToCountVowels() {
        return Stream.of(
                /*
                 *   negative:
                 *       the string doesn't contain vowels
                 *       - "bcdgf" - > 0
                 *       - "xyz" -> 0
                 *       corner:
                 *       - "" -> 0
                 *       - "   " -> 0
                 */
                Arguments.of("bcdgf", 0),
                Arguments.of("xyz", 0),
                Arguments.of("", 0),
                Arguments.of("   ", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidStringsToCountVowels")
    public void userCanCountVowelsNegativeTest(String inputString, int count) {
        int countedVowels = numberAndStringHelper.countVowels(inputString);
        assertEquals(count, countedVowels);
    }

    @Test
    //corner:
    //null -> IllegalArgumentException
    public void userCannotCountVowelsInNullString() {
        assertThrows(IllegalArgumentException.class, () -> numberAndStringHelper.countVowels(null));
    }
}
