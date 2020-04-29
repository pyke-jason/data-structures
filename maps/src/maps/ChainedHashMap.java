package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    // TODO: define reasonable default values for each of the following three fields
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = .75;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 16;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 3;

    /*
    Warning:
    DO NOT rename this `chains` field or change its type.
    We will be inspecting it in our Gradescope-only tests.

    An explanation of this field:
    - `chains` is the main array where you're going to store all of your data (see the [] square bracket notation)
    - The other part of the type is the AbstractIterableMap<K, V> -- this is saying that `chains` will be an
    array that can store an AbstractIterableMap<K, V> object at each index.
       - AbstractIterableMap represents an abstract/generalized Map. The ArrayMap you wrote in the earlier part
       of this project qualifies as one, as it extends the AbstractIterableMap class.  This means you can
       and should be creating ArrayMap objects to go inside your `chains` array as necessary. See the instructions on
       the website for diagrams and more details.
        (To jump to its details, middle-click or control/command-click on AbstractIterableMap below)
     */
    AbstractIterableMap<K, V>[] chains;

    private int size;
    private int initialChainCount;
    private int chainInitialCapacity;
    private double resizingLoadFactorThreshold;
    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        size = 0;
        this.initialChainCount = initialChainCount;
        this.chainInitialCapacity = chainInitialCapacity;
        this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;
        this.chains = this.createArrayOfChains(initialChainCount);
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     * <p>
     * Note that each element in the array will initially be null.
     * <p>
     * Note: You do not need to modify this method.
     *
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     * <p>
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     * <p>
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        int index = getHashIndex(key, chains.length);
        if (chains[index] != null) {
            return chains[index].get(key);
        } else {
            return null;
        }
    }

    private int getHashIndex(Object key, int chainSize) {
        if (key == null) {
            return 0;
        }
        int hashCode = key.hashCode();
        if (hashCode >= 0) {
            return hashCode % chainSize;
        } else {
            return chainSize + hashCode % -chainSize;
        }
    }

    @Override
    public V put(K key, V value) {
        if (size >= resizingLoadFactorThreshold * chains.length) {
            resizeArrayOfChains();
        }
        int index = getHashIndex(key, chains.length);
        if (chains[index] == null) {
            chains[index] = createChain(chainInitialCapacity);
        }
        size++;
        return chains[index].put(key, value);
    }

    private void resizeArrayOfChains() {
        AbstractIterableMap<K, V>[] temp = createArrayOfChains(chains.length * 2);
        for (AbstractIterableMap<K, V> chain : chains) {
            if (chain != null) {
                for (Entry<K, V> map : chain) {
                    K key = map.getKey();
                    V value = map.getValue();
                    int index = getHashIndex(key, temp.length);
                    if (temp[index] == null) {
                        temp[index] = createChain(chainInitialCapacity);
                    }
                    temp[index].put(key, value);
                }
            }
        }
        chains = temp;
    }

    @Override
    public V remove(Object key) {
        int index = getHashIndex(key, chains.length);
        if (chains[index] != null) {
            if (!chains[index].containsKey(key)) {
                return null;
            }
            V result = chains[index].remove(key);
            size--;
            if (chains[index].size() == 0) {
                chains[index] = null;
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < chains.length; i++) {
            if (chains[i] != null) {
                chains[i] = null;
            }
        }
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = getHashIndex(key, chains.length);
        if (chains[index] != null) {
            return chains[index].containsKey(key);
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new ChainedHashMapIterator<>(chains);
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        private int index;
        private Iterator<Entry<K, V>> current;

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            index = -1;
        }

        @Override
        public boolean hasNext() {
            if (current != null && current.hasNext()) {
                return true;
            }
            for (int i = index + 1; i < chains.length; i++) {
                if (chains[i] != null && chains[i].size() > 0) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (current != null && current.hasNext()) {
                return current.next();
            }
            index++;
            while (index < chains.length) {
                AbstractIterableMap<K, V> cur = chains[index];
                if (cur != null && cur.size() > 0) {
                    current = cur.iterator();
                    return current.next();
                }
                index++;
            }
            current = null;
            throw new NoSuchElementException();
        }
    }
}
