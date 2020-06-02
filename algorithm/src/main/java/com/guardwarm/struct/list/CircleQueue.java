package com.guardwarm.struct.list;

/**
 * @author asus
 */
@SuppressWarnings("unused")
public class CircleQueue<E> {
    private int size;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;
    private int front;

    public CircleQueue() {
        this(DEFAULT_CAPACITY);
    }

    public CircleQueue(int capacity) {
        front = 0;
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        front = 0;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = size + (size >> 1);
            E[] newElements = (E[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[index(i)];
            }
            elements = newElements;
            front = 0;
        }
    }

    public E front() {
        return elements[front];
    }

    public void enQueue(E element) {
        ensureCapacity();
        elements[index(size)] = element;
        // 前面操作会用到front，所以front要最后修改
        front = index(-1);
        size++;
    }

    public E deQueue() {
        E old = elements[front];
        elements[front] = null;
        size --;
        front = index(1);
        return old;
    }

    private int index(int index) {
        index += front;
        if (index < 0) {
            return index + elements.length;
        }
        // %效率低，由于index一定>0 且 <2length，故可如下优化
        return index - (index >= elements.length ?
                elements.length : 0);
    }
}
