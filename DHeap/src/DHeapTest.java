import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeNoException;

public class DHeapTest {

    private static final Integer[] TEST_NUMBERS = {1, 5, 2, 7, 9, 4, 6, 2, 4, 1, 6};
    private static final List<Integer> TEST_NUMBERS_LIST = Arrays.asList(TEST_NUMBERS);
    private static final int NUMBER_OF_HEAPS = 20;
    private static final int MAX_SIZE = 5000;

    List<DHeap> heaps;

    @Before
    public void setUp() throws Exception {
        heaps = new ArrayList<>(NUMBER_OF_HEAPS);
        for (int i = 1; i <= NUMBER_OF_HEAPS; i++) {
            DHeap heap = new DHeap(i, MAX_SIZE);
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
            checkHeap(heap, 0);
            heap.arrayToHeap(TEST_NUMBERS);
            checkHeap(heap, TEST_NUMBERS.length);
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
            for (Integer number : TEST_NUMBERS_LIST) {
                heap.Insert(number);
                size += 1;
                checkHeap(heap, size);
            }
        }
    }

    // This is used for tests that expect a heap with items, but shouldn't fail just because heap construction fails.
    public static void initHeap(DHeap heap, Integer[] numbers) {
        try {
            heap.arrayToHeap(numbers);
            checkHeap(heap, numbers.length);
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
            assertEquals((int) Collections.min(TEST_NUMBERS_LIST), min_item.getKey());
            assertEquals(0, min_item.getPos());
            assertEquals(min_item, heap.Get_Min());
        }
    }

    @Test
    public void testDecrease_Key() throws Exception {
        assert false; //TODO
    }

    @Test
    public void testDelete() throws Exception {
        assert false; //TODO
    }

    @Test
    public void testDHeapSort() throws Exception {
        assert false; //TODO
    }

    @Test
    public void testFuzz() throws Exception {
        assert false; //TODO
    }

    @Test
    public void testMeasurements() throws Exception {
        //TODO
    }
}