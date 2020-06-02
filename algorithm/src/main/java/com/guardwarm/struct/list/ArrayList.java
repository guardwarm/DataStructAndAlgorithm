package com.guardwarm.struct.list;

/**
 * @author asus
 */
@SuppressWarnings("unused")
public class ArrayList<E> extends AbstractList<E>{
    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements;
    public ArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }
    public ArrayList(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capacity];
    }
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        if (elements.length > 100) {
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        }
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacity();

        if (size != 0) {
            System.arraycopy(elements, index, elements,
                    index + 1, size - index);
        }
        elements[index] = element;
        size++;
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

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E old = elements[index];
        System.arraycopy(elements, index+1,
                elements, index,size - index -1);
        elements[--size] = null;
        return old;
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        }else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }
}
