package com.guardwarm.struct.heap;

import com.guardwarm.struct.printer.BinaryTrees;
import org.junit.Before;
import org.junit.Test;

public class BinaryHeapTest {

    @Before
    public void setUp() throws Exception {
        Integer[] data = {
                22, 1, 96, 42, 9, 18, 87, 73, 93, 24, 3, 8, 90
        };
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        for (Integer datum : data) {
            heap.add(datum);
        }
        BinaryTrees.println(heap);

    }

    @Test
    public void clear() {
    }
}