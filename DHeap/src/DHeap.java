import java.util.Arrays;
import java.util.List;

//TODO: add our info

//TODO: add a case for runtime complexity where d=1 ??

/**
 * This class implements a heap structure over an internal array. The heap
 * is a D-ary heap, meaning that each node has d children.
 */
public class DHeap {

    /**
     * Holds the number of elements currently in the heap.
     */
    private int size;
    /**
     * Holds the maximum number of elements possible for the heap to hold.
     */
    private int max_size;
    /**
     * The number of children each node in the d-heap structure has.
     */
    private int d;
    /**
     * The actually members of the heap.
     */
    private DHeap_Item[] array;
    /**
     * Used for testing comparisons inside the heap, for measurements.
     */
    int compare_count; // Package local for testing

    /**
     * Constructor for the DHeap class.
     *
     * <pre>
     * Runtime: O(max_size)
     * </pre>
     *
     * @param m_d    The number of children for each node in the heap
     * @param m_size The maximum size the heap might get
     */
    DHeap(int m_d, int m_size) {
        assert m_d >= 1;
        assert m_size >= 0;
        max_size = m_size;
        d = m_d;
        array = new DHeap_Item[max_size];
        size = 0;
        compare_count = 0;
    }

    // For testing
    /**
     * Returns a list of all the items in the heap.
     *
     * <pre>
     * Runtime: O(n)
     * </pre>
     *
     * @return All the items in the heap, as a list
     *
     */
    List<DHeap_Item> getItems() {
        return Arrays.asList(array).subList(0, size);
    }

    /**
     * Returns the size of the heap.
     *
     * <pre>
     * Runtime: O(1)
     * </pre>
     *
     * @return The current number of elements in the heap
     */
    public int getSize() {
        return size;
    }

    /**
     * The function builds a new heap from the given array.
     * Previous data of the heap will be erased.
     *
     * <pre>
     * precondition: array1.length <= max_size
     * postcondition: isHeap(), size = array.length()
     * Runtime: O(n)
     * </pre>
     *
     * @param array1 An array of all the items to insert into the heap
     */
    public void arrayToHeap(DHeap_Item[] array1) {
        assert array1.length <= max_size;
        size = array1.length;
        for (int i = 0; i < size; i++) {
            setItem(i, array1[i]);
        }
        for (int i = size; i < max_size; i++) {
            // Clear old values to allow GC to collect them.
            array[i] = null;
        }
        compare_count = 0;
        heapify();
    }

    /**
     * Make a heap from an array of numbers. Erases the current contents of
     * the heap.
     *
     * <pre>
     * precondition: numbers.length <= max_size
     * postcondition: isHeap(), size = array.length()
     * Runtime: O(n)
     * </pre>
     *
     * @param numbers The numbers to insert into the heap
     */
    public void arrayToHeap(int[] numbers) {
        DHeap_Item[] items = new DHeap_Item[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            items[i] = newItem(numbers[i]);
        }
        arrayToHeap(items);
    }

    /**
     * Make a heap from a list of Integers. Erases the current contents of
     * the heap.
     *
     * <pre>
     * precondition: numbers.size() <= max_size
     * postcondition: isHeap(), size = array.length()
     * Runtime: O(n)
     * </pre>
     *
     * @param numbers The numbers to insert into the heap
     */
    public void arrayToHeap(List<Integer> numbers) {
        DHeap_Item[] items = new DHeap_Item[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            items[i] = newItem(numbers.get(i));
        }
        arrayToHeap(items);
    }

