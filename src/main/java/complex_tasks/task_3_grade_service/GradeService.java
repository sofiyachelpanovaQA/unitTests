package complex_tasks.task_3_grade_service;

import java.util.ArrayList;
import java.util.List;

public class GradeService<T extends Number> {
    private List<StudentGrade<T>> studentGradeList = new ArrayList<>();

    public List<StudentGrade<T>> getStudentGradeList() {
        return List.copyOf(studentGradeList);
    }

    public synchronized void addGrade(StudentGrade<T> grade) {
        if (grade.getGrade() != null && grade.getGrade().doubleValue() >= 0)  {
            studentGradeList.add(grade);
        } else throw new InvalidGradeException();
    }

    public double getAverageGrade(String subject) {
        if (subject != null) {
            return studentGradeList.stream()
                    .filter(grade -> grade.getSubject().equalsIgnoreCase(subject))
                    .mapToDouble(grade -> grade.getGrade().doubleValue())
                    .average()
                    .orElseThrow(() ->
                            new IllegalArgumentException("Не найдено оценок для предмета: " + subject));
        } throw new IllegalArgumentException("Предмет не может быть null");
    }
}
