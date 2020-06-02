package com.guardwarm.struct.map;

import org.junit.Before;
import org.junit.Test;

public class TreeMapTest {

    @Before
    public void setUp() {
        TreeMap<String, Integer> ma = new TreeMap<>();
        for (int i = 0; i < 10; i++) {
            ma.put("hhh"+i, i);
        }
        ma.remove("hhh5");
        ma.traversal(new Map.Visit<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    @Test
    public void size() {
    }
}