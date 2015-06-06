import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

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

    private void repeatForAllHeaps(Consumer<DHeap> function) {
        for (int i = 0; i < NUMBER_OF_HEAPS; i++) {
            DHeap heap = heaps.get(i);
            function.accept(heap);
        }
    }

    private void checkHeap(DHeap heap, int expectedSize) {
        try {
            assertEquals(expectedSize, heap.getSize());
            assertTrue(heap.isHeap());
        } catch (Exception e) {
            heap.printTree();
            throw e;
        }
    }

    @Test
    public void testArrayToHeap() throws Exception {
        repeatForAllHeaps((heap) -> {
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
        repeatForAllHeaps((heap) -> {
            int size = 0;
            checkHeap(heap, size);
            for (Integer number : TEST_NUMBERS_LIST) {
                heap.Insert(number);
                size += 1;
                checkHeap(heap, size);
            }
        });
    }

    @Test
    public void testDelete_Min() throws Exception {
        assert false; //TODO
    }

    @Test
    public void testGet_Min() throws Exception {
        repeatForAllHeaps((heap) -> {
            heap.arrayToHeap(TEST_NUMBERS);
            int min1 = Collections.min(TEST_NUMBERS_LIST);
            int min2 = heap.Get_Min().getKey();
            assertEquals(min1, min2);
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