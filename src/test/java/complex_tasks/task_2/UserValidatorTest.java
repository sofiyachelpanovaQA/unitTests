package complex_tasks.task_2;

import complex_tasks.task_2_user_validator.InvalidUserException;
import complex_tasks.task_2_user_validator.User;
import complex_tasks.task_2_user_validator.UserValidator;
import complex_tasks.task_2_user_validator.ValidationDisabledException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {
    UserValidator userValidator = new UserValidator();
    /*
     *           1. Тест кейсы для валидации имени:
     *   1. Positive: Валидное имя -> true
     *   2. Negative: Имя с маленькой буквы -> InvalidUserException
     *   3. Corner: Null -> InvalidUserException
     *   4. Валидное имя + глобальный Флаг true -> true
     *   5. Валидное имя + глобальный Флаг false -> ValidationDisabledException
     */
    @Test
    @DisplayName("1. Проверка валидации Имени")
    public void validateNameTest() {
        User user1 = new User("Ivan", 20, "test1@test.ru");
        User user2 = new User("ivan", 20, "test2@test.ru");
        User user3 = new User(null, 20, "test3@test.ru");
        userValidator.setValidationEnabled(true); //поставили флаг true

        assertTrue(userValidator.validateName(user1)); // case 1 + case 4
        assertThrows(InvalidUserException.class, () -> userValidator.validateName(user2)); // case 2
        assertThrows(InvalidUserException.class, () -> userValidator.validateName(user3));  // case 3

        //case 5:
        userValidator.setValidationEnabled(false); //поставили флаг false
        assertThrows(ValidationDisabledException.class, () -> userValidator.validateName(user1));


    }

    /*
     *           2. Тест кейсы для валидации возраста:
     *   1. Positive + corner: 18 -> true
     *                         100 -> true
     *   2. Negative + corner: 17 -> InvalidUserException
     *                         101 -> InvalidUserException
     *   3. Валидный возраст + глобальный Флаг true -> true
     *   4. Валидный возраст + глобальный Флаг false -> ValidationDisabledException
     */
    @Test
    @DisplayName("2. Проверка валидации возраста")
    public void validateAgeTest() {
        User user1 = new User("Ivan", 18, "test1@test.ru");
        User user2 = new User("Anton", 100, "test2@test.ru");
        User user3 = new User("Anna", 17, "test3@test.ru");
        User user4 = new User("Sonya", 101, "test3@test.ru");
        userValidator.setValidationEnabled(true); //поставили флаг true

        assertTrue(userValidator.validateAge(user1)); // case 1.1 + case 3
        assertTrue(userValidator.validateAge(user2)); // case 1.2 + case 3
        assertThrows(InvalidUserException.class, () -> userValidator.validateAge(user3)); // case 2.1
        assertThrows(InvalidUserException.class, () -> userValidator.validateAge(user4)); // case 2.2

        //case 4:
        userValidator.setValidationEnabled(false); //поставили флаг false
        assertThrows(ValidationDisabledException.class, () -> userValidator.validateAge(user1));

    }

    /*
     *           3. Тест кейсы для валидации email:
     *   1. Валидация включена, email корректный -> true
     *                  "test@test.com",
                        "user.name@mail.ru",
                        "user_123@gmail.com",
                        "a-b+c@yandex.ru"
     *   2. Валидация включена, email некорректный → InvalidUserException
     *                  "test",
                        "test@",
                        "@mail.com",
                        "test@mail",
                        "test@@mail.com",
                        "test mail@mail.com"
     *   3. Валидация выключена → ValidationDisabledException
     */

    //Case 1
    @ParameterizedTest
    @DisplayName("3.1 Positive валидация допустимого email")
    @ValueSource(strings = {"test@test.com",
                            "user.name@mail.ru",
                            "user_123@gmail.com",
                            "a-b+c@yandex.ru"})
    public void validateEmailPositiveTest(String email) {
        User user = new User("Ivan", 20, email);
        userValidator.setValidationEnabled(true); //поставили флаг true

        assertTrue(userValidator.validateEmail(user));
    }

    //Case 2
    @ParameterizedTest
    @DisplayName("3.2 Negative валидация недопустимого email")
    @ValueSource(strings = {"test",
                            "test@",
                            "@mail.com",
                            "test@mail",
                            "test@@mail.com",
                            "test mail@mail.com"})
    public void validateEmailNegativeTest(String email) {
        User user = new User("Ivan", 20, email);
        userValidator.setValidationEnabled(true); //поставили флаг true

        assertThrows(InvalidUserException.class, () -> userValidator.validateEmail(user));
    }

    //Case 3
    @Test
    @DisplayName("3.3 Negative валидация при выключенном флаге")
    public void validateEmailWhenValidationDisabledTest() {
        User user = new User("Ivan", 20, "test@test.ru");
        userValidator.setValidationEnabled(false); //поставили флаг false

        assertThrows(ValidationDisabledException.class, () -> userValidator.validateEmail(user));
    }





}
