package complex_tasks.task_6_task_manager;

public enum Status {
    NEW("Новая"),
    IN_PROGRESS("В процессе"),
    DONE("Готово");

    private final String statusName;

    Status(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}