    /**
     * Makes sure the internal array is structured like a heap, moving elements
     * where necessary.
     *
     * <pre>
     * Runtime: O(n)
     * </pre>
     */
    private void heapify() {
        if (size <= 1) {
            return;
        }
        int last_inner_node_index = parent(size - 1);
        for (int i = last_inner_node_index; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    /**
     * Prints the heap as a tree. Used internally for testing.
     *
     * <pre>
     * Runtime: O(n)
     * </pre>
     */
    void printTree() {
        if (size > 0) {
            printTree(0, "", true);
        } else {
            System.out.println("empty");
        }
    }

    /**
     * The internal method that prints a part of the heap as a subtree.
     *
     * <pre>
     * Note: adapted from http://stackoverflow.com/a/8948691
     * Runtime: O(n), n = sub-heap size.
     * </pre>
     *
     * @param i      The index from which to start printing
     * @param prefix The prefix used in order to align all all the prints
     * @param isTail Says weather the current node is it's father's last child
     */
    private void printTree(int i, String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "+-- " : "+-- ") +
                array[i].getKey());

        if (hasChildren(i)) {
            int start = child(i, 1);
            int end = lastChildIndex(i);
            for (int j = start; j < end; j++) {
                printTree(j, prefix + (isTail ? "    " : "|   "), false);
            }
            printTree(lastChildIndex(i),
                    prefix + (isTail ? "    " : "|   "), true);
        }
    }

    /**
     * public boolean isHeap()
     * The function returns true if and only if the D-ary tree rooted at
     * array[0] satisfies the heap property or size == 0.
     *
     * <pre>
     * Runtime: O(n)
     * </pre>
     *
     * @return If the array satisfies the properties of a D-ary tree
     */
    public boolean isHeap() {
        for (int i = size - 1; i > 0; i--) {
            if (array[i] == null || array[parent(i)] == null ||
                array[i].getKey() < array[parent(i)].getKey()) {
                return false;
            }
        }
        return true;
    }

    /**
     * An internal method, used to assert the internal array satisfies the
     * properties of a D-ary heap, and a few more internal tests.
     *
     * <pre>
     * Runtime: O(n)
     * </pre>
     */
    void checkHeap() {
        assert size >= 0 && size <= max_size;
        assert isHeap();
        for (int i = 0; i < size; i++) {
            assert array[i].getPos() == i;
        }
    }

    /**
     * Computes the index of the parent for the heap element at
     * the given index.
     *
     * <pre>
     * precondition: i > 0
     * Runtime: O(1)
     * </pre>
     * 
     * @param i The index of the element who's parent is requested
     * @return The index of the parent for the given element
     */
    public int parent(int i) {
        assert i > 0;
        return (i - 1) / d;
    }

    /**
     * Computes the index of the k-th child of
     * node i in a complete D-ary tree stored in an array. 1 <= k <= d.
     * Note that indices of arrays in Java start from 0.
     *
     * <pre>
     * precondition: i >= 0
     * Runtime: O(1)
     * </pre>
     * 
     * @param i The node who's child is requested
     * @param k The requested child's index in relations to the parent
     * @return The index of the requested child
     */
    public int child(int i, int k) {
        assert i >= 0;
        assert k >= 1 && k <= d;
        return (d * i) + k;
    }

    /**
     * Returns the number of children a specific node has.
     *
     * <pre>
     * precondition: i >= 0
     * Runtime: O(1)
     * </pre>
     *
     * @param i The node we want to get the number of children it has
     * @return The number of children the given node has
     */
    private int childrenCount(int i) {
        int c = child(i, 1);
        if (c >= size) {
            return 0;
        }
        if (size - c <= d) {
            return size - c;
        }
        return d;
    }

    /**
     * Says if a certain node has any children.
     *
     * <pre>
     * precondition: i >= 0
     * Runtime: O(1)
     * </pre>
     *
     * @param i The node to check
     * @return true iff the node has any children
     */
    private boolean hasChildren(int i) {
        return childrenCount(i) > 0;
    }

    /**
     * Returns the index of the last child of the checked node.
     *
     * <pre>
     * precondition: i >= 0
     * precondition: hasChildren(i) == true
     * Runtime: O(1)
     * </pre>
     *
     * @param i The node to check
     * @return The index of the last child of the tested node
     */
    private int lastChildIndex(int i) {
        return child(i, childrenCount(i));
    }

    /**
     * Inserts an item into the heap.
     *
     * <pre>
     * precondition: item != null, isHeap(), size < max_size
     * postcondition: isHeap()
     * Runtime: O(log(n)/log(d))
     * </pre>
     *
     * @param item The heap item to insert
     */
    public void Insert(DHeap_Item item) {
        assert item != null;
        assert size < max_size;
        size += 1;
        setItem(size - 1, item);
        heapifyUp(size - 1);
    }

