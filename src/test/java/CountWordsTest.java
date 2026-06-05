import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class CountWordsTest extends NumberAndStringHelperTest {
    /*
    * positive:
    *   "У лукоморья дуб зеленый" -> 4
    *   "У   лукоморья  дуб зеленый" -> 4
    *   corner:
    *   "q" -> 1
    *   "" -> 0
    * negative:
    *   null -> exception
     */

    public static Stream<Arguments> validStringsToCountWords() {
        return Stream.of(
                Arguments.of("У лукоморья дуб зеленый", 4),
                Arguments.of("У   лукоморья  дуб зеленый", 4),
                Arguments.of("У", 1),
                Arguments.of("", 0)
        );
    }
    //positive:
    @ParameterizedTest
    @MethodSource("validStringsToCountWords")
    public void userCanCountWordsInSentence(String sentence, int expectedCountedWords) {
        int countedWords = numberAndStringHelper.countWords(sentence);
        assertEquals(expectedCountedWords, countedWords);
    }

    //negative:
    @Test
    public void userCannotCountWordsInNullString() {
        assertThrows(NullPointerException.class, () -> numberAndStringHelper.countWords(null));
    }

}
