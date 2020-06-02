package com.guardwarm.struct.list;

import org.junit.Before;
import org.junit.Test;

public class NonSingleLinkedListTest {
    NonSingleLinkedList<Integer> list;
    @Before
    public void setUp() throws Exception {
        list = new NonSingleLinkedList<>();
        for (int i = 0; i <17; i++) {
            list.add(i);
        }
    }

    @Test
    public void clear() {
    }

    @Test
    public void get() {
        for (int i = 0; i < list.size; i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void set() {
        System.out.println(list.get(3));
        list.set(3, 999);
        list.add(0, 999);
        list.add(list.size - 1, 999);
        list.add(list.size, 999);
        for (int i = 0; i < list.size; i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void add() {

    }

    @Test
    public void remove() {
        System.out.println(list.get(4));
        list.remove(4);
        list.remove(Integer.valueOf(6));
        System.out.println(list.get(4));
    }

    @Test
    public void indexOf() {
        System.out.println(list.indexOf(0));
        System.out.println(list.indexOf(7));
        System.out.println(list.indexOf(16));
    }
}