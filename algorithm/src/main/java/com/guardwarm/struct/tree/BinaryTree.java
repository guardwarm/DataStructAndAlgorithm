package com.guardwarm.struct.tree;

import com.guardwarm.struct.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author asus
 */
@SuppressWarnings("unused")
public class BinaryTree<E> implements BinaryTreeInfo {
    protected int size;
    protected Node<E> root;

    protected static class Node<E> {
        E element;
        Node<E> parent;
        Node<E> left;
        Node<E> right;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public boolean hasTwoChild() {
            return left != null && right != null;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public Node<E> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }else if (isRightChild()) {
                return parent.left;
            }else {
                return null;
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    '}';
        }
    }

    public static abstract class AbstractVisitor<E> {
        /**
         * 为true时表示停止遍历
         */
        boolean stop;

        /**
         * 对元素经行什么处理
         *
         * @param element 遍历到的元素
         * @return 是否结束遍历  true则结束
         */
        public abstract boolean visit(E element);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public void preorder() {
        preorder(root);
    }

    private void preorder(Node<E> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.element);
        preorder(node.left);
        preorder(node.right);
    }

    public void preorder(AbstractVisitor<E> visitor) {
        if (visitor == null || root == null) {
            return;
        }
        // 递归实现前序遍历
        preorder(root, visitor);
        // 遍历实现前序遍历
//        Stack<Node<E>> stack = new Stack<>();
//        stack.push(root);
//        while (!stack.isEmpty()) {
//            Node<E> node = stack.pop();
//            if (visitor.visit(node.element)) {
//                return;
//            }
//            if (node.right != null) {
//                stack.push(node.right);
//            }
//            if (node.left != null) {
//                stack.push(node.left);
//            }
//        }
        // 遍历实现前序遍历2
//        Stack<Node<E>> stack = new Stack<>();
//        Node<E> node = root;
//        while (true) {
//            if (node != null) {
//                if (visitor.visit(node.element)) {
//                    return;
//                }
//                if (node.right != null) {
//                    stack.push(node.right);
//                }
//                node = node.left;
//            }else if (stack.isEmpty()) {
//                return;
//            }else {
//                node = stack.pop();
//            }
//        }
    }

    private void preorder(Node<E> node, AbstractVisitor<E> visitor) {
        if (node == null || visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.element);
        preorder(node.left, visitor);
        preorder(node.right, visitor);
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(Node<E> node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.element);
        inorder(node.right);
    }

    public void inorder(AbstractVisitor<E> visitor) {
        if (visitor == null || root == null) {
            return;
        }
        // 利用递归实现中序遍历
//        inorder(root, visitor);
        // 利用迭代实现中序遍历
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = root;
        while (true) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            }else if (stack.isEmpty()) {
                return;
            }else {
                node = stack.pop();
                if (visitor.visit(node.element)) {
                    return;
                }
                node = node.right;
            }
        }
    }

    private void inorder(Node<E> node, AbstractVisitor<E> visitor) {
        if (node == null || visitor.stop) {
            return;
        }
        inorder(node.left, visitor);
        if (visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.element);
        inorder(node.right, visitor);
    }

    public void postorder() {
        postorder(root);
    }

    private void postorder(Node<E> node) {
        if (node == null) {
            return;
        }
        postorder(node.left);
        postorder(node.right);
        System.out.print(node.element);
    }

    public void postorder(AbstractVisitor<E> visitor) {
        if (visitor == null || root == null) {
            return;
        }
        // 递归实现后序遍历
//        postorder(root, visitor);
        // 迭代实现后序遍历
        // 记录上一次弹出访问的节点
        Node<E> prev = null;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<E> top = stack.peek();
            // 栈顶元素为叶子节点或者是之前弹出节点的父节点
            if (top.isLeaf() || (prev != null && prev.parent == top)) {
                prev = stack.pop();
                // 访问节点
                if (visitor.visit(prev.element)) return;
            } else {
                if (top.right != null) {
                    stack.push(top.right);
                }
                if (top.left != null) {
                    stack.push(top.left);
                }
            }
        }
    }

    private void postorder(Node<E> node, AbstractVisitor<E> visitor) {
        if (node == null || visitor.stop) {
            return;
        }
        postorder(node.left, visitor);
        postorder(node.right, visitor);
        if (visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.element);
    }

    public void levelOrder() {
        if (root == null) {
            return;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        Node<E> node;
        while (!queue.isEmpty()) {
            node = queue.poll();
            System.out.println(node);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public void levelOrder(AbstractVisitor<E> visitor) {
        if (root == null || visitor == null) {
            return;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        Node<E> node;
        queue.offer(root);
        while (!queue.isEmpty()) {
            node = queue.poll();
            visitor.stop = visitor.visit(node.element);
            if (visitor.stop) {
                break;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public int height() {
        if (root == null) {
            return 0;
        }
        Node<E> node;
        int height = 0;
        int levelCount = 1;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            node = queue.poll();
            levelCount--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelCount == 0) {
                levelCount = queue.size();
                height++;
            }
        }
        return height;
    }

    private int height(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height((node.right)));
    }

    protected Node<E> predecessor(Node<E> node) {
        if (node == null) {
            return null;
        }
        node = node.left;
        if (node != null) {
            while (node.right != null) {
                node = node.right;
            }
            return node;
        } else {
            while (node.parent != null && node == node.parent.left) {
                node = node.parent;
            }
            // node.parent == null
            // node == node.parent.right
            return node.parent;
        }
    }

    protected Node<E> successor(Node<E> node) {
        if (node == null) {
            return null;
        }
        node = node.right;
        if (node != null) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        } else {
            while (node.parent != null && node == node.parent.right) {
                node = node.parent;
            }
            return node.parent;
        }
    }

    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 利用层序遍历访问所有节点
     * 左右都不为空   无需处理，入队即可
     * 左为空，右不为空 返回false
     * 左不为空，右为空 之后都是叶子节点
     * 左右皆为空  之后必须全是叶子节点
     * @return  是否是完全二叉树
     */
    public boolean isCompleteBinaryTree() {
        if (root == null) {
            return false;
        }
        Node<E> node;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (leaf && !node.isLeaf()) {
                return false;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }else if (node.right != null){
                return false;
            }
            if (node.right != null) {
                queue.offer(node.right);
            }else {
                // 处理左右为空和左不空有空
                leaf = true;
            }
        }
        return true;
    }

    protected Node<E> createNewNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        BinarySearchTree.Node<E> myNode = (Node<E>) node;
        StringBuilder sb = new StringBuilder();
        sb.append(myNode);
        return sb;
    }
}
