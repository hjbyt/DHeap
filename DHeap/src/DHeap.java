import java.util.Arrays;
import java.util.List;

/**
 * D-Heap
 */

public class DHeap {

    private int size;
    private int max_size;
    private int d;
    private DHeap_Item[] array;
    int compare_count; // Package local for testing

    // Constructor
    // m_d >= 2, m_size > 0
    DHeap(int m_d, int m_size) {
        max_size = m_size;
        d = m_d;
        array = new DHeap_Item[max_size];
        size = 0;
        compare_count = 0;
    }

    // For testing

    /**
     * Returns a list of all the items in the heap
     * @return All the items in the heap, as a list
     */
    List<DHeap_Item> getItems() {
        return Arrays.asList(array).subList(0, size);
    }

    /**
     * Returns the size of the heap
     * @return The current number of elements in the heap
     */
    public int getSize() {
        return size;
    }

    /**
     * The function builds a new heap from the given array.
     * Previous data of the heap will be erased.
     * The running time is O(n), where n = numbers.size()
     * preconidtion: array1.length <= max_size
     * postcondition: isHeap()
     * size = array.length()
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
     * The running time is O(n), where n = numbers.length
     * precondition: numbers.length <= max_size
     * postcondition: isHeap(); size = array.length();
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
     * The running time is O(n), where n = numbers.size()
     * precondition: numbers.size() <= max_size
     * postcondition: isHeap(); size = array.length();
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
     * The running time is O(n), where n = size
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
     * Running time is O(n) where n = size
     */
    public void printTree() {
        if (size > 0) {
            printTree(0, "", true);
        } else {
            System.out.println("empty");
        }
    }

    /**
     * The internal method that prints a part of the heap as a subtree
     * Note: adapted from http://stackoverflow.com/a/8948691
     * The running time is O(n) where n = size
     * @param i The index from which to start printing
     * @param prefix The prefix used in order to align all all the prints
     * @param isTail Says wheather the current node is it's father's last child
     */
    private void printTree(int i, String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "+-- " : "+-- ") +
                array[i].toString());

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
     * Running time is O(n) where n = size
     * @return If the array satisfies the properties of a D-ary tree
     */
    public boolean isHeap() {
        for (int i = size - 1; i > 0; i--) {
            if (array[i].getKey() < array[parent(i)].getKey()) {
                return false;
            }
        }
        return true;
    }

    /**
     * An internal method, used to assert the internal array satisfies the
     * properties of a D-ary heap, and a few more internal tests
     * Running time is O(n) where n = size
     */
    void checkHeap() {
        assert size >= 0 && size <= max_size;
        assert isHeap();
        for (int i = 0; i < size; i++) {
            assert array[i].getPos() == i;
        }
    }

    /**
     * The methods compute the index of the parent for the heap element at
     * the given offset
     * The running time is O(1)
     * precondition: i >= 0
     * @param i The element who's parent's offset is requested
     * @return The index of the parant for the given element
     */
    public int parent(int i) {
        assert i > 0;
        return (i - 1) / d;
    }

    /**
     * The methods compute the index of the parent and the k-th child of
     * vertex i in a complete D-ary tree stored in an array. 1 <= k <= d.
     * Note that indices of arrays in Java start from 0.
     * The running time is O(1)
     * precondition: i >= 0
     * @param i The vertex who's child is requested
     * @param k The requested child's index in relations to the parent
     * @return The index of the requested child
     */
    public int child(int i, int k) {
        assert i >= 0;
        assert k >= 1 && k <= d;
        return (d * i) + k;
    }

    /**
     * Returns the number of children a specific vertex has
     * Runs in O(1) time.
     * Made package local for testing
     * @param i The vertex we want to get the number of children it has
     * @return The number of children the given vertex has
     */
    int childrenCount(int i) {
        int c = child(i, 1);
        if (c >= size) {
            return 0;
        }
        if (size - c <= d) {
            return size - c;
        }
        return d;
    }

    private boolean hasChildren(int i) {
        return childrenCount(i) > 0;
    }

    private int lastChildIndex(int i) {
        return child(i, childrenCount(i));
    }

    /**
     * public void Insert(DHeap_Item item)
     * <p>
     * precondition: item != null
     * isHeap()
     * size < max_size
     * <p>
     * postcondition: isHeap()
     */
    public void Insert(DHeap_Item item) {
        assert item != null;
        assert size < max_size;
        size += 1;
        setItem(size - 1, item);
        heapifyUp(size - 1);
    }

    public void Insert(int number) {
        Insert(newItem(number));
    }

    private DHeap_Item newItem(int number) {
        return new DHeap_Item(null, number);
    }

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
     * public void Delete_Min()
     * <p>
     * precondition: size > 0
     * isHeap()
     * <p>
     * postcondition: isHeap()
     */
    public void Delete_Min() {
        assert size > 0;
        setItem(0, array[size - 1]);
        array[size - 1] = null;
        size -= 1;
        heapifyDown(0);
    }

    /**
     * Makes sure the elements in the underlying array maintain the
     * heap property, under a specific node in the heap, swaping elements
     * if needed
     * The runing time is O(logn) where n = size
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

    private void setItem(int i, DHeap_Item item) {
        assert i >= 0 && i < size;
        array[i] = item;
        array[i].setPos(i);
    }

    private void swapItems(int i, int j) {
        DHeap_Item temp = array[i];
        setItem(i, array[j]);
        setItem(j, temp);

    }

    private int minChildIndex(int i) {
        assert hasChildren(i);
        int start = child(i, 1);
        int end = lastChildIndex(i);
        int min_index = start;
        for (int j = start + 1; j <= end; j++) {
            if (array[j].getKey() < array[min_index].getKey()) {
                min_index = j;
            }
        }
        return min_index;
    }


    /**
     * public String Get_Min()
     * <p>
     * precondition: heapsize > 0
     * isHeap()
     * size > 0
     * <p>
     * postcondition: isHeap()
     */
    public DHeap_Item Get_Min() {
        assert size > 0;
        return array[0];
    }

    private DHeap_Item popMin() {
        DHeap_Item item = Get_Min();
        Delete_Min();
        return item;
    }

    /**
     * public void Decrease_Key(DHeap_Item item, int delta)
     * <p>
     * precondition: item.pos < size;
     * item != null
     * isHeap()
     * <p>
     * postcondition: isHeap()
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
     * public void Delete(DHeap_Item item)
     * <p>
     * precondition: item.pos < size;
     * item != null
     * isHeap()
     * <p>
     * postcondition: isHeap()
     */
    public void Delete(DHeap_Item item) {
        assert item != null;
        assert item.getPos() >= 0 && item.getPos() < size;
        assert array[item.getPos()] == item;
        //TODO: can we simply delete it like we do in delete-min? it should be more efficient
        int i = item.getPos();
        while (i != 0) {
            int p = parent(i);
            swapItems(i, p);
            i = p;
        }
        Delete_Min();
    }

    /**
     * Return a sorted array containing the same integers in the input array.
     * Sorting should be done using the DHeap.
     */
    public static int[] DHeapSort(int[] array) {
        assert array != null;
        //TODO: is it ok to simply use d=2?
        return DHeapSort(array, 2);
    }

    static int[] DHeapSort(int[] array, int d) {
        DHeap heap = new DHeap(d, array.length);
        heap.arrayToHeap(array);

        int[] sorted = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            sorted[i] = heap.popMin().getKey();
        }

        return sorted;
    }
}
