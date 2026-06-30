package complex_tasks.task_2_user_validator;

public class ValidationDisabledException extends RuntimeException {
    public ValidationDisabledException() {
        super("Валидация недоступна, требуется разрешение");
    }
}
