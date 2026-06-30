package unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class IsValidEmailTest extends NumberAndStringHelperTest {
    /*
    * Test cases:
    * positive (true):
    *   user@test.com
    *   user123@test.com
    *   user_name@test.com
    *   user.name@test.com
    *   user-name@test.com
    *   user@mail.test.com
    *   user@mail-test.com
    *   John.Test@TEST.COM
    *   --corner:
    *   a@b.cc
    *   aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@test.com
    *
    * negative (false):
    *   usertest.com
    *   user@
    *   @test.com
    *   user@test
    *   user@test.r
    *
    *   null
    *
     */

    @ParameterizedTest
    @ValueSource(strings = {
            //positive
            "user@test.com",
            "user123@test.com",
            "user_name@test.com",
            "user.name@test.com",
            "user-name@test.com",
            "user@mail.test.com",
            "user@mail-test.com",
            "John.Test@TEST.COM",
            //corner
            "a@b.cc",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@test.com"
    })
    public void userCanCreateValidEmail(String email) {
        assertTrue(numberAndStringHelper.isValidEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            //negative
            "usertest.com",
            "user@",
            "@test.com",
            "user@test",
            "user@test.r"
    })
    public void userCannotCreateInvalidEmail(String email) {
        assertFalse(numberAndStringHelper.isValidEmail(email));
    }

    //negative 'null'
    @Test
    public void userCannotCreateNullEmail() {
        assertFalse(numberAndStringHelper.isValidEmail(null));
    }

}
