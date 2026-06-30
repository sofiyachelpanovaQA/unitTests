package complex_tasks.task_2_user_validator;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super("Валидация не пройдена для поля: " + message);
    }
}
