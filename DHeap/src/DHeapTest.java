import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeNoException;

public class DHeapTest {

    private static final List<Integer> TEST_NUMBERS = Arrays.asList(1, 5, 2, 7, 9, 4, 6, 2, 4, 1, 6);
    private static final int NUMBER_OF_HEAPS = 20;
    private static final int MAX_SIZE = 5000;

    List<DHeap> heaps;

    @Before
    public void setUp() throws Exception {
        heaps = new ArrayList<>(NUMBER_OF_HEAPS);
        for (int d = 1; d <= NUMBER_OF_HEAPS; d++) {
            DHeap heap = new DHeap(d, MAX_SIZE);
            checkHeap(heap, 0);
            heaps.add(heap);
        }
    }

    private static void checkHeap(DHeap heap, int expectedSize) {
        try {
            assertEquals(expectedSize, heap.getSize());
            heap.checkHeap();
        } catch (Exception e) {
            heap.printTree();
            throw e;
        }
    }

    @Test
    public void testArrayToHeap() throws Exception {
        for (DHeap heap : heaps) {
            for (int i = 0; i <= TEST_NUMBERS.size(); i++) {
                List<Integer> numbers = TEST_NUMBERS.subList(0, i);
                heap.arrayToHeap(numbers);
                checkHeap(heap, numbers.size());
            }
        }
    }

    @Test
    public void testParent() throws Exception {
        //TODO?
    }

    @Test
    public void testChild() throws Exception {
        //TODO?
    }

    @Test
    public void testInsert() throws Exception {
        for (DHeap heap : heaps) {
            int size = 0;
            checkHeap(heap, size);
            for (Integer number : TEST_NUMBERS) {
                heap.Insert(number);
                size += 1;
                checkHeap(heap, size);
            }
        }
    }

    // This is used for tests that expect a heap with items, but shouldn't fail just because heap construction fails.
    public static void initHeap(DHeap heap, List<Integer> numbers) {
        try {
            heap.arrayToHeap(numbers);
            checkHeap(heap, numbers.size());
        } catch (Throwable e) {
            assumeNoException(e);
        }
    }

    @Test
    public void testDelete_Min() throws Exception {
        for (DHeap heap : heaps) {
            initHeap(heap, TEST_NUMBERS);
            int size = heap.getSize();

            while (heap.getSize() > 0) {
                List<DHeap_Item> itemsBefore = new ArrayList<>(heap.getItems());
                heap.Delete_Min();
                List<DHeap_Item> itemsAfter = new ArrayList<>(heap.getItems());
                size -= 1;

                checkHeap(heap, size);
                assertEquals(itemsBefore.size() - 1, itemsAfter.size());

                List<Integer> keysBefore = itemsBefore.stream().map(DHeap_Item::getKey).collect(Collectors.toList());
                itemsBefore.removeAll(itemsAfter);
                DHeap_Item deletedItem = itemsBefore.get(0);
                assertEquals(0, deletedItem.getPos());
                assertEquals((int) Collections.min(keysBefore), deletedItem.getKey());
            }
        }
    }

    @Test
    public void testGet_Min() throws Exception {
        for (DHeap heap : heaps) {
            initHeap(heap, TEST_NUMBERS);
            DHeap_Item min_item = heap.Get_Min();
            assertEquals((int) Collections.min(TEST_NUMBERS), min_item.getKey());
            assertEquals(0, min_item.getPos());
            assertEquals(min_item, heap.Get_Min());
        }
    }

    @Test
    public void testDecrease_Key() throws Exception {
        //TODO
    }

    @Test
    public void testDelete() throws Exception {
        //TODO
    }

    private static int[] copyArray(Integer[] arr) {
        int[] newArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        return newArr;
    }

    private static void testSortArray(int[] javaSorted) {
        int[] ourSorted = DHeap.DHeapSort(javaSorted);
        Arrays.sort(javaSorted);
        assertArrayEquals(ourSorted, javaSorted);
    }

    private static int[] getRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt();
        }
        return arr;
    }

    @Test
    public void testDHeapSort() throws Exception {
        testSortArray(copyArray(TEST_NUMBERS));
    }

    @Test
    public void testDHeapSort_Fuzz() throws Exception {
        Random rand = new Random();
        for (int i = 0; i <= 100; i++) {
            testSortArray(getRandomArray(i));
        }
    }

    @Test
    public void testFuzz() throws Exception {
        //TODO
    }

    @Test
    public void testMeasurements() throws Exception {
        //TODO
    }
}