package com.guardwarm.struct.list;

public class Queue<E> {
    // 通过组合双向链表来实现功能
    List<E> list = new NonSingleLinkedList<>();
    public int size() {
        return list.size();
    }
    public boolean isEmpty() {
        return list.isEmpty();
    }
    public void clear() {
        list.clear();
    }
    public void enQueue(E element) {
        list.add(list.size(), element);
    }
    public E deQueue() {
        return list.remove(0);
    }
    public E front() {
        return list.get(0);
    }
}
