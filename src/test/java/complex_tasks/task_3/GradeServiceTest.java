package complex_tasks.task_3;

import complex_tasks.task_3_grade_service.GradeService;
import complex_tasks.task_3_grade_service.InvalidGradeException;
import complex_tasks.task_3_grade_service.StudentGrade;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GradeServiceTest {


    /*
     *           1. Тест кейсы для проверки метода добавления оценки
     *   1. Positive: Добавить валидную оценку -> оценка добавлена в список, список увеличен на 1
     *   2. Corner: Добавить оценку 0 -> оценка добавлена в список, оценка добавлена в список, список увеличен на 1
     *   3. Negative + corner : Добавить невалидную оценку (-1) -> InvalidGradeException
     *   4. Positive (проверяем другой тип оценки): Double
     *   5. Negative + corner для double: 0.0000001 -> InvalidGradeException
     *   6. Negative: null -> InvalidGradeException
     *   7. Потокобезопасность
     */

    @Test
    @DisplayName("Positive: Добавление валидной оценки")
    public void addGradeCase1() {
        GradeService<Integer> gradeServiceInteger = new GradeService<>();
        StudentGrade<Integer> studentGrade = new StudentGrade<>("Anna", "Математика", 98);

        int actualSize = gradeServiceInteger.getStudentGradeList().size(); // запомнили размер до применения метода
        gradeServiceInteger.addGrade(studentGrade); // добавили оценку
        assertEquals(List.of(studentGrade), gradeServiceInteger.getStudentGradeList()); // проверили что оценка добавлена
        assertEquals(actualSize + 1, gradeServiceInteger.getStudentGradeList().size()); // проверили что список увеличился на 1
        assertEquals(98, gradeServiceInteger.getStudentGradeList().getFirst().getGrade().intValue()); // проверили что оценка та же, что и добавили
    }

    @Test
    @DisplayName("Corner: Добавление оценки 0")
    public void addGradeCase2() {
        GradeService<Integer> gradeServiceInteger = new GradeService<>();
        StudentGrade<Integer> studentGrade = new StudentGrade<>("Anna", "Математика", 0);

        int actualSize = gradeServiceInteger.getStudentGradeList().size();  // запомнили размер до применения метода
        gradeServiceInteger.addGrade(studentGrade); // добавили оценку
        assertEquals(List.of(studentGrade), gradeServiceInteger.getStudentGradeList()); // проверили что оценка добавлена
        assertEquals(actualSize + 1, gradeServiceInteger.getStudentGradeList().size()); // проверили что список увеличился на 1
        assertEquals(0, gradeServiceInteger.getStudentGradeList().getFirst().getGrade().intValue()); // проверили что оценка та же, что и добавили

    }

    @Test
    @DisplayName("Negative + corner : Добавление невалидной оценки (-1)")
    public void addGradeCase3() {
        GradeService<Integer> gradeServiceInteger = new GradeService<>();
        StudentGrade<Integer> studentGrade = new StudentGrade<>("Anna", "Математика", -1);

        int actualSize = gradeServiceInteger.getStudentGradeList().size(); // запомнили размер до применения метода
        assertThrows(InvalidGradeException.class, () -> gradeServiceInteger.addGrade(studentGrade)); // добавили оценку и проверили исключение
        assertEquals(actualSize, gradeServiceInteger.getStudentGradeList().size()); // проверили что список не изменился

    }

    @Test
    @DisplayName("Positive: Добавление double")
    public void addGradeCase4() {
        GradeService<Double> gradeServiceDouble= new GradeService<>();
        StudentGrade<Double> studentGrade = new StudentGrade<>("Anna", "Математика", 2.5);

        int actualSize = gradeServiceDouble.getStudentGradeList().size(); // запомнили размер до применения метода
        gradeServiceDouble.addGrade(studentGrade); // добавили оценку
        assertEquals(List.of(studentGrade), gradeServiceDouble.getStudentGradeList()); // проверили что оценка добавлена
        assertEquals(actualSize + 1, gradeServiceDouble.getStudentGradeList().size()); // проверили что список увеличился на 1
        assertEquals(2.5, gradeServiceDouble.getStudentGradeList().getFirst().getGrade().doubleValue()); // проверили что оценка та же, что и добавили
    }

    @Test
    @DisplayName("Negative + Corner для double")
    public void addGradeCase5() {
        GradeService<Double> gradeServiceDouble= new GradeService<>();
        StudentGrade<Double> studentGrade = new StudentGrade<>("Anna", "Математика", -0.000000001);

        int actualSize = gradeServiceDouble.getStudentGradeList().size(); // запомнили размер до применения метода
        assertThrows(InvalidGradeException.class, () -> gradeServiceDouble.addGrade(studentGrade)); // добавили оценку и проверили исключение
        assertEquals(actualSize, gradeServiceDouble.getStudentGradeList().size()); // проверили что список не изменился
    }

    @Test
    @DisplayName("Negative: grade is null")
    public void addGradeCase6() {
        GradeService<Double> gradeServiceDouble= new GradeService<>();
        StudentGrade<Double> studentGrade = new StudentGrade<>("Anna", "Математика", null);

        int actualSize = gradeServiceDouble.getStudentGradeList().size(); // запомнили размер до применения метода
        assertThrows(InvalidGradeException.class, () -> gradeServiceDouble.addGrade(studentGrade)); // добавили оценку и проверили исключение
        assertEquals(actualSize, gradeServiceDouble.getStudentGradeList().size()); // проверили что список не изменился
    }

    @Test
    @DisplayName("Проверка потокобезопасности добавления")
    public void addThreadSafety() throws InterruptedException {
        GradeService<Integer> gradeServiceInteger= new GradeService<>();
        StudentGrade<Integer> studentGrade = new StudentGrade<>("Anna", "Математика", 10);

        int threads = 1000;
        Thread[] threadArray = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            threadArray[i] = new Thread(() ->
                    gradeServiceInteger.addGrade(studentGrade));
        }

        for (Thread thread : threadArray) {
            thread.start();
        }
        for (Thread thread : threadArray) {
            thread.join();
        }

        assertEquals(threads, gradeServiceInteger.getStudentGradeList().size());
    }

    /*
     *      2. Проверки метода вычисления среднего балла:
     * 1. Вычисление среднего по нескольким оценкам одного предмета. 4, 5, 6 -> 5.0
     * 2. Вычисление среднего по одной оценке. 4 -> 4.0
     * 3. Предмет отсутствует в списке — выбрасывается IllegalArgumentException.
     * 4. Список оценок пуст — выбрасывается IllegalArgumentException.
     * 5. Поиск предмета без учета регистра (equalsIgnoreCase)
     * 6. Наличие нескольких предметов — учитываются только оценки выбранного предмета.
     * 7. Передача null в качестве названия предмета.
     * 8. Корректное вычисление среднего для дробных оценок. 3,25 3,75 4,77 4,81 -> 4,145
     */

    @Test
    @DisplayName("Вычисление среднего по нескольким оценкам одного предмета")
    public void getAverageGradeBySeveralGrades() {
        GradeService<Integer> gradeService = new GradeService<>();

        StudentGrade<Integer> studentGrade1 = new StudentGrade<>("Anna", "Maths", 4);
        StudentGrade<Integer> studentGrade2 = new StudentGrade<>("Ivan", "Maths", 5);
        StudentGrade<Integer> studentGrade3 = new StudentGrade<>("Maria", "Maths", 6);

        List.of(studentGrade1, studentGrade2, studentGrade3).forEach(gradeService::addGrade); // добавили оценки Maths
        assertEquals(5.0, gradeService.getAverageGrade("Maths")); // сверили ОР с ФР
    }

    @Test
    @DisplayName("Вычисление среднего по одной оценке")
    public void getAverageGradeByOneGrade() {
        GradeService<Integer> gradeService = new GradeService<>();

        StudentGrade<Integer> studentGrade1 = new StudentGrade<>("Anna", "Physics", 4);

        gradeService.addGrade(studentGrade1); // добавили оценку Physics
        assertEquals(4.0, gradeService.getAverageGrade("Physics")); // сверили ОР с ФР
    }

    @Test
    @DisplayName("Предмет отсутствует в списке")
    public void getAverageGradeByNonexistSubject() {
        GradeService<Integer> gradeService = new GradeService<>();

        StudentGrade<Integer> studentGrade1 = new StudentGrade<>("Anna", "Physics", 4);
        StudentGrade<Integer> studentGrade2 = new StudentGrade<>("Ivan", "Maths", 5);

        gradeService.addGrade(studentGrade1); // добавили оценку Physics
        gradeService.addGrade(studentGrade2); // добавили оценку Maths
        assertThrows(IllegalArgumentException.class, () -> gradeService.getAverageGrade("Literature")); // проверили что выбрасывается исключение
    }

    @Test
    @DisplayName("Список оценок пуст")
    public void getAverageInEmptyGradeList() {
        GradeService<Integer> gradeService = new GradeService<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> gradeService.getAverageGrade("Literature")); // проверили что выбрасывается исключение
        assertEquals("Не найдено оценок для предмета: Literature", exception.getMessage()); // проверили текст ошибки
    }

    @Test
    @DisplayName("Поиск без учета регистра")
    public void getAverageIgnoreCase() {
        GradeService<Integer> gradeService = new GradeService<>();

        StudentGrade<Integer> studentGrade1 = new StudentGrade<>("Anna", "Maths", 4);
        StudentGrade<Integer> studentGrade2 = new StudentGrade<>("Ivan", "Maths", 5);
        StudentGrade<Integer> studentGrade3 = new StudentGrade<>("Maria", "Maths", 6);

        List.of(studentGrade1, studentGrade2, studentGrade3).forEach(gradeService::addGrade); // добавили оценки Maths
        assertEquals(5.0, gradeService.getAverageGrade("maths")); // сверили ОР с ФР c предметом с маленькой буквы
    }

    @Test
    @DisplayName("Наличие нескольких предметов")
    public void getAverageWithSeveralSubjects() {
        GradeService<Integer> gradeService = new GradeService<>();

        StudentGrade<Integer> studentGrade1 = new StudentGrade<>("Anna", "Maths", 4);
        StudentGrade<Integer> studentGrade2 = new StudentGrade<>("Ivan", "Physics", 5);
        StudentGrade<Integer> studentGrade3 = new StudentGrade<>("Maria", "Literature", 6);
        StudentGrade<Integer> studentGrade4 = new StudentGrade<>("Ivan", "Literature", 7);

        List.of(studentGrade1, studentGrade2, studentGrade3, studentGrade4).forEach(gradeService::addGrade); // добавили оценки разных предметов
        assertEquals(6.5, gradeService.getAverageGrade("Literature")); // сверили ОР с ФР
    }

    @Test
    @DisplayName("Передача null в качестве названия предмета")
    public void getAverageInEmptyGrade() {
        GradeService<Integer> gradeService = new GradeService<>();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gradeService.getAverageGrade(null)); // проверили что выбрасывается исключение
        assertEquals("Предмет не может быть null", exception.getMessage()); // проверили текст ошибки

    }

    @Test
    @DisplayName("Корректное вычисление среднего дробных оценок")
    public void getDoubleAverageGrade() {
        GradeService<Double> gradeService = new GradeService<>();

        StudentGrade<Double> studentGrade1 = new StudentGrade<>("Anna", "Maths", 3.25);
        StudentGrade<Double> studentGrade2 = new StudentGrade<>("Anna", "Maths", 3.75);
        StudentGrade<Double> studentGrade3 = new StudentGrade<>("Ivan", "Maths", 4.77);
        StudentGrade<Double> studentGrade4 = new StudentGrade<>("Maria", "Maths", 4.81);

        List.of(studentGrade1, studentGrade2, studentGrade3, studentGrade4).forEach(gradeService::addGrade); // добавили оценки Maths
        assertEquals(4.145, gradeService.getAverageGrade("Maths")); // сверили ОР с ФР
    }

}
