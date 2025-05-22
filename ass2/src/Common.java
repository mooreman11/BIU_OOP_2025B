/**
 *  Class for shared core and common functionality
 */
public class Common {
    static final double THRESHOLD = 0.00001d;
    /**
     * Compares two {@code Double} values to determine if they are approximately
     * equal within a specified threshold.
     *
     * <p>The comparison is performed by calculating the absolute difference
     * between the two values and checking if it is less than the constant
     * {@code THRESHOLD}.
     *
     * @param a the first {@code Double} value to compare
     * @param b the second {@code Double} value to compare
     * @return {@code true} if the absolute difference between {@code a} and
     * {@code b} is less than {@code THRESHOLD}, otherwise {@code false}
     * @throws NullPointerException if either {@code a} or {@code b} is
     * {@code null}
     */
    public static boolean thresholdComparison(Double a, Double b) {
        return Math.abs(a - b) < THRESHOLD;
    }

    /**
     * Converts the command-line arguments to an array of integers representing the ball sizes.
     *
     * @param args the command-line arguments containing ball sizes as strings.
     * @return an array of integers representing the ball sizes.
     */
    public static int[] convertArgstoInt(String[] args) {
        int[] ints = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            ints[i] = Integer.parseInt(args[i]);
        }
        return ints;
    }
}
