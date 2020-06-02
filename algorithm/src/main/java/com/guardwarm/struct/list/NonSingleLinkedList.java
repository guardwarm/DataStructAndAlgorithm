package com.guardwarm.struct.list;
/**
 * @author asus
 */
public class NonSingleLinkedList<E> extends AbstractList<E> {
    Node<E> first;
    Node<E> last;
    public NonSingleLinkedList() {
        first = new Node<>(null, null, null);
        last = new Node<>(null, null, null);
    }
    private static class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;
        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
    @Override
    public void clear() {
        first.next = null;
        last.next = null;
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
        rangeCheckForAdd(index);
        Node<E> prev = index == 0 ? first : node(index - 1);
        Node<E> next = index == size ? last : prev.next;
        prev.next = new Node<>(element, prev, next);
        next.prev = prev.next;
        size ++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> prev = index == 0 ? first : node(index - 1);
        Node<E> next = index == size - 1 ? last : prev.next.next;
        E old = prev.next.element;
        prev.next = next;
        next.prev = prev;
        size--;
        return old;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first.next;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                }
                node = node.next;
            }
        }else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) {
                    return i;
                }
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    private Node<E> node(int index) {
        rangeCheck(index);
        Node<E> node;
        if (index > (size >> 1)) {
            node = last.prev;
            for (int i = 0; i < size - index - 1; i++) {
                node = node.prev;
            }
        }else {
            node = first.next;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }
}
