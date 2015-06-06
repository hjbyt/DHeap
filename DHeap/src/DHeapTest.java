import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class DHeapTest {

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
            Integer[] numbers = {1, 5, 2, 7, 9, 4, 6, 2, 4, 1, 6};
            checkHeap(heap, 0);
            heap.arrayToHeap(numbers);
            checkHeap(heap, numbers.length);
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
            Integer[] numbers_ = {1, 5, 2, 7, 9, 4, 6, 2, 4, 1, 6};
            List<Integer> numbers = Arrays.asList(numbers_);
            int size = 0;
            checkHeap(heap, size);
            for (Integer number : numbers) {
                heap.Insert(number);
                size += 1;
                checkHeap(heap, size);
            }
        });
    }

    @Test
    public void testDelete_Min() throws Exception {
        //TODO
    }

    @Test
    public void testGet_Min() throws Exception {
        //TODO
    }

    @Test
    public void testDecrease_Key() throws Exception {
        //TODO
    }

    @Test
    public void testDelete() throws Exception {
        //TODO
    }

    @Test
    public void testDHeapSort() throws Exception {
        //TODO
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