package com.guardwarm.struct.tree;

import java.util.Comparator;
/**
 * @author wh
 */
@SuppressWarnings("all")
public class BinarySearchTree<E> extends BinaryTree<E> {

    private final Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E element) {
        elementNotNullCheck(element);
        if (size == 0) {
            root = createNewNode(element, null);
            size++;
            afterAdd(root);
            return;
        }
        Node<E> node = root;
        Node<E> parent = root;
        int cmp = 0;
        while (node != null) {
            parent = node;
            cmp = compare(element, node.element);
            if (cmp > 0) {
                node = node.right;
            } else if (cmp == 0) {
                node.element = element;
                return;
            } else {
                node = node.left;
            }
        }
        Node<E> newNode = createNewNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        afterAdd(newNode);
    }

    private void elementNotNullCheck(E element) {
        if (null == element) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    protected void afterAdd(Node<E> node) {}

    public boolean contains(E element) {
        return node(element) != null;
    }

    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        if (node == null) {
            return;
        }
        size--;
        if (node.hasTwoChild()) {
            Node<E> success = successor(node);
            node.element = success.element;
            node = success;
        }
        // node度必为0或1 但度为1时左右节点都有可能
        Node<E> replacement = node.left != null ?
                node.left : node.right;
        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
            } else if (node.isLeftChild()) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
            afterRemove(replacement);
        } else if (node.parent == null) {
            // 度为0且是根节点
            root = null;
        } else {
            // 度为0且不是根节点
            if (node.isLeftChild()) {
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
            afterRemove(node);
        }
    }

    protected void afterRemove(Node<E> node) {}

    private Node<E> node(E element) {
        elementNotNullCheck(element);
        Node<E> node = root;
        int cmp;
        while (node != null) {
            cmp = compare(node.element, element);
            if (cmp == 0) {
                break;
            } else if (cmp > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable) e1).compareTo(e2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, sb, "");
        return sb.toString();
    }
    private void toString(Node<E> node,
                          StringBuilder sb, String prefix) {
        if (node == null) {
            return;
        }
        sb.append(prefix).append(node.element).append("\n");
        toString(node.left, sb, prefix + "L---");
        toString(node.right, sb, prefix + "R---");
    }
}