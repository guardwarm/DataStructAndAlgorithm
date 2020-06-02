package com.guardwarm.struct.tree;

import com.guardwarm.struct.printer.BinaryTrees;
import org.junit.Before;
import org.junit.Test;

public class AvlTreeTest {
    AvlTree<Integer> bbst;
    @Before
    public void setUp() throws Exception {
        bbst = new AvlTree<>();
        int[] data = new int[]{
                81, 65, 70, 96, 90, 13, 63, 23, 42, 99, 92, 32
        };
        for (int datum : data) {
            bbst.add(datum);
            BinaryTrees.println(bbst);
            System.out.println("--------------");
        }
    }

    @Test
    public void afterAdd() {
    }

    @Test
    public void afterRemove() {
    }
}