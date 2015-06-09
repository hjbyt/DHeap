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
    private static final int MAX_CONTENT_VALUE = 20000;

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
        // TODO - Also test when asking the root for a praent
    }

    @Test
    public void testChild() throws Exception {
        //TODO?
        // TODO - Check what that leaves return correct values
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
    public void testGet_Min_Fuzz() throws Exception {
        Random rand = new Random();
        for (DHeap heap : heaps) {
            int[] contents = getRandomArray(rand.nextInt(MAX_SIZE), MAX_CONTENT_VALUE);
            heap.arrayToHeap(contents);
            Arrays.sort(contents);
            assertEquals(heap.Get_Min().getKey(), contents[0]);
        }
    }

    @Test
    public void testDecrease_Key() throws Exception {
        Random rand = new Random();
        for (DHeap heap : heaps) {
            heap.arrayToHeap(getRandomArray(rand.nextInt(MAX_SIZE), MAX_CONTENT_VALUE));
            for (int i = 0; i < 500; i++) {
                assert heap.isHeap();
                DHeap_Item itemToChange = getRandomItem(heap);
                int delta = rand.nextInt(MAX_CONTENT_VALUE / 4);
                heap.Decrease_Key(itemToChange, delta);
                assert heap.isHeap();
            }
        }
    }

    private DHeap_Item getRandomItem(DHeap heap) {
        List<DHeap_Item> heapContents = heap.getItems().stream().collect(Collectors.toList());
        Collections.shuffle(heapContents);
        return heapContents.get(0);
    }

    @Test
    public void testDelete() throws Exception {
        Random rand = new Random();
        for (DHeap heap : heaps) {
            heap.arrayToHeap(getRandomArray(rand.nextInt(MAX_SIZE), MAX_CONTENT_VALUE));
            while (0 < heap.getSize()) {
                // Note - We can't generate the list once because things might move around and we would have thr wrong
                // Note - item position
                DHeap_Item itemToDelete = getRandomItem(heap);
                heap.Delete(itemToDelete);
            }
        }
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

    private static int[] getRandomArray(int size, int max_value) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(max_value);
        }
        return arr;
    }

    @Test
    public void testDHeapSort() throws Exception {
        testSortArray(copyArray(TEST_NUMBERS.toArray(new Integer[TEST_NUMBERS.size()])));
    }

    @Test
    public void testDHeapSort_Fuzz() throws Exception {
        Random rand = new Random();
        for (int i = 0; i <= 100; i++) {
            testSortArray(getRandomArray(i, MAX_CONTENT_VALUE));
        }
    }

    @Test
    public void testFuzz() throws Exception {
        //TODO
    }


    private void ourHeapSortCopy(DHeap heap, int m) {
        // Note - here we copy some code from the DHeapSort because their requested API
        // Note - doesn't allow us to do what they ask us to measure...
        // Note - We also don't care about the result, just the deletion of keys
        for (int i = 0; i < m; i++) {
            heap.Delete_Min();
        }
    }

    @Test
    public void testMeasurements() throws Exception {
        int TEST_MAX_KEY_VALUE = 1000;
        for (int m : new int[] {1000, 10000, 100000}) {
            for (int d : new int[]{2,3,4}) {
                DHeap heap = new DHeap(d, m);
                int[] arr = getRandomArray(m, TEST_MAX_KEY_VALUE);
                heap.arrayToHeap(arr);
                ourHeapSortCopy(heap, m);
                System.out.println("Number of comparisons for insertions m:=" + m + " d:= " + d + " comparisons:=" + heap.compare_count);
            }
        }
        for (int d : new int[]{2,3,4}) {
            for (int x : new int[]{1, 100, 1000}) {
                int[] arr = getRandomArray(100000, TEST_MAX_KEY_VALUE);
                DHeap_Item[] items = Arrays.stream(arr).mapToObj(i -> new DHeap_Item(null, i)).toArray(DHeap_Item[]::new);
                DHeap heap = new DHeap(d, arr.length);
                for (DHeap_Item item : items) {
                    heap.Insert(item);
                }
                heap.compare_count = 0; // Note - We only want the Decrease-Key comparisons
                for (DHeap_Item item : items) {
                    heap.Decrease_Key(item, x);
                }
                System.out.println("Number of comparisons for Decrease_Key d:= " + d + " x:= " + x + " comparisons:= " + heap.compare_count);
            }
        }
    }
}