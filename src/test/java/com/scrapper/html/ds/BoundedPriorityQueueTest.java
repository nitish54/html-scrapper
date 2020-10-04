package com.scrapper.html.ds;

import com.scrapper.html.model.Word;
import org.junit.Assert;
import org.junit.Test;

public class BoundedPriorityQueueTest {

    @Test
    public void testAdd() {
        BoundedPriorityQueue<Word> boundedPriorityQueue = new BoundedPriorityQueue<>(new Word.WordComparator(), 1);
        Word word = new Word("word", 10);
        Word anotherWord = new Word("anotherword", 50);
        boundedPriorityQueue.add(word);
        boundedPriorityQueue.add(anotherWord);
        Assert.assertArrayEquals(new Word[]{anotherWord}, boundedPriorityQueue.toArray());
    }
}
