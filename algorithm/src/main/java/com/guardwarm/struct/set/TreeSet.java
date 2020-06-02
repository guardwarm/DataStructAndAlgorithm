package com.guardwarm.struct.set;

import com.guardwarm.struct.tree.BinaryTree;
import com.guardwarm.struct.tree.RBTree;

import java.util.Comparator;

public class TreeSet<E> implements Set<E>{
    private final RBTree<E> rbTree;

    public TreeSet() {
        rbTree = new RBTree<>();
    }

    public TreeSet(Comparator<E> comparator) {
        rbTree = new RBTree<>(comparator);
    }

    public int size() {
        return rbTree.size();
    }

    public boolean isEmpty() {
        return rbTree.isEmpty();
    }

    @Override
    public void clear() {
        rbTree.clear();
    }

    @Override
    public boolean contains(E element) {
        return rbTree.contains(element);
    }

    @Override
    public void add(E element) {
        rbTree.add(element);
    }

    @Override
    public void remove(E element) {
        rbTree.remove(element);
    }

    @Override
    public void traversal(final Visitor<E> visitor) {
        rbTree.inorder(new BinaryTree.AbstractVisitor<E>() {
            @Override
            public boolean visit(E element) {
                return visitor.visit(element);
            }
        });
    }

}
