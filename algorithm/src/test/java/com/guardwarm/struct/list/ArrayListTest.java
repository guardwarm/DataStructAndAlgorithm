package com.guardwarm.struct.list;

import org.junit.Before;
import org.junit.Test;

public class ArrayListTest {
    ArrayList<Integer> list;
    @Before
    public void init() {
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(5);
        }
    }

    @Test
    public void clear() {
        list.clear();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void get() {
        System.out.println(list.get(4));
    }

    @Test
    public void set() {
        list.set(3, 55);
        System.out.println(list.get(3));
    }

    @Test
    public void add() {
    }

    @Test
    public void remove() {
        System.out.println(list.size);

    }

    @Test
    public void indexOf() {
        list.set(5, 66);
        System.out.println(list.indexOf(66));
    }
}