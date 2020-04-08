package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 * <p>
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 * (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 * links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        ListNode first = list.front;
        ListNode second = first.next;
        ListNode third = second.next;
        list.front = third;
        third.next = second;
        second.next = first;
        first.next = null;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        ListNode first = list.front;
        if (first != null) {
            ListNode cur = first;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = first;
            list.front = first.next;
            first.next = null;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        LinkedIntList res = new LinkedIntList();
        ListNode newARoot = copyListNodes(a.front);
        ListNode newBRoot = copyListNodes(b.front);
        ListNode lastANode = getLastNode(newARoot);
        if (lastANode != null) {
            lastANode.next = newBRoot;
            res.front = newARoot;
        } else {
            res.front = newBRoot;
        }
        return res;
    }

    private static ListNode copyListNodes(ListNode oldListCur) {
        if (oldListCur != null) {
            ListNode newListCur = new ListNode(oldListCur.data);
            newListCur.next = copyListNodes(oldListCur.next);
            return newListCur;
        } else {
            return null;
        }
    }

    private static ListNode getLastNode(ListNode cur) {
        if (cur == null) {
            return null;
        } else if (cur.next == null) {
            return cur;
        } else {
            return getLastNode(cur.next);
        }
    }

}
