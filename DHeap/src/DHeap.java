import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        //TODO
    }

    /**
     * public boolean isHeap()
     * <p>
     * The function returns true if and only if the D-ary tree rooted at array[0]
     * satisfies the heap property or size == 0.
     */
    public boolean isHeap() {
        return false; //TODO
    }


    public int parent(int i) {
        assert i >= 0;
        return (i - 1) / d;
    }

    public int child(int i, int k) {
        assert i >= 0;
        assert k >= 1 && k <= d;
        return (d * i) + k;
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
//        return 999; //TODO
//    }
//
//    public static int child(int i, int k) {
//        return 999; //TODO
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
        //TODO
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
        array[0] = array[size];
        array[0].setPos(0); //TODO: unneeaded?
        heapifyDown(0);
    }

    private void heapifyDown(int i) {
        int min = minChildIndex(i);
        if (array[min].getKey() < array[i].getKey()) {
            DHeap_Item temp = array[i];
            array[i] = array[min];
            updatePosition(i);
            array[min] = temp;
            heapifyDown(min);
        } else {
            updatePosition(i);
        }
    }

    private void updatePosition(int i) {
        array[i].setPos(i);
    }

    private int minChildIndex(int i) {
        int start = child(i, 1);
        int end = child(i, d);
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
        //TODO
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
        //TODO
    }

    /**
     * Return a sorted array containing the same integers in the input array.
     * Sorting should be done using the DHeap.
     */
    public static int[] DHeapSort(int[] array) {
        return null; //TODO
    }
}
