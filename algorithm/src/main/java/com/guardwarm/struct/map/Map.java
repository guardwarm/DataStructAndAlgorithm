package com.guardwarm.struct.map;

@SuppressWarnings("unused")
public interface Map<K, V> {
    int size();
    boolean isEmpty();
    void clear();
    boolean containsKey(K key);
    boolean containsValue(V value);

    V get(K key);
    V put(K key, V value);
    V remove(K key);
    void traversal(Visit<K, V> visit);

    abstract class Visit<K, V> {
        boolean stop;
        public abstract boolean visit(K key, V value);
    }
}
