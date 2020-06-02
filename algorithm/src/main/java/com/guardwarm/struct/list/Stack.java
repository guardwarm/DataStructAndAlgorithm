package com.guardwarm.struct.list;

public class Stack<E> {
    private List<E> list = new ArrayList<>();
    public void clear() {
        list.clear();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
    public void push(E element) {
        list.add(element);
    }

    public E peek() {
        return list.get(list.size() - 1);
    }

    public E pop() {
        return list.remove(list.size() - 1);
    }
}
