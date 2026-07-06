package complex_tasks.task_6_task_manager;

import java.time.LocalDate;

public class Task<T> {
    private T id;
    private Status status;
    private Priority priority;
    private LocalDate createdDate;

    public Task(T id, Status status, Priority priority, LocalDate createdDate) {
        this.id = id;
        this.status = status;
        this.priority = priority;
        this.createdDate = createdDate;
    }

    public T getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
