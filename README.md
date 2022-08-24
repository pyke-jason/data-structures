# Data Structures
Several implementations of data structures & algorithms written in Java for my Data Structures & Algorithms class at the University of Washington.

# deques

Two deque implementations for Java. One is an array-based deque, while the other is linked. This deque implementations have the following operations:
| Signature             | Description                                                                               |
|-----------------------|-------------------------------------------------------------------------------------------|
| void addFirst(T item) | Adds an item of type T to the front of the deque.                                         |
| void addLast(T item)  | Adds an item of type T to the back of the deque.                                          |
| T removeFirst()       | Removes and returns the item at the front of the deque.                                   |
| T removeLast()        | Removes and returns the item at the back of the deque.                                    |
| int size()            | Returns the number of items in the deque.                                                 |
| boolean isEmpty()     | Returns true if deque is empty, false otherwise.                                          |
| T get(int index)      | Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. |

# heap

The priority for each item is extrinsic to the object — that is, rather than relying on some sort of comparison function to decide which item has less priority than another, priorities are manually assigned by passing in numbers along with the items.

## ExtrinsicMinPQ

> `peekMin`, `contains`, `size` and `changePriority` run in $O(log (n))$ time. `add` and `removeMin` run in $O(log (n))$ time except for the rare resize operation.


| Signature                                    | Description                                                      |
|----------------------------------------------|------------------------------------------------------------------|
| void add(T item, double priority)            | Adds an item with the given priority value.                      |
| boolean contains(T item)                     | Returns true if the PQ contains the given item; false otherwise. |
| T peekMin()                                  | Returns the item with least-valued priority.                     |
| T removeMin()                                | Removes and returns the item with least-valued priority.         |
| void changePriority(T item, double priority) | Changes the priority of the given item.                          |
| int size()                                   | Returns the number of items in the PQ.                           |
| boolean isEmpty()                            | Returns true if the PQ is empty, false otherwise.                |

# map

Implementation of a lot of Java's existing map functionality.

| Signature                                    | Description                                                      |
|----------------------------------------------|------------------------------------------------------------------|
| void add(T item, double priority)            | Adds an item with the given priority value.                      |
| boolean contains(T item)                     | Returns true if the PQ contains the given item; false otherwise. |
| T peekMin()                                  | Returns the item with least-valued priority.                     |
| T removeMin()                                | Removes and returns the item with least-valued priority.         |
| void changePriority(T item, double priority) | Changes the priority of the given item.                          |
| int size()                                   | Returns the number of items in the PQ.                           |
| boolean isEmpty()                            | Returns true if the PQ is empty, false otherwise.                |

# mazes

Maze solving using Dijkstra's and maze generation through Kruskal's algorithm by generating a minimum spanning tree (MST).

# seamcarving

A seamcarving implementation for carving images, using sentinel nodes and Dijkstra's for finding seams.         
