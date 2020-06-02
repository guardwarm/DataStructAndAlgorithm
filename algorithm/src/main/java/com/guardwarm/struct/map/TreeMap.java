package com.guardwarm.struct.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @param <K> 键类型
 * @param <V> 值类型
 * @author wh
 */
@SuppressWarnings("unused")
public class TreeMap<K, V> implements Map<K, V> {
    private int size;
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private final Comparator<K> comparator;
    private Node<K, V> root;

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public TreeMap() {
        this(null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        Node<K, V> node;
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (valEquals(value, node.value)) {
                return true;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V put(K key, V value) {
        keyNotNullCheck(key);
        if (size == 0) {
            root = new Node<>(key, value, null);
            size++;
            afterPut(root);
            return null;
        }
        Node<K, V> node = root;
        Node<K, V> parent = root;
        int cmp = 0;
        while (node != null) {
            parent = node;
            cmp = compare(key, node.key);
            if (cmp > 0) {
                node = node.right;
            } else if (cmp == 0) {
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            } else {
                node = node.left;
            }
        }
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        afterPut(newNode);
        return null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    private V remove(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        size--;
        V oldValue = node.value;
        if (node.hasTwoChild()) {
            Node<K, V> success = successor(node);
            node.key = success.key;
            node.value = success.value;
            node = success;
        }
        // node度必为0或1 但度为1时左右节点都有可能
        Node<K, V> replacement = node.left != null ?
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
            } else {
                node.parent.right = null;
            }
            afterRemove(node);
        }
        return oldValue;
    }

    private void afterRemove(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        // 删除的是根节点
        if (parent == null) {
            return;
        }

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        // 被删除的节点在左边，兄弟节点在右边
        if (left) {
            // 兄弟节点是红色
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    // 除非是一连串的黑色节点，这个才会反复调用
                    // 显然是不可能的
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                sibling.color = colorOf(parent);
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else {
            // 被删除的节点在右边，兄弟节点在左边
            // 兄弟节点是红色
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                sibling.color = colorOf(parent);
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    @Override
    public void traversal(Visit<K, V> visitor) {
        if (visitor == null) {
            return;
        }
        traversal(root, visitor);
    }

    private void traversal(Node<K, V> node, Visit<K, V> visitor) {
        if (node == null || visitor.stop) {
            return;
        }
        traversal(node.left, visitor);
        if (visitor.stop) {
            return;
        }
        visitor.stop = visitor.visit(node.key, node.value);
        traversal(node.right, visitor);
    }

    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        int cmp;
        while (node != null) {
            cmp = compare(key, node.key);
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                return node;
            }
        }
        return null;
    }

    protected void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        if (parent == null) {
            // 即使不断上溢到根节点，根节点也还是原来那个根节点
            // 只需染色，无需更改root指向
            black(node);
            return;
        }
        if (isBlack(parent)) {
            return;
        }
        Node<K, V> uncle = parent.sibling();
        Node<K, V> grand = parent.parent;
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            afterPut(red(grand));
            return;
        }
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                black(parent);
            } else {
                black(node);
                rotateLeft(parent);
            }
            rotateRight(red(grand));
        } else {
            if (node.isRightChild()) {
                black(parent);
            } else {
                black(node);
                rotateRight(parent);
            }
            rotateLeft(red(grand));
        }
    }

    protected Node<K, V> predecessor(Node<K, V> node) {
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

    protected Node<K, V> successor(Node<K, V> node) {
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

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        parent.parent = grand.parent;
        if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else {
            root = parent;
        }
        if (child != null) {
            child.parent = grand;
        }
        grand.parent = parent;
    }

    private Node<K, V> red(Node<K, V> node) {
        node.color = RED;
        return node;
    }

    private Node<K, V> black(Node<K, V> node) {
        node.color = BLACK;
        return node;
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    private int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        //noinspection unchecked
        return ((Comparable<K>) k1).compareTo(k2);
    }

    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }

    private static class Node<K, V> {
        K key;
        V value;
        boolean color = RED;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
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

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }
}
