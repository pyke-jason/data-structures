package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        size = 0;
        front = new Node<T>(null);
        back = new Node<T>(null);
        front.next = back;
        back.prev = front;
    }

    public void addFirst(T item) {
        size += 1;
        Node<T> frontNext = front.next;
        Node<T> newNode = new Node<T>(item);
        newNode.next = frontNext;
        front.next = newNode;
        frontNext.prev = newNode;
        newNode.prev = front;
    }

    public void addLast(T item) {
        size += 1;
        Node<T> backPrev = back.prev;
        Node<T> newNode = new Node<T>(item);
        newNode.prev = backPrev;
        back.prev = newNode;
        backPrev.next = newNode;
        newNode.next = back;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> res = front.next;
        Node<T> resNext = res.next;
        front.next = resNext;
        resNext.prev = front;
        return res.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> res = back.prev;
        Node<T> resPrev = res.prev;
        back.prev = resPrev;
        resPrev.next = back;
        return res.value;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> curNode = front.next;
        int count = 0;
        while (count < index) {
            curNode = curNode.next;
            count++;
        }
        return curNode.value;
    }

    public int size() {
        return size;
    }
}
