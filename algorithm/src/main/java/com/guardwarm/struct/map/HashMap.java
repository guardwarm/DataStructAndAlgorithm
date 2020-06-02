package com.guardwarm.struct.map;

import com.guardwarm.struct.printer.BinaryTreeInfo;
import com.guardwarm.struct.printer.BinaryTrees;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@SuppressWarnings("unused")
public class HashMap<K, V> implements Map<K, V> {
    private int size;
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private Node<K, V>[] table;
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        table = new Node[capacity];
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
        if (size == 0) {
            return;
        }
        // 将数组内的引用清空
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V put(K key, V value) {
        resize();
        int index = index(key);
        Node<K, V> root = table[index];
        if (root == null) {
            root = createNode(key, value, null);
            table[index] = root;
            size++;
            fixAfterPut(root);
            return null;
        }
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k2;
        int h2;
        Node<K, V> result = null;
        int h1 = hash(key);
        boolean searched = false;
        do {
            parent = node;
            k2 = node.key;
            h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            }else if (h1 < h2) {
                cmp = -1;
            }else if (Objects.equals(key, k2)) {
                cmp = 0;
            }else if (key != null && k2 != null
                    && key.getClass() == k2.getClass()
                    && key instanceof Comparable
                    && (cmp = ((Comparable)key).compareTo(k2)) != 0) {
            }else if (searched) {
                cmp = System.identityHashCode(key) - System.identityHashCode(k2);
            }else {
                if ((node.left != null && (result = node(node.left, key)) != null)
                        || (node.right != null && (result = node(node.right, key)) != null)) {
                    node = result;
                    cmp = 0;
                }else {
                    searched = true;
                    cmp = System.identityHashCode(key) - System.identityHashCode(k2);
                }
            }
            if (cmp > 0) {
                node = node.right;
            }else if (cmp < 0) {
                node = node.left;
            }else {
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
                return oldValue;
            }
        }while (node != null);
        Node<K, V> newNode = createNode(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;
        fixAfterPut(newNode);
        return null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    protected V remove(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        size--;
        Node<K, V> willNode = node;
        V oldValue = node.value;
        if (node.hasTwoChild()) {
            Node<K, V> success = successor(node);
            node.key = success.key;
            node.value = success.value;
            node.hash = success.hash;
            node = success;
        }
        // node度必为0或1 但度为1时左右节点都有可能
        Node<K, V> replacement = node.left != null ?
                node.left : node.right;
        int index = index(node);
        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) {
                table[index] = replacement;
            } else if (node.isLeftChild()) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
            fixAfterRemove(replacement);
        } else if (node.parent == null) {
            // 度为0且是根节点
            table[index] = null;
        } else {
            // 度为0且不是根节点
            if (node.isLeftChild()) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            fixAfterRemove(node);
        }
        afterRemove(willNode, node);
        return oldValue;
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> kvNode : table) {
            if (kvNode == null) {
                continue;
            }
            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) {
                    return true;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    /**
     * 对哈希表进行扩容
     * 本质：遍历所有节点，将其移动到新表
     */
    private void resize() {
        if (size / table.length <= DEFAULT_LOAD_FACTOR) {
            return;
        }
        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length << 1];
        Queue<Node<K,V>> queue = new LinkedList<>();
        Node<K,V> tempNode;
        for (Node<K, V> node : oldTable) {
            if (node == null) {
                continue;
            }
            queue.offer(node);
            while (!queue.isEmpty()) {
                tempNode = queue.poll();
                if (tempNode.left != null) {
                    queue.offer(tempNode.left);
                }
                if (tempNode.right != null) {
                    queue.offer(tempNode.right);
                }
                // 先将左右节点入队再移动节点
                moveNode(tempNode);
            }
        }
    }

    private void moveNode(Node<K, V> newNode) {
        // 将节点的k，v之外的属性重置
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;

        int index = index(newNode);
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = newNode.hash;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            }else if (h1 < h2) {
                cmp = -1;
                // 这是在移动，所以原数据中不可能存在相等覆盖的情况
            }else if (k1 != null && k2 != null
            && k1.getClass() == k2.getClass()
            && k1 instanceof Comparable
            && (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
            }else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            }else {
                // 从上面走下来不可能出现==0的情况
                node = node.left;
            }
        }while (node != null);

        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        fixAfterPut(newNode);
    }

    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    protected void afterRemove(Node<K, V> willNode, Node<K, V> removeNode) {}

    /**
     * 根据key获取其在桶中位置
     * @param key   存储的键
     * @return  桶中的位置索引
     */
    private int index(K key) {
        return hash(key) & (table.length - 1);
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    private int index(Node<K, V> node) {
        return (node.hash & (table.length - 1));
    }

    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }

    private Node<K,V> node(Node<K, V> node, K key) {
        int h1 = hash(key);
        Node<K, V> result = null;
        int cmp;
        K k2;
        int h2;
        // 存放时按一定规则存放，取出时再按这个规则查找，可以极大提高效率
        while (node != null) {
            k2 = node.key;
            h2 = node.hash;
            if (h1 > h2) {
                node = node.right;
            }else if (h1 < h2) {
                node = node.left;
            }else if (Objects.equals(key, k2)){
                return node;
            }else if (key != null && k2 != null
            && key.getClass() == k2.getClass()
            && key instanceof Comparable
            && (cmp = ((Comparable) key).compareTo(k2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            }else if (node.right != null && (result = node(node.right, key)) != null) {
                return result;
            }else {
                node = node.left;
            }
        }
        return null;
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
            // table[index(grand)]等价于这棵树的根节点
            table[index(grand)] = parent;
        }
        if (child != null) {
            child.parent = grand;
        }
        grand.parent = parent;
    }

    protected void fixAfterPut(Node<K, V> node) {
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
            fixAfterPut(red(grand));
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

    private void fixAfterRemove(Node<K, V> node) {
        if (isRed(node)) {
            black(node);
            return;
        }
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
                    fixAfterRemove(parent);
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
                    fixAfterRemove(parent);
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
        if (size == 0 || visitor == null) {
            return;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> kvNode : table) {
            if (kvNode == null) {
                continue;
            }
            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) {
                    return;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }

    private Node<K, V> red(Node<K, V> node) {
        if (node != null) {
            node.color = RED;
        }
        return node;
    }

    private Node<K, V> black(Node<K, V> node) {
        if (node != null) {
            node.color = BLACK;
        }
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


    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index = " + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object string(Object node) {
                    return node;
                }

                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>)node).right;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>)node).left;
                }
            });
            System.out.println("---------------------------------------------------");
        }
    }

    protected  static class Node<K, V> {
        // 在节点内保存会浪费空间，但可以节省重复计算hashcode的时间
        int hash;
        K key;
        V value;
        boolean color = RED;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;

        public Node(K key, V value, Node<K, V> parent) {
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
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

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }
}
