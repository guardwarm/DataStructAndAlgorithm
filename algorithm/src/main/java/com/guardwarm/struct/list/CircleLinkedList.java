package com.guardwarm.struct.list;

/**
 * @author wh
 * @param <E>   泛型参数
 */
public class CircleLinkedList<E> extends AbstractList<E> {
    Node<E> first;
    
    public CircleLinkedList() {
        first = new Node<>(null, null);
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        Node<E> node;
        if (size == 0) {
            first.next = new Node<>(element, first);
        } else {
            node = node(index - 1);
            node.next = new Node<>(element, node.next);
        }

        size++;
    }

    @Override
    public E remove(int index) {
        Node<E> prev = node(index - 1);
        E old = prev.next.element;
        if (size == 1) {
            prev.next = null;
        }else {
            prev.next = prev.next.next;
        }
        size--;
        return old;
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node(i).element == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node(i).element)) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    private Node<E> node(int index) {
        Node<E> node;
        if (index == -1) {
            node = first;
        }else {
            node = first.next;
        }
        for (int i = 0; i < index; i++) {
            if (node.next == first) {
                node = node.next.next;
            } else {
                node = node.next;
            }
        }
        return node;
    }
}
