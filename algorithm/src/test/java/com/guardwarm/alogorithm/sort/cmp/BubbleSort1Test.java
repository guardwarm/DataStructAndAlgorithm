package com.guardwarm.alogorithm.sort.cmp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BubbleSort1Test {

    @Before
    public void setUp() throws Exception {
        Integer[] a = new Integer[]{1, 5, 7, 8, 3 , 7};
        new BubbleSort1<Integer>().sort(a);
        for (Integer integer : a) {
            System.out.println(integer);
        }
    }

    @Test
    public void sort() {
    }
}