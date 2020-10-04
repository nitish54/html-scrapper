package com.scrapper.html.ds;

import org.junit.Assert;
import org.junit.Test;

public class TrieTest {

    @Test
    public void testInsert() {
        Trie trie = new Trie();
        trie.insert("word");
        Assert.assertEquals(true, trie.find("word"));
    }
}