    /**
     * Inserts a new item from a number into the heap.
     *
     * <pre>
     * precondition: item != null, isHeap(), size < max_size
     * postcondition: isHeap()
     * Runtime: O(log(n)/log(d))
     * </pre>
     *
     * @param number The number to insert to the heap (as a heap item)
     */
    void Insert(int number) {
        Insert(newItem(number));
    }

    /**
     * Creates a new DHeap_Item from a number, to insert it into the heap.
     *
     * <pre>
     * Runtime: O(1)
     * </pre>
     *
     * @param number The number to wrap
     * @return The DHeap_Item wrapping the number
     */
    private DHeap_Item newItem(int number) {
        return new DHeap_Item(null, number);
    }

    /**
     * Makes sure the elements in the underlying array maintain the
     * heap property, under a specific node in the heap, swapping elements
     * if needed.
     *
     * <pre>
     * Runtime: O(log(n)/log(d))
     * </pre>
     *
     * @param i The heap index to start the process from
     */
    private void heapifyUp(int i) {
        while (i != 0) {
            int p = parent(i);
            compare_count++;
            if (array[i].getKey() < array[p].getKey()) {
                swapItems(i, p);
                i = p;
            } else {
                break;
            }
        }
    }

    /**
     * Deletes the smallest element from the heap.
     *
     * <pre>
     * precondition: size > 0, isHeap()
     * postcondition: isHeap()
     * Runtime: O(d/log(d)*log(n))
     * </pre>
     */
    public void Delete_Min() {
        assert size > 0;
        setItem(0, array[size - 1]);
        array[size - 1] = null;
        size -= 1;
        heapifyDown(0);

        // Note: This could be implemented nicely instead by simply calling:
        //       Delete(Get_Min());
        //       But that would increase the amount of comparisons by a
        //       constant factor, and may mismatch the expected results for
        //       the assignment, so we chose to leave it as it is.
    }

    /**
     * Makes sure the elements in the underlying array maintain the
     * heap property, under a specific node in the heap, swapping elements
     * if needed.
     *
     * <pre>
     * Runtime: O(d/log(d)*log(n))
     * </pre>
     *
     * @param i The node from which to start the verification
     */
    private void heapifyDown(int i) {
        while (hasChildren(i)) {
            int min = minChildIndex(i);
            compare_count++;
            if (array[min].getKey() < array[i].getKey()) {
                swapItems(i, min);
                i = min;
            } else {
                break;
            }
        }
    }

    /**
     * Set the specified array slot to the given item,
     * and update the item's position.
     *
     * <pre>
     * Runtime: O(1)
     * </pre>
     *
     * @param i    The index at which to set the item
     * @param item The item to put in the internal array
     */
    private void setItem(int i, DHeap_Item item) {
        assert i >= 0 && i < size;
        array[i] = item;
        array[i].setPos(i);
    }

    /**
     * Swaps two items' position in the internal representation if the Heap.
     *
     * <pre>
     * Runtime: O(1)
     * </pre>
     *
     * @param i The index of the first item
     * @param j The index of the second item
     */
    private void swapItems(int i, int j) {
        DHeap_Item temp = array[i];
        setItem(i, array[j]);
        setItem(j, temp);
    }

    /**
     * Returns the index of the child with the lowest key
     * from the given node's children.
     *
     * <pre>
     * precondition: hasChildren(i) == true
     * Runtime: O(d)
     * </pre>
     *
     * @param i The node who's child we want
     * @return The index of the child of the given node with the lowest key
     */
    private int minChildIndex(int i) {
        assert hasChildren(i);
        int start = child(i, 1);
        int end = lastChildIndex(i);
        int min_index = start;
        for (int j = start + 1; j <= end; j++) {
            compare_count++;
            if (array[j].getKey() < array[min_index].getKey()) {
                min_index = j;
            }
        }
        return min_index;
    }


