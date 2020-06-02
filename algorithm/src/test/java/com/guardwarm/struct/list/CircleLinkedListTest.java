package com.guardwarm.struct.list;

import org.junit.Before;
import org.junit.Test;

public class CircleLinkedListTest {
    CircleLinkedList<Integer> cirList;
    @Before
    public void setUp() throws Exception {
        cirList = new CircleLinkedList<>();
        for (int i = 1; i <= 8; i++) {
            cirList.add(i);
        }
    }

    @Test
    public void clear() {
    }

    @Test
    public void get() {
    }

    @Test
    public void set() {
    }

    @Test
    public void add() {
        cirList.add(0, 999);
        cirList.add(10, 999);
        for (int i = 0; i < cirList.size; i++) {
            System.out.println(cirList.get(i));
        }
    }

    @Test
    public void remove() {
    }

    @Test
    public void indexOf() {
    }
}