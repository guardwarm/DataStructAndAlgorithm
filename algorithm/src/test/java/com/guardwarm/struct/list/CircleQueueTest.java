package com.guardwarm.struct.list;

import org.junit.Before;
import org.junit.Test;

public class CircleQueueTest {
    CircleQueue<Integer> cirQueue;
    @Before
    public void setUp() {
        cirQueue = new CircleQueue<>();
        for (int i = 0; i < 20; i++) {
            cirQueue.enQueue(i);
        }
    }

    @Test
    public void size() {
    }

    @Test
    public void isEmpty() {
        while (!cirQueue.isEmpty()) {
            System.out.println(cirQueue.deQueue());
        }
    }

    @Test
    public void front() {
        while (!cirQueue.isEmpty()) {
            System.out.println(cirQueue.front());
            cirQueue.deQueue();
        }
    }
}