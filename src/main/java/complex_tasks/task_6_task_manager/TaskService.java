package complex_tasks.task_6_task_manager;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService<T> {
    Map<T, Task<T>> tasksMap =  new HashMap<>();

    public Map<T, Task<T>> getTasksMap() {
        return Map.copyOf(tasksMap);
    }

    public void addTask(Task<T> task) {
        if (task == null) {throw new IllegalArgumentException("Задача не может быть null");}
        if (task.getId() == null) {throw new IllegalArgumentException("Id не может быть null");}
        if (tasksMap.containsKey(task.getId())) {
            throw new IllegalArgumentException("Задача с данным id уже существует");
        }
        tasksMap.put(task.getId(), task);


    }

    public synchronized void deleteTask(T id) {
        if (id == null || !tasksMap.containsKey(id)) {
            throw new IllegalArgumentException("Задачи по id не найдено");
        }
        tasksMap.remove(id);
    }

    public List<Task<T>> filterByStatus (Status status) {
        return tasksMap.values().stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
    }

    public List<Task<T>> filterByPriority (Priority priority) {
        return tasksMap.values().stream()
                .filter(task -> task.getPriority().equals(priority))
                .toList();
    }

    public List<Task<T>> sortedByDate() {
        return tasksMap.values().stream()
                .sorted(Comparator.comparing(Task::getCreatedDate))
                .toList();
    }
}
