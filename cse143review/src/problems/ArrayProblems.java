package problems;

/**
 * See the spec on the website for example behavior.
 *
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
        if (array.length == 0) {
            return "[]";
        } else {
            String result = "[";
            for (int value : array) {
                result += value + ", ";
            }
            return result.substring(0, result.length() - 2) + "]";
        }
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        int[] result = new int[array.length];
        int index = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            result[index] = array[i];
            index++;
        }
        return result;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        if (array.length != 0) {
            int placeholder = array[array.length - 1];
            for (int i = array.length - 1; i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = placeholder;
        }
    }
}
