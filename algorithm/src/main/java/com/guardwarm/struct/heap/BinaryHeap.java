package com.guardwarm.struct.heap;

import com.guardwarm.struct.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 二叉堆（最大堆）
 * @param <E> 存放的数据类型
 * @author wh
 */
@SuppressWarnings({"unused", "unchecked"})
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap() {
        this(null, null);
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        this(null, comparator);
    }

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        }else {
            size = elements.length;
            int capacity = Math.max(DEFAULT_CAPACITY, size);
            this.elements = (E[]) new Object[capacity];
            System.arraycopy(elements, 0,
                    this.elements, 0, size);
            heapify();
        }
    }

    private void heapify() {
        for (int i = (size >> 1); i >= 0; i--) {
            siftDown(i);
        }
//        for (int i = 0; i < size; i++) {
//            siftUp(i);
//        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotCheck(element);
        ensureCapacity();
        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        E oldValue = elements[0];
        elements[0] = elements[--size];
        elements[size] = null;
        siftDown(0);
        return oldValue;
    }

    @Override
    public E replace(E element) {
        elementNotCheck(element);
        E oldValue = null;
        if (size == 0) {
            elements[size++] = element;
        }else {
            oldValue = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return oldValue;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            // +优先级高于>>
            int newCapacity = size + (size >> 1);
            E[] newEles = (E[]) new Object[newCapacity];
            System.arraycopy(elements, 0, newEles, 0, size);
            elements = newEles;
        }
    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }

    private void elementNotCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private void siftUp(int index) {
        E e = elements[index];
        int parentIndex;
        E parent;
        while (index > 0) {
            parentIndex = (index - 1) >> 1;
            parent = elements[parentIndex];
            if (compare(parent, e) >= 0) {
                break;
            }
            elements[index] = parent;
            index = parentIndex;
        }
        elements[index] = e;
    }

    private void siftDown(int index) {
        E e = elements[index];
        int leftIndex;
        int rightIndex;
        int half = size >> 1;
        E eLeft;
        while (index < half) {
            leftIndex = 2 * index + 1;
            rightIndex = leftIndex + 1;
            eLeft = elements[leftIndex];
            if (rightIndex < size && compare(eLeft, elements[rightIndex]) < 0) {
                eLeft = elements[leftIndex = rightIndex];
            }
            if (compare(eLeft, e) <= 0) {
                break;
            }
            elements[index] = eLeft;
            index = leftIndex;
        }
        elements[index] = e;
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int)node << 1) + 1;
        return index < size ? index : null;
    }

    @Override
    public Object right(Object node) {
        int index = ((int)node << 1) + 2;
        return index < size ? index : null;
    }

    @Override
    public Object string(Object node) {
        return elements[(int)node];
    }
}
