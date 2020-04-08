package problems;

/**
 * See the spec on the website for example behavior.
 * <p>
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        String res = "[";
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                res += array[i];
            } else {
                res += array[i] + ", ";
            }
        }
        res += "]";
        return res;
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        int[] res = new int[array.length];
        for (int i = 0; i < res.length; i++) {
            res[array.length - 1 - i] = array[i];
        }
        return res;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        if (array.length > 0) {
            int last = array[array.length - 1];
            for (int i = array.length - 2; i >= 0; i--) {
                array[1 + i] = array[i];
            }
            array[0] = last;
        }
    }
}
