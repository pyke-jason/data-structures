package problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        for (String str : list) {
            int count = 0;
            for (String str1 : list) {
                if (str1.equals(str)) {
                    count++;
                    if (count == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> res = new HashMap<>();
        for (String str : m1.keySet()) {
            if (m2.containsKey(str)) {
                int value = m1.get(str);
                if (value == m2.get(str)) {
                    res.put(str, value);
                }
            }
        }
        return res;
    }
}
