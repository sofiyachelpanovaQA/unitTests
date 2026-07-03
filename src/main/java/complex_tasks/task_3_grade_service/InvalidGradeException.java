package complex_tasks.task_3_grade_service;

public class InvalidGradeException extends RuntimeException {
    public InvalidGradeException() {
        super("Введенная оценка некорректна");
    }
}
