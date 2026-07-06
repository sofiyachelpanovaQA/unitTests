package complex_tasks.task_6_task_manager;

public enum Priority {
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий");

    private final String priorityName;

    Priority(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getStatusName() {
        return priorityName;
    }
}

