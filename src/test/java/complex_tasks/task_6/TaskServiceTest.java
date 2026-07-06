package complex_tasks.task_6;

import complex_tasks.task_6_task_manager.Priority;
import complex_tasks.task_6_task_manager.Status;
import complex_tasks.task_6_task_manager.Task;
import complex_tasks.task_6_task_manager.TaskService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TaskServiceTest {
    /*
    * Проверки метода добавления задачи
    * 1. Добавление задачи в пустой список -> задача есть в списке, размер списка = 1
    * 2. Добавление задачи в непустой список -> задача есть в списке, размер списка n + 1
    * 3. Добавление задачи с неуникальным id -> IllegalArgumentException c сообщением "Задача с данным id уже существует"
    * 4. Добавление задачи с null id -> IllegalArgumentException c сообщением "Id не может быть null"
    * 5. Добавление null задачи -> IllegalArgumentException c сообщением "Задача не может быть null"
    * 6. Добавление задачи с другим типом (UUID)
     */

    @Test
    @DisplayName("Добавление задачи в пустой список")
    public void addTaskInEmptyList() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));

        taskService.addTask(task1);
        assertEquals(task1, taskService.getTasksMap().get(task1.getId())); // задача есть в списке
        assertEquals(1, taskService.getTasksMap().size()); // размер списка = 1
    }

    @Test
    @DisplayName("Добавление задачи в непустой список")
    public void addTaskInNonEmptyList() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(2, Status.NEW, Priority.MEDIUM, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // сделали список непустым
        int sizeBefore = taskService.getTasksMap().size(); // запомнили размер

        taskService.addTask(task2);
        assertEquals(task2, taskService.getTasksMap().get(task2.getId())); // задача есть в списке
        assertEquals(sizeBefore + 1, taskService.getTasksMap().size()); // размер списка = N + 1
    }

    @Test
    @DisplayName("Добавление задачи с неуникальным id")
    public void addTaskWithNonuniqueId() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(1, Status.NEW, Priority.MEDIUM, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // сделали список непустым
        int sizeBefore = taskService.getTasksMap().size(); // запомнили размер

        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskService.addTask(task2)); // выбрасывается исключение
        assertEquals("Задача с данным id уже существует", exception.getMessage()); // сообщение исключения
        assertFalse(taskService.getTasksMap().containsValue(task2)); // задачи нет в списке
        assertEquals(sizeBefore, taskService.getTasksMap().size()); // размер списка не изменился
    }

    @Test
    @DisplayName("Добавление null задачи")
    public void addNullTask() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskService.addTask(task1)); // выбрасывается исключение
        assertEquals("Задача не может быть null", exception.getMessage()); // сообщение исключения
    }

    @Test
    @DisplayName("Добавление задачи с null id")
    public void addTaskWithNullId() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(null, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskService.addTask(task1)); // выбрасывается исключение
        assertEquals("Id не может быть null", exception.getMessage()); // сообщение исключения
    }

    @Test
    @DisplayName("Добавление задачи с другим типом (UUID)")
    public void addTaskWithUuidId() {
        TaskService<UUID> taskService = new TaskService<>();
        Task<UUID> task1 = new Task<>(UUID.randomUUID(), Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));

        taskService.addTask(task1);
        assertEquals(task1, taskService.getTasksMap().get(task1.getId())); // задача есть в списке
        assertEquals(1, taskService.getTasksMap().size()); // размер списка = 1
    }

    /*
     * Проверки метода удаления задачи
     * 1. Удаление последней задачи -> задачи нет в списке, список пуст
     * 2. Удаление не последней задачи -> задачи нет в списке, размер списка n - 1
     * 3. Удаление задачи с несуществующим id -> IllegalArgumentException c сообщением "Задачи по id не найдено"
     * 4. Удаление задачи с другим типом (UUID)
     *
     */

    @Test
    @DisplayName("Удаление задачи в пустом списке")
    public void deleteLastTask() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // сделали список непустым с 1 элементом

        taskService.deleteTask(1);
        assertFalse(taskService.getTasksMap().containsKey(1));
        assertEquals(0, taskService.getTasksMap().size());
    }

    @Test
    @DisplayName("Удаление не последней задачи")
    public void deleteNotLastTask() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(2, Status.NEW, Priority.MEDIUM, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1);
        taskService.addTask(task2);
        int sizeBefore = taskService.getTasksMap().size();

        taskService.deleteTask(1);
        assertFalse(taskService.getTasksMap().containsKey(1));
        assertEquals(sizeBefore - 1, taskService.getTasksMap().size());
    }

    @Test
    @DisplayName("Удаление задачи с несуществующим id")
    public void deleteNonExistId() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(2, Status.NEW, Priority.MEDIUM, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1);
        taskService.addTask(task2);
        int sizeBefore = taskService.getTasksMap().size();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(3)); // проверили исключение
        assertEquals("Задачи по id не найдено", exception.getMessage()); // текст сообщения
        assertEquals(sizeBefore, taskService.getTasksMap().size()); // список не изменился
    }

    @Test
    @DisplayName("Удаление задачи с другим типом (UUID)")
    public void deleteTaskWithUuidId() {
        TaskService<UUID> taskService = new TaskService<>();
        UUID randomUuid1 = UUID.randomUUID();
        UUID randomUuid2 = UUID.randomUUID();
        Task<UUID> task1 = new Task<>(randomUuid1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<UUID> task2 = new Task<>(randomUuid2, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1);
        taskService.addTask(task2); // создали список задач

        int sizeBefore = taskService.getTasksMap().size(); // запомнили размер до удаления

        taskService.deleteTask(randomUuid1); // удалили задачу по id
        assertFalse(taskService.getTasksMap().containsKey(randomUuid1)); // задачи нет в списке
        assertEquals(sizeBefore - 1, taskService.getTasksMap().size()); // длина списка уменьшилась на 1
    }

    /*
     * Проверки метода фильтрации по статусу задачи
     * 1. Фильтрация списка из 1 задачи
     * 2. Фильтрация списка из нескольких задач
     * 3. Не найдено задач по статусу
     * 4. Все задачи в списке проходят по фильтру
     *
     */

    @Test
    @DisplayName("Фильтрация по статусу списка из 1 задачи")
    public void filterListWithOneElementByStatus() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // создали список задач

        List<Task<Integer>> filteredTaskList = taskService.filterByStatus(Status.NEW);
        assertEquals(List.of(task1), filteredTaskList); // проверили что 1 задача попала в отфильтрованный список
    }

    @Test
    @DisplayName("Фильтрация по статусу списка из нескольких задач")
    public void filterListWithSeveralElementByStatus() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task3 = new Task<>(3, Status.DONE, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task4 = new Task<>(4, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task5 = new Task<>(5, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // создали список задач
        taskService.addTask(task2); // создали список задач
        taskService.addTask(task3); // создали список задач
        taskService.addTask(task4); // создали список задач
        taskService.addTask(task5); // создали список задач

        List<Task<Integer>> filteredTaskList = taskService.filterByStatus(Status.NEW);
        assertEquals(List.of(task1, task4), filteredTaskList); // проверили что 2 задачи попали в отфильтрованный список
    }

    @Test
    @DisplayName("Не найдено задач по статусу")
    public void statusNotFound() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task3 = new Task<>(3, Status.DONE, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task4 = new Task<>(4, Status.DONE, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task5 = new Task<>(5, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // создали список задач
        taskService.addTask(task2); // создали список задач
        taskService.addTask(task3); // создали список задач
        taskService.addTask(task4); // создали список задач
        taskService.addTask(task5); // создали список задач

        List<Task<Integer>> filteredTaskList = taskService.filterByStatus(Status.NEW);
        assertTrue(filteredTaskList.isEmpty()); // результатов не найдено
    }

    @Test
    @DisplayName("Все задачи в списке проходят по фильтру")
    public void allTasksFound() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task3 = new Task<>(3, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // создали список задач
        taskService.addTask(task2); // создали список задач
        taskService.addTask(task3); // создали список задач
        int sizeBefore = taskService.getTasksMap().size(); // запомнили размер

        List<Task<Integer>> filteredTaskList = taskService.filterByStatus(Status.IN_PROGRESS);
        assertEquals(sizeBefore, filteredTaskList.size()); // проверили что размер совпадает
        assertEquals(List.of(task1, task2, task3), filteredTaskList); // проверили результат фильтра
    }

    /*
     * Проверки метода фильтрации по приоритету задачи
     * 1. Фильтрация списка из 1 задачи
     * 2. Фильтрация списка из нескольких задач
     * 3. Не найдено задач по статусу
     * 4. Все задачи в списке проходят по фильтру
     *
     */

    @Test
    @DisplayName("Фильтрация по приоритету списка из 1 задачи")
    public void filterListWithOneElementByPriority() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // создали список задач

        List<Task<Integer>> filteredTaskList = taskService.filterByPriority(Priority.HIGH);
        assertEquals(List.of(task1), filteredTaskList); // проверили что 1 задача попала в отфильтрованный список
    }

    @Test
    @DisplayName("Фильтрация по приоритету списка из нескольких задач")
    public void filterListWithSeveralElementByPriority() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.MEDIUM, LocalDate.of(2026, 6, 10));
        Task<Integer> task3 = new Task<>(3, Status.DONE, Priority.LOW, LocalDate.of(2026, 6, 10));
        Task<Integer> task4 = new Task<>(4, Status.NEW, Priority.LOW, LocalDate.of(2026, 6, 10));
        Task<Integer> task5 = new Task<>(5, Status.IN_PROGRESS, Priority.MEDIUM, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // создали список задач
        taskService.addTask(task2); // создали список задач
        taskService.addTask(task3); // создали список задач
        taskService.addTask(task4); // создали список задач
        taskService.addTask(task5); // создали список задач

        List<Task<Integer>> filteredTaskList = taskService.filterByPriority(Priority.LOW);
        assertEquals(List.of(task3, task4), filteredTaskList); // проверили что 2 задачи попали в отфильтрованный список
    }

    @Test
    @DisplayName("Не найдено задач по статусу")
    public void priorityNotFound() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task3 = new Task<>(3, Status.DONE, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task4 = new Task<>(4, Status.DONE, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task5 = new Task<>(5, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // создали список задач
        taskService.addTask(task2); // создали список задач
        taskService.addTask(task3); // создали список задач
        taskService.addTask(task4); // создали список задач
        taskService.addTask(task5); // создали список задач

        List<Task<Integer>> filteredTaskList = taskService.filterByPriority(Priority.LOW);
        assertTrue(filteredTaskList.isEmpty()); // результатов не найдено
    }

    @Test
    @DisplayName("Все задачи в списке проходят по фильтру")
    public void allTasksFoundWithPriority() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        Task<Integer> task3 = new Task<>(3, Status.IN_PROGRESS, Priority.HIGH, LocalDate.of(2026, 6, 10));
        taskService.addTask(task1); // создали список задач
        taskService.addTask(task2); // создали список задач
        taskService.addTask(task3); // создали список задач
        int sizeBefore = taskService.getTasksMap().size(); // запомнили размер

        List<Task<Integer>> filteredTaskList = taskService.filterByPriority(Priority.HIGH);
        assertEquals(sizeBefore, filteredTaskList.size()); // проверили что размер совпадает
        assertEquals(List.of(task1, task2, task3), filteredTaskList); // проверили результат фильтра
    }

    // проверка метода сортировки по дате
    @Test
    @DisplayName("Сортировка по дате")
    public void sortedByDate() {
        TaskService<Integer> taskService = new TaskService<>();
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.HIGH, LocalDate.of(2026, 1, 10)); // 1
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.MEDIUM, LocalDate.of(2026, 5, 10)); // 4
        Task<Integer> task3 = new Task<>(3, Status.DONE, Priority.LOW, LocalDate.of(2026, 6, 10)); // 5
        Task<Integer> task4 = new Task<>(4, Status.NEW, Priority.LOW, LocalDate.of(2026, 2, 20)); // 3
        Task<Integer> task5 = new Task<>(5, Status.IN_PROGRESS, Priority.MEDIUM, LocalDate.of(2026, 2, 10)); // 2
        taskService.addTask(task1); // создали список задач
        taskService.addTask(task2); // создали список задач
        taskService.addTask(task3); // создали список задач
        taskService.addTask(task4); // создали список задач
        taskService.addTask(task5); // создали список задач

        assertEquals(List.of(task1, task5, task4, task2, task3), taskService.sortedByDate());
    }

}
