package com.guardwarm.struct.trie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrieTest {

    @Before
    public void setUp() throws Exception {
        Trie<Integer> trie = new Trie<>();
        trie.add("cat", 1);
        trie.add("dog", 2);
        trie.add("catalog", 3);
        trie.add("cast", 4);
        trie.add("小码哥", 5);
        System.out.println(trie.remove("catalog"));
        Assert.assertTrue(trie.startsWith("catal"));
    }

    @Test
    public void size() {
    }
}