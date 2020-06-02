package com.guardwarm.struct.map;

import java.util.Objects;

@SuppressWarnings("unused")
public class LinkedHashMap<K, V> extends HashMap<K, V> {
    private LinkedNode<K, V> first;
    private LinkedNode<K, V> last;

    @Override
    public void clear() {
        first = null;
        last = null;
        super.clear();
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (Objects.equals(value, node.value)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removeNode) {
        if (willNode == null || removeNode == null) {
            return;
        }
        LinkedNode<K, V> node1 = (LinkedNode<K, V>) willNode;
        LinkedNode<K, V> node2 = (LinkedNode<K, V>) removeNode;
        // 如果删除的是有两个子节点的节点，发生了后继和原节点替换
        // 此时需将两节点交换，不然删除后会改变原链表顺序
        if (node1 != node2) {
            LinkedNode<K, V> temp = node1.prev;
            node1.prev = node2.prev;
            node2.prev = temp;
            if (node1.prev == null) {
                first = node1;
            } else {
                node1.prev.next = node1;
            }
            if (node2.prev == null) {
                first = node2;
            } else {
                node2.prev.next = node2;
            }

            temp = node1.next;
            node1.next = node2.next;
            node2.next = temp;
            if (node1.next == null) {
                last = node1;
            } else {
                node1.next.prev = node1;
            }
            if (node2.next == null) {
                last = node2;
            } else {
                node1.next.prev = node2;
            }
        }
        LinkedNode<K, V> prev = node2.prev;
        LinkedNode<K, V> next = node2.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    @Override
    public void traversal(Visit<K, V> visitor) {
        if (visitor == null) {
            return;
        }
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (visitor.visit(node.key, node.value)) {
                return;
            }
            node = node.next;
        }
    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K, V> node = new LinkedNode<>(key, value, parent);
        if (first == null) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }

    private static class LinkedNode<K, V> extends Node<K, V> {
        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;

        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}
