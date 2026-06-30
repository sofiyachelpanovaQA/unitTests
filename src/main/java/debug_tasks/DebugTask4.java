package debug_tasks;

public class DebugTask4 {
    public static void main(String[] args) {
        try {
            System.out.println(isPalindrome("aba"));
        } catch (NullPointerException e) {
            System.out.println("Строка не должна быть пустой");
        }
    }
    public static boolean isPalindrome(String str) {
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
}
