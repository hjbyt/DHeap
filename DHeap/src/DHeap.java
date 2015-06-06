/**
 * D-Heap
 */

public class DHeap {

    private int size, max_size, d;
    private DHeap_Item[] array;

    // Constructor
    // m_d >= 2, m_size > 0
    DHeap(int m_d, int m_size) {
        max_size = m_size;
        d = m_d;
        array = new DHeap_Item[max_size];
        size = 0;
    }

    // Getter for size
    public int getSize() {
        return size;
    }

    /**
     * public void arrayToHeap()
     * <p>
     * The function builds a new heap from the given array.
     * Previous data of the heap should be erased.
     * preconidtion: array1.length() <= max_size
     * postcondition: isHeap()
     * size = array.length()
     */
    public void arrayToHeap(DHeap_Item[] array1) {
        assert array1.length <= max_size;
        System.arraycopy(array1, 0, array, 0, array1.length);
        size = array1.length;
        heapify();
    }

    private void heapify() {
        //TODO: i don't know if this is correct.
        //      i adapted this from cormen, it should be tested...
        final int last_internal_node = (size / d) - 1;
        for (int i = last_internal_node; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    /**
     * public boolean isHeap()
     * <p>
     * The function returns true if and only if the D-ary tree rooted at array[0]
     * satisfies the heap property or size == 0.
     */
    public boolean isHeap() {
        return size == 0 || isSubHeap(0);
    }

    private boolean isSubHeap(int i) {
        if (!hasChildren(i)) {
            return true;
        }

        int start = child(i, 1);
        int end = lastChildIndex(i);
        for (int j = start; j <= end; j++) {
            if (array[j].getKey() < array[i].getKey()) {
                return false;
            }
            if (!isSubHeap(j)) {
                return false;
            }
        }

        return true;
    }


    public int parent(int i) {
        assert i > 0;
        return (i - 1) / d;
    }

    public int child(int i, int k) {
        assert i >= 0;
        assert k >= 1 && k <= d;
        return (d * i) + k;
    }

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

    private boolean hasChildren(int i) {
        return childrenCount(i) > 0;
    }

    private int lastChildIndex(int i) {
        return child(i, childrenCount(i));
    }

    //TODO XXX: ask TA about this...
    /**
     * public static int parent(i), child(i,k)
     * (2 methods)
     * <p>
     * precondition: i >= 0
     * <p>
     * The methods compute the index of the parent and the k-th child of
     * vertex i in a complete D-ary tree stored in an array. 1 <= k <= d.
     * Note that indices of arrays in Java start from 0.
     */
//    public static int parent(int i) {
//        return 999;
//    }
//
//    public static int child(int i, int k) {
//        return 999;
//    }

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
        size += 1;
        setItem(size - 1, item);
        heapifyUp(size - 1);
    }

    private void heapifyUp(int i) {
        while (i != 0) {
            int p = parent(i);
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
        size -= 1;
        setItem(0, array[size]);
        heapifyDown(0);
    }

    private void heapifyDown(int i) {
        while (hasChildren(i)) {
            int min = minChildIndex(i);
            if (array[min].getKey() < array[i].getKey()) {
                swapItems(i, min);
                i = min;
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
        assert item.getPos() < size;
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
        //TODO: is it okay to simply use d=2?
        return DHeapSort(array, 2);
    }

    private static int[] DHeapSort(int[] array, int d) {
        DHeap heap = new DHeap(d, array.length);
        DHeap_Item[] items = new DHeap_Item[array.length];
        int[] sorted = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            DHeap_Item item = new DHeap_Item(null, array[i]);
            items[i] = item;
        }
        heap.arrayToHeap(items);

        for (int i = 0; i < array.length; i++) {
            sorted[i] = heap.popMin().getKey();
        }

        return sorted;
    }
}
