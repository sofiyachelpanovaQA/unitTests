import java.util.Arrays;

public class NumberAndStringHelper {

    //метод, который определяет, является ли число чётным
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    // метод, который считает количество гласных букв в строке:
    public int countVowels(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return (int) input.toLowerCase().chars()
                .filter(c -> "aeiou".indexOf(c) != -1)
                .count();
    }

    //метод, который переворачивает строку:
    public String reverse(String input) {
        if (input == null) return null;
        return new StringBuilder(input).reverse().toString();
    }

    //метод, который находит максимальное число в массиве:
    public int findMax(int[] numbers) {
        return Arrays.stream(numbers).max().orElseThrow();
    }

    //метод, который определяет, является ли год високосным:
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    //метод, который находит второе по величине число:
    public int findSecondMax(int[] numbers) {
        return Arrays.stream(numbers).distinct().sorted().skip(numbers.length - 2).findFirst().orElseThrow();
    }

    //метод, который проверяет, является ли строка валидным email:
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }

    //метод, который вычисляет факториал числа:
    public int factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative numbers not allowed");
        return (n == 0) ? 1 : n * factorial(n - 1);
    }

    //метод, который считает количество слов в строке:
    public int countWords(String sentence) {
        return sentence.trim().isEmpty() ? 0 : sentence.split("\\s+").length;
    }

    //метод, который проверяет валидность телефонного номера:
    public boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\+\\d{1,3} \\d{10}");
    }

}
