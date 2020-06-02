package com.guardwarm.struct.tree;

import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeTest {
    BinarySearchTree<Integer> bst;
    int[] data;
    @Before
    public void setUp() throws Exception {
        bst = new BinarySearchTree<>();
        data = new int[]{
                7,4,2,34,5,6
        };
    }

    @Test
    public void size() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void add() {
        for (int i = 0; i < data.length ; i++) {
            bst.add(data[i]);
        }
        bst.preorder(new BinaryTree.AbstractVisitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
//        BinaryTrees.println(bst);
//        System.out.println(bst.size());
//        bst.remove(5);
//        BinaryTrees.println(bst);
//        System.out.println(bst.size());
//        System.out.println(bst);
//        System.out.println();
//        bst.preorder();
//        System.out.println(bst.predecessor(2));
//        System.out.println(bst.successor(2));
//        System.out.println();
//        bst.inorder();
//        System.out.println();
//        bst.postorder();
    }

    @Test
    public void clear() {
    }

    @Test
    public void cantains() {
    }
}