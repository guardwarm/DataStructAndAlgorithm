package com.guardwarm.struct.trie;

import java.util.HashMap;

public class Trie<V> {
    private int size;
    Node<V> root;

    private static class Node<V> {
        Node<V> parent;
        HashMap<Character, Node<V>> children;
        Character character;
        V value;
        boolean isWord;
        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("Key must not be empty");
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return (node != null && node.isWord) ?
                node.value : null;
    }

    public boolean contains(String key) {
        Node<V> node = node(key);
        return node != null && node.isWord;
    }

    public boolean startsWith(String prefix) {
        return node(prefix) != null;
    }

    public V add(String key, V value) {
        keyCheck(key);
        if (root == null) {
            root = new Node<>(null);
        }
        Node<V> node = root;
        for (char c : key.toCharArray()) {
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                node.children = emptyChildren ?
                        new HashMap<Character, Node<V>>() : node.children;
                node.children.put(c, childNode);
            }
            node = childNode;
        }
        if (node.isWord) {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }
        node.value = value;
        node.isWord = true;
        size++;
        return null;
    }

    public V remove(String key) {
        keyCheck(key);
        Node<V> node = node(key);
        if (node == null || !node.isWord) {
            return null;
        }
        size--;
        V oldValue = node.value;
        if (node.children != null && !node.children.isEmpty()) {
            node.value = null;
            node.isWord = false;
            return oldValue;
        }
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (parent.isWord || !parent.children.isEmpty()) {
                break;
            }
            node = parent;
        }
        return oldValue;
    }

    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        for (char c : key.toCharArray()) {
            if (node == null ||
            node.children == null ||
            node.children.isEmpty()) {
                return null;
            }
            node = node.children.get(c);
        }
        return node;
    }
}
