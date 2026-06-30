package complex_tasks.task_1;

import complex_tasks.task_1_entity_manager.EntityManager;
import complex_tasks.task_1_entity_manager.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class EntityManagerTest {
    /*
    *           1. Тест кейсы для метода добавления:
    *   1. Добавление в пустой список -> Длина списка увеличилась на 1, объект появился в списке
    *   2. Добавление в Непустой список -> Длина списка увеличилась на 1, объект появился в списке
    *   3. Добавление нескольких элементов -> Длина списка увеличилась на n, объекты появился в списке
    *   4. Добавление дубликата -> Длина списка увеличилась на 1, объект появился в списке, кол-во нахождений объекта = 2
     *  5. Проверить потокобезопасность
     */

    Student student1;
    Student student2;
    Student student3;
    Student student4;
    Student student5;
    EntityManager<Student> entityManager;

    @BeforeEach
    void setUp() {
        student1 = new Student(30, "Anton", true);
        student2 = new Student(28, "Anna", true);
        student3 = new Student(18, "Ivan", false);
        student4 = new Student(22, "Alex", false);
        student5 = new Student(30, "Anton", false);
        entityManager = new EntityManager<>();
    }


    @Test
    @DisplayName("1.1 Добавление объекта в пустой список")
    public void addEntityInEmptyList() {
        int actualSize = entityManager.getAll().size();
        assertEquals(0, actualSize); // проверяем что список пустой
        entityManager.addEntity(student1); // добавили
        assertEquals(actualSize + 1, entityManager.getAll().size()); // проверили что размер увеличился на 1
        assertEquals(List.of(student1), entityManager.getAll()); // проверили что список содержит один элемент и это student1
    }

    @Test
    @DisplayName("1.2 Добавление объекта в Непустой список")
    public void addEntityInNotEmptyList() {
        entityManager.addEntity(student1); // делаем список непустым
        entityManager.addEntity(student2); // делаем список непустым
        entityManager.addEntity(student3); // делаем список непустым
        int actualSize = entityManager.getAll().size();
        assertNotEquals(0, actualSize); // проверяем что список непустой
        entityManager.addEntity(student4); // добавили
        assertEquals(actualSize + 1, entityManager.getAll().size()); // проверили что размер увеличился на 1
        assertTrue(entityManager.getAll().contains(student4)); // проверили что список содержит student4
    }

    @Test
    @DisplayName("1.3 Добавление нескольких объектов")
    public void addSeveralEntities() {
        int actualSize = entityManager.getAll().size();
        // добавляем несколько объектов
        entityManager.addEntity(student1);
        entityManager.addEntity(student2);
        entityManager.addEntity(student3);
        assertEquals(actualSize + 3, entityManager.getAll().size()); // проверили что размер увеличился на 3
        assertEquals(List.of(student1, student2, student3), entityManager.getAll()); // проверили что список содержит все объекты
    }

    @Test
    @DisplayName("1.4 Добавление дубликата")
    public void addDuplicate() {
        // добавляем несколько объектов
        entityManager.addEntity(student1);
        entityManager.addEntity(student2);
        entityManager.addEntity(student3);
        int actualSize = entityManager.getAll().size();
        // добавляем дубликат
        entityManager.addEntity(student3);

        assertEquals(actualSize + 1, entityManager.getAll().size()); // проверили что размер увеличился на 1
        assertEquals(2, Collections.frequency(entityManager.getAll(), student3)); // проверили что studen3 - 2 шт. в списке
    }

    @Test
    @DisplayName("1.5 Проверка потокобезопасности добавления")
    public void addThreadSafety() throws InterruptedException {
        int threads = 1000;
        Thread[] threadArray = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            threadArray[i] = new Thread(() ->
                    entityManager.addEntity(student1));
        }

        for (Thread thread : threadArray) {
            thread.start();
        }
        for (Thread thread : threadArray) {
            thread.join();
        }

        assertEquals(threads, entityManager.getAll().size());
    }

    /*
     *           2. Тест кейсы для метода удаления:
     *   1. Успешное удаление -> метод вернул true, элемента больше нет в списке, длина уменьшилась на 1, другие элементы не затронуты (есть в списке)
     *   2. Удаление отсутствующего объекта -> метод вернул false, список не изменился
     *   3. Удаление дубля -> кол-во одинаковых объектов уменьшилось на 1
     *   4. Потокобезопасность метода: несколько потоков пытаются удалить объект -> только один поток вернет true
     */

    @Test
    @DisplayName("2.1 Успешное удаление")
    public void deleteEntity() {
        List.of(student1,student2,student3,student4).forEach(entityManager::addEntity); // создали список
        int actualSize = entityManager.getAll().size(); // запомнили размер
        assertTrue(entityManager.deleteEntity(student3)); //удалили сущность и сразу проверили что метод вернул true
        assertEquals(actualSize - 1, entityManager.getAll().size()); // проверили что длина уменьшилась на 1
        assertEquals(List.of(student1, student2, student4), entityManager.getAll()); //проверили что другие элементы на месте
    }

    @Test
    @DisplayName("2.2 Удаление несуществующего объекта")
    public void deleteNonexistentEntity() {
        List.of(student1,student2,student3).forEach(entityManager::addEntity); // создали список
        int actualSize = entityManager.getAll().size(); // запомнили размер
        assertFalse(entityManager.deleteEntity(student4)); // попытались удалить сущность и сразу проверили что метод вернул false
        assertEquals(List.of(student1, student2, student3), entityManager.getAll()); //проверили что другие элементы на месте
    }

    @Test
    @DisplayName("2.3 Удаление дубля")
    public void deleteDuplicate() {
        List.of(student1,student2,student3,student3,student4).forEach(entityManager::addEntity); // создали список с дублем
        int expectedCount = Collections.frequency(entityManager.getAll(), student3); // запомнили кол-во дублей
        entityManager.deleteEntity(student3); // удалили дубль
        assertEquals(expectedCount - 1, Collections.frequency(entityManager.getAll(), student3)); // проверили что кол-во studen3 уменьшилось на 1
    }

    @Test
    @DisplayName("2.4 Проверка потокобезопасности удаления")
    public void deleteThreadSafety() throws InterruptedException {
         entityManager.addEntity(student1);

         AtomicInteger successRemoves = new AtomicInteger();

         Runnable task = () -> {
             if (entityManager.deleteEntity(student1)) {
                 successRemoves.incrementAndGet();
             }
         };

         Thread thread1 = new Thread(task);
         Thread thread2 = new Thread(task);
         Thread thread3 = new Thread(task);
         thread1.start();
         thread2.start();
         thread3.start();
         thread1.join();
         thread2.join();
         thread3.join();

         assertEquals(1, successRemoves.get());
         assertEquals(0, entityManager.getAll().size());

    }

    /*
     *           3. Тест кейсы для метода фильтрации по возрасту:
     *  возраста: 18, 22, 28, 30
     *   1. Несколько из возрастов в диапазоне: 20 - 29 -> student 2, student 4
     *   2. Все возраста в диапазоне + границы: 18 - 30 -> student1, student2, student3, student4
     *   3. Ни одного в диапазоне + граница нижняя: -> 0 - 17 -> пустой список
     *   4. Ни одного в диапазоне + граница верхняя: -> 31 - 100 -> пустой список
     *   5. minAge = maxAge: 18-18 -> student3
     *   6. Негативный: minAge > maxAge -> IllegalArgumentException
     */

    @Test
    @DisplayName("3. Проверка метода фильтрации по возрасту")
    public void filterByAge() {
        List.of(student1,student2,student3,student4).forEach(entityManager::addEntity); // создали список
        assertEquals(List.of(student2, student4), entityManager.filterByAge(20, 29)); // case 1
        assertEquals(List.of(student1,student2,student3,student4), entityManager.filterByAge(18, 30)); // case 2
        assertEquals(List.of(), entityManager.filterByAge(0, 17)); // case 3
        assertEquals(List.of(), entityManager.filterByAge(31, 100)); // case 4
        assertEquals(List.of(student3), entityManager.filterByAge(18, 18)); // case 5
        assertThrows(IllegalArgumentException.class, () -> entityManager.filterByAge(30, 18)); // case 5

    }


    /*
     *           4. Тест кейсы для метода фильтрации по имени:
     *  возраста: 18, 22, 28, 30
     *   1. Найдено 1 имя: Alex -> student 4
     *   2. Найдено несколько имен: Anton -> student1, student5
     *   3. Не найдено ни одного имени: -> Sonya -> пустой список
     */
    @Test
    @DisplayName("4. Проверка метода фильтрации по имени")
    public void filterByName() {
        List.of(student1,student2,student3,student4,student5).forEach(entityManager::addEntity); // создали список

        assertEquals(List.of(student4), entityManager.filterByName("Alex")); // case 1
        assertEquals(List.of(student1, student5), entityManager.filterByName("Anton")); // case 2
        assertEquals(List.of(), entityManager.filterByName("Sonya")); // case 3

    }

    /*
     *           5. Тест кейсы для метода фильтрации по активности:
     *   1. true -> student1, student2
     *   2. false -> student3, student4, studen5
     *   3. true не найдено: -> пустой список
     */

    @Test
    @DisplayName("5. Проверка метода фильтрации по активности")
    public void filterByActive() {

        List.of(student3,student4,student5).forEach(entityManager::addEntity); // создали список только с активностью false
        assertEquals(List.of(), entityManager.filterByActive(true)); // case 3

        List.of(student1,student2).forEach(entityManager::addEntity); // добавили студентов с активностью true
        assertEquals(List.of(student1,student2), entityManager.filterByActive(true)); // case 1
        assertEquals(List.of(student3, student4, student5), entityManager.filterByActive(false)); // case 2

    }






}
