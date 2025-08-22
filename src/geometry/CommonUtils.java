package geometry;

/**
 * Utility class that contains common helper methods.
 */
public class CommonUtils {
    /**
     * Small epsilon value for double comparison.
     */
        public static final double EPSILON = 0.0000001;
    /**
     * Compares two double values for equality.
     * @param num1 the first number.
     * @param num2 the second number.
     * @return true if the numbers are equal within the epsilon range, false otherwise.
     */
    public static boolean isdoubleEqual(double num1, double num2) {
            return Math.abs(num1 - num2) < EPSILON;
        }
}
