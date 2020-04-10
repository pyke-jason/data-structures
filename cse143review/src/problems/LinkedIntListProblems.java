package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        ListNode temp = list.front.next.next;
        list.front.next.next.next = list.front.next;
        list.front.next.next = list.front;
        list.front.next = null;
        list.front = temp;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front != null && list.front.next != null) {
            ListNode curr = list.front.next;
            ListNode temp = list.front;
            list.front = list.front.next;
            while (curr.next != null) {
                curr = curr.next;
            }
            temp.next = null;
            curr.next = temp;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        LinkedIntList c = new LinkedIntList();
        ListNode temp = c.front;
        if (a.front != null && a.front.next != null) {
            c.front = new ListNode(a.front.data);
            temp = c.front;
            ListNode curr = a.front;
            while (curr.next != null) {
                curr = curr.next;
                temp.next = new ListNode(curr.data);
                temp = temp.next;
            }
        }
        if (b.front != null) {
            if (temp != null) {
                temp.next = b.front;
            } else {
                c.front = b.front;
            }
        }
        return c;
    }
}
