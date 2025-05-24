/**
 * A utility class for shared core and common functionality used across the application.
 */
public class Common {

    /**
     * A small threshold value used for approximate comparisons of double values.
     */
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
     *         {@code b} is less than {@code THRESHOLD}, otherwise {@code false}
     * @throws NullPointerException if either {@code a} or {@code b} is
     *                              {@code null}
     */
    public static boolean thresholdComparison(Double a, Double b) {
        return Math.abs(a - b) < THRESHOLD;
    }
}
