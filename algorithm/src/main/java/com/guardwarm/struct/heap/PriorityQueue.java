package com.guardwarm.struct.heap;

import java.util.Comparator;

public class PriorityQueue<E> {
    private BinaryHeap<E> heap;

    public PriorityQueue(Comparator<E> comparator) {
        heap = new BinaryHeap<>(comparator);
    }

    public PriorityQueue() {
        this(null);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void clear() {
        heap.clear();
    }

    public E front() {
        return heap.get();
    }

    public E deQueue() {
        return heap.remove();
    }

    public void inQueue(E element) {
        heap.add(element);
    }
}
