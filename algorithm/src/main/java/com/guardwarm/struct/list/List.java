package com.guardwarm.struct.list;

public interface List<E> {
    int ELEMENT_NOT_FOUND = -1;
    int size();
    boolean isEmpty();
    void clear();
    E get(int index);
    E set(int index, E element);
    void add(E element);
    void add(int index, E element);
    E remove(E element);
    E remove(int index);
    int indexOf(E element);
    boolean contains(E element);

}
