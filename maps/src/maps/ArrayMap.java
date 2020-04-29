package maps;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 5;
    private int size;
    SimpleEntry<K, V>[] entries;

    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        for (SimpleEntry<K, V> entry : entries) {
            if (entry != null && (entry.getKey() == key || entry.getKey().equals(key))) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (entries.length <= size) {
            SimpleEntry<K, V>[] temp = this.createArrayOfEntries(entries.length * 2);
            for (int i = 0; i < entries.length; i++) {
                temp[i] = entries[i];
            }
            entries = temp;
        }

        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null
                && (entries[i].getKey() == key
                || entries[i].getKey().equals(key))) {
                V result = entries[i].getValue();
                entries[i] = new SimpleEntry<K, V>(key, value);
                return result;
            }
        }

        entries[size] = new SimpleEntry<K, V>(key, value);
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        V result = null;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null
                && (entries[i].getKey() == key
                || entries[i].getKey().equals(key))) {
                size--;
                result = entries[i].getValue();
                entries[i] = entries[size];
                entries[size] = null;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        Arrays.fill(entries, null);
    }

    @Override
    public boolean containsKey(Object key) {
        for (SimpleEntry<K, V> entry : entries) {
            if (entry != null && (entry.getKey() == key || entry.getKey().equals(key))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new ArrayMapIterator<>(this.entries);
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {

        private final SimpleEntry<K, V>[] entries;
        private int index;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return entries.length > index
                && entries[index] != null
                && entries[index].getKey() != null;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (this.hasNext()) {
                Map.Entry<K, V> entry = entries[index];
                index++;
                return entry;
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
