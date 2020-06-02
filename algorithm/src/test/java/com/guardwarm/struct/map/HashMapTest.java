package com.guardwarm.struct.map;

import com.guardwarm.model.Key;
import com.guardwarm.model.SubKey1;
import com.guardwarm.model.SubKey2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashMapTest {
    LinkedHashMap<Object, Integer> map;
    @Before
    public void setUp() {
        map = new LinkedHashMap<>();
    }

    @Test
    public void size() {
    }

    /**
     * 测试hash冲突时能否正常存取值
     */
    @Test
    public void testGet() {
        for (int i = 1; i <= 20; i++) {
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            map.put(new Key(i), i + 5);
        }
        assertEquals(map.size(), 20);
        assertEquals(4, (int) map.get(new Key(4)));
        assertEquals(10, (int) map.get(new Key(5)));
        assertEquals(11, (int) map.get(new Key(6)));
        assertEquals(12, (int) map.get(new Key(7)));
        assertEquals(8, (int) map.get(new Key(8)));
        map.traversal(new Map.Visit<Object, Integer>() {
            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    @Test
    public void test2() {
        map.put(null, 1); // 1
        map.put(new Object(), 2); // 2
        map.put("jack", 3); // 3
        map.put(10, 4); // 4
        map.put(new Object(), 5); // 5
        map.put("jack", 6);
        map.put(10, 7);
        map.put(null, 8);
        map.put(10, null);
        assertEquals(5, map.size());
        assertEquals(8, (int) map.get(null));
//        System.out.println(map.get("jack"));
        assertEquals(6, (int) map.get("jack"));
        assertNull(map.get(10));
        assertNull(map.get(new Object()));
        assertTrue(map.containsKey(10));
        assertTrue(map.containsKey(null));
        assertTrue(map.containsValue(null));
        assertFalse(map.containsValue(1));
    }

    @Test
    public void test3() {
        for (int i = 1; i <= 20; i++) {
            map.put(new SubKey1(i), i);
        }
        map.put(new SubKey2(1), 5);
        assertTrue(map.get(new SubKey1(1)) == 5);
        assertTrue(map.get(new SubKey2(1)) == 5);
        assertTrue(map.size() == 20);
    }

    @Test
    public void test4() {
        map.put("jack", 1);
        map.put("rose", 2);
        map.put("jim", 3);
        map.put("jake", 4);
        map.remove("jack");
        map.remove("jim");
        for (int i = 1; i <= 10; i++) {
            map.put("test" + i, i);
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            assertTrue(map.remove(new Key(i)) == i);
        }
        for (int i = 1; i <= 3; i++) {
            map.put(new Key(i), i + 5);
        }
        assertTrue(map.size() == 19);
        assertTrue(map.get(new Key(1)) == 6);
        assertTrue(map.get(new Key(2)) == 7);
        assertTrue(map.get(new Key(3)) == 8);
        assertTrue(map.get(new Key(4)) == 4);
        assertTrue(map.get(new Key(5)) == null);
        assertTrue(map.get(new Key(6)) == null);
        assertTrue(map.get(new Key(7)) == null);
        assertTrue(map.get(new Key(8)) == 8);
    }
}