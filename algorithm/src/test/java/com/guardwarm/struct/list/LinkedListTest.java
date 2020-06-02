package com.guardwarm.struct.list;

import org.junit.Before;
import org.junit.Test;

public class LinkedListTest {
    LinkedList<Integer> list;
    @Before
    public void init() {
        list = new LinkedList<>();
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }
    }

    @Test
    public void clear() {
        list.clear();
        list.get(6);
    }

    @Test
    public void get() {
        for (int i = 0; i < list.size; i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void set() {
        System.out.println(list.get(6));
        list.set(6, 777);
        System.out.println(list.get(6));
    }

    @Test
    public void add() {

    }

    @Test
    public void remove() {
        System.out.println(list.get(6));
        list.remove(6);
        System.out.println(list.get(6));
    }

    @Test
    public void indexOf() {
        System.out.println(list.indexOf(5));
        System.out.println(list.indexOf(888));
    }
}