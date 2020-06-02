package com.guardwarm.struct.tree;

import com.guardwarm.struct.printer.BinaryTrees;
import org.junit.Before;
import org.junit.Test;

public class RBTreeTest {

    @Before
    public void setUp() throws Exception {
        RBTree<Integer> rbTree = new RBTree<>();
        int[] data = new int[]{
                81, 28, 32, 2, 65, 66, 24, 29, 31, 72, 59, 49, 44, 85, 82, 35, 27, 19, 52, 63
        };
        for (int i : data) {
            rbTree.add(i);
        }
        BinaryTrees.println(rbTree);
    }

    @Test
    public void createNewNode() {
    }
}