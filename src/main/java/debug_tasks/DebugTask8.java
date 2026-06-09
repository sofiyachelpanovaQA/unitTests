package debug_tasks;

public class DebugTask8 {

    static double roundNum(double num) {
        return (double) Math.round(num * 100) / 100;
    }

    public static void main(String[] args) {
        double a = 0.1 * 3;
        double b = 0.3;

        if ((roundNum(a)) == (roundNum(b))) {
            System.out.println("Equal");
        } else {
            System.out.println("Not Equal");
        }
    }
}