    /**
     * Returns the element with the lowest key value in the heap.
     *
     * <pre>
     * precondition: heap-size > 0, isHeap(), size > 0
     * postcondition: isHeap()
     * Runtime: O(1)
     * </pre>
     *
     * @return The element with the lowest key value in the heap
     */
    public DHeap_Item Get_Min() {
        assert size > 0;
        return array[0];
    }

    /**
     * Returns the element with the lowest key value in the heap,
     * and deletes it from the heap.
     *
     * <pre>
     * precondition: heap-size > 0, isHeap(), size > 0
     * postcondition: isHeap()
     * Runtime: O(d/log(d)*log(n))
     * </pre>
     *
     * @return The element with the lowest key value in the heap
     */
    private DHeap_Item popMin() {
        DHeap_Item item = Get_Min();
        Delete_Min();
        return item;
    }

    /**
     * Decrease the key of the given item by the given amount.
     *
     * <pre>
     * precondition: item.pos < size, item != null, isHeap()
     * postcondition: isHeap()
     * Runtime: O(log(n)/log(d))
     * </pre>
     *
     * @param item  The item who's key we want to decrease
     * @param delta The value by which we want to decrease the item's key
     */
    public void Decrease_Key(DHeap_Item item, int delta) {
        assert delta >= 0;
        assert item != null;
        assert item.getPos() < size;
        int key = item.getKey();
        key -= delta;
        item.setKey(key);
        heapifyUp(item.getPos());
    }

    /**
     * Deletes the given item from the heap.
     *
     * <pre>
     * precondition: item.pos < size, item != null, isHeap()
     * postcondition: isHeap()
     * Runtime: O(d/log(d)*log(n))
     * </pre>
     *
     * @param item The item we want to delete from the heap
     */
    public void Delete(DHeap_Item item) {
        assert item != null;
        int i = item.getPos();
        assert i >= 0 && i < size;
        assert array[i] == item;

        DHeap_Item replacement = array[size - 1];
        setItem(i, replacement);
        array[size - 1] = null;
        size -= 1;

        if (replacement.getKey() > item.getKey()) {
            heapifyDown(i);
        } else if (replacement.getKey() < item.getKey()) {
            heapifyUp(i);
        }
    }

    /**
     * Return a sorted array containing the same integers in the input array
     * (done using HeapSort with a d-ary heap where d=2).
     *
     * <pre>
     * Runtime: O(n*log(n)), n = array.length
     * </pre>
     *
     * @param array The array to sort
     * @return A new array with the same values as the received one but sorted
     */
    public static int[] DHeapSort(int[] array) {
        assert array != null;
        return DHeapSort(array, 2);
    }

    /**
     * Return a sorted array containing the same integers in the input array.
     *
     * <pre>
     * Runtime: O(d/log(d)*n*log(n)), n = array.length
     * </pre>
     *
     * @param array The array to sort
     * @param d     The arity of the d-ary heap to use
     * @return A new array with the same values as the received one but sorted
     */
    static int[] DHeapSort(int[] array, int d) {
        SortResult sortResult = DHeapSortMeasure(array, d);
        return sortResult.array;
    }

    /**
     * Return a sorted array containing the same integers in the input array,
     * and also count the amount of comparisons made to detect the order.
     *
     * <pre>
     * Runtime: O(d/log(d)*n*log(n)), n = array.length
     * </pre>
     *
     * @param array The array to sort
     * @param d     The arity of the d-ary heap to use
     * @return A new array with the same values as the received one but sorted
     */
    static SortResult DHeapSortMeasure(int[] array, int d) {
        DHeap heap = new DHeap(d, array.length);
        heap.arrayToHeap(array);

        int[] sorted = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            sorted[i] = heap.popMin().getKey();
        }
        return new SortResult(sorted, heap.compare_count);
    }

    /**
     * Auxiliary class used to hold the result
     * of a DHeapSortMeasure operation.
     */
    static class SortResult {
        public int[] array;
        public int comparisons;

        public SortResult(int[] array, int comparisons) {
            this.array = array;
            this.comparisons = comparisons;
        }
    }
}
