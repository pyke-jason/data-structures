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
        Node<T> node = new Node<T>(item, front, front.next);
        front.next.prev = node;
        front.next = node;
        size += 1;
    }

    public void addLast(T item) {
        Node<T> node = new Node<T>(item, back.prev, back);
        back.prev.next = node;
        back.prev = node;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> node = front.next;
        node.next.prev = node.prev;
        node.prev.next = node.next;
        return node.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> node = back.prev;
        node.next.prev = node.prev;
        node.prev.next = node.next;
        return node.value;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        } else if (index > size / 2) {
            Node<T> curr = back.prev;
            for (int i = 0; i < size - index - 1; i++) {
                curr = curr.prev;
            }
            return curr.value;
        } else {
            Node<T> curr = front.next;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            return curr.value;
        }
    }

    public int size() {
        return size;
    }
}
