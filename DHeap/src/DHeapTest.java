import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        heaps.forEach((heap) -> {
            checkHeap(heap, 0);
            heap.arrayToHeap(TEST_NUMBERS);
            checkHeap(heap, TEST_NUMBERS.length);
        });
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
        heaps.forEach((heap) -> {
            int size = 0;
            checkHeap(heap, size);
            for (Integer number : TEST_NUMBERS_LIST) {
                heap.Insert(number);
                size += 1;
                checkHeap(heap, size);
            }
        });
    }


    public static void initHeap(DHeap heap) {
        try {
            heap.arrayToHeap(TEST_NUMBERS);
        } catch (Throwable e) {
            assumeNoException(e);
        }
    }

    @Test
    public void testDelete_Min() throws Exception {
        assert false; //TODO
    }

    @Test
    public void testGet_Min() throws Exception {
        heaps.forEach((heap) -> {
            initHeap(heap);
            DHeap_Item min_item = heap.Get_Min();
            assertEquals((int) Collections.min(TEST_NUMBERS_LIST), min_item.getKey());
            assertEquals(0, min_item.getPos());
        });
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