package debug_tasks;

public class DebugTask6 {
    public static void main(String[] args) {
        countdown(5);
    }
    public static void countdown(int n) {
        while (n > 0) {
            System.out.println(n);
            n--;
        }

    }
}

