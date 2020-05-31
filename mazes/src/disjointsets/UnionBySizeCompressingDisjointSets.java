package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A quick-union-by-size data structure with path compression.
 *
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    // Stores the index to a value's parent. If it is negative then the item is a parent and
    // the number represents the size of the tree
    List<Integer> pointers;

    // Key: Number, Value: Index of pointers
    Map<T, Integer> indexMapping;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        pointers = new ArrayList<>();
        indexMapping = new HashMap<>();
    }

    @Override
    public void makeSet(T item) {
        if (indexMapping.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        int index = pointers.size();
        pointers.add(-1);
        indexMapping.put(item, index);
    }

    @Override
    public int findSet(T item) {
        if (!indexMapping.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        // path compression
        // every node we visit we point to the root node
        int originalIndex = indexMapping.get(item);
        int rootIndex = getRootIndex(originalIndex);
        setIndexToRoot(originalIndex, rootIndex);
        return rootIndex;
    }

    private int getRootIndex(int curIndex) {
        int value = pointers.get(curIndex);
        if (value < 0) {
            return curIndex;
        } else {
            return getRootIndex(value);
        }
    }

    private void setIndexToRoot(int curIndex, int rootIndex) {
        int value = pointers.get(curIndex);
        if (value >= 0) {
            pointers.set(curIndex, rootIndex);
            setIndexToRoot(value, rootIndex);
        }
    }

    @Override
    public boolean union(T item1, T item2) {
        if (!indexMapping.containsKey(item1) || !indexMapping.containsKey(item2)) {
            throw new IllegalArgumentException();
        }
        // parent will be bigger tree
        int r1Index = findSet(item1);
        int r2Index = findSet(item2);
        if (r1Index != r2Index) {
            int r1Size = pointers.get(r1Index);
            int r2Size = pointers.get(r2Index);
            int newSize = r1Size + r2Size;
            if (r1Size <= r2Size) {
                pointers.set(r2Index, r1Index);
                pointers.set(r1Index, newSize);
            } else {
                pointers.set(r1Index, r2Index);
                pointers.set(r2Index, newSize);
            }
            return true;
        } else {
            return false;
        }
    }
}
