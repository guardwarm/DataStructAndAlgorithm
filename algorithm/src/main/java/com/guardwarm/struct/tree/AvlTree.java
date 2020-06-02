package com.guardwarm.struct.tree;

import java.util.Comparator;

/**
 * @author asus
 */
@SuppressWarnings("unused")
public class AvlTree<E> extends BalanceBinarySearchTree<E> {
    public AvlTree() {
        this(null);
    }

    public AvlTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalance(node)) {
                updateHeight(node);
            }else {
                reBalance(node);
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalance(node)) {
                updateHeight(node);
            }else {
                reBalance(node);
            }
        }
    }

    private void reBalance(Node<E> grand) {
        Node<E> parent = ((AvlNode<E>)grand).tallerChild();
        Node<E> node = ((AvlNode<E>)parent).tallerChild();
        if (parent.isLeftChild()) {
            if (node == parent.left){
                rotateRight(grand);
            }else {
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else {
            if (node == parent.left) {
                rotateRight(parent);
            }
            rotateLeft(grand);
        }
    }

    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);
        // 由于更新内部没有使用递归，所以使用的时候需先更新子树
        updateHeight(grand);
        updateHeight(parent);
    }

    private void updateHeight(Node<E> node) {
        ((AvlNode<E>)node).updateHeight();
    }

    private boolean isBalance(Node<E> node) {
        return Math.abs(((AvlNode<E>)node).balanceFactor()) <= 1;
    }

    private static class AvlNode<E> extends Node<E> {
        int height = 1;

        public AvlNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AvlNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AvlNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AvlNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AvlNode<E>) right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AvlNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AvlNode<E>) right).height;
            if (leftHeight > rightHeight) {
                return left;
            } else if (leftHeight < rightHeight) {
                return right;
            } else {
                return this == parent.left ? left : right;
            }
        }


        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")"+balanceFactor();
        }
    }

    @Override
    protected Node<E> createNewNode(E element, Node<E> parent) {
        return new AvlNode<>(element, parent);
    }
}
