package com.guardwarm.struct.set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TreeSetTest {

    @Before
    public void setUp() {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(1);
        }
        System.out.println(set.size());
        Assert.assertEquals(set.size(), 1);
    }

    @Test
    public void size() {
    }
}