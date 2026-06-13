package debug_tasks;

public class DebugTask8 {

    public static void main(String[] args) {
        double a = 0.1 * 3;
        double b = 0.3;

        if (Math.abs(a - b) < 0.000001) {
            System.out.println("Equal");
        } else {
            System.out.println("Not Equal");
        }
    }
}
