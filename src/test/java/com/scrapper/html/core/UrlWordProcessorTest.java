package com.scrapper.html.core;

import com.scrapper.html.model.Word;
import com.scrapper.html.props.UrlProcessInitProperties;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UrlWordProcessorTest {

    private static UrlWordProcessor processor;

    @BeforeClass
    public static void init(){
        UrlProcessInitProperties properties = UrlProcessInitProperties.INSTANCE;
        properties.setUrl("https://spring.io/guides/gs/securing-web/");
        properties.setDepth(1);
        properties.setWordLengthThreshold(3);
        processor = new UrlWordProcessor(properties);
        processor.process();
    }

    @Test
    public void testGetTop10Words() {
        List<Word> top10Words = processor.getTop10Words();
        Assert.assertThat(top10Words, CoreMatchers.everyItem(CoreMatchers.isA(Word.class)));
        List<Word> expectedSortedList = new ArrayList<>(top10Words);
        Collections.sort(expectedSortedList, new Word.WordComparator());
        Assert.assertThat(top10Words, Matchers.contains(expectedSortedList.toArray()));
    }

    @Test
    public void testGetTop10WordPairs() {
        List<Word> top10WordPairs = processor.getTop10WordPairs();
        Assert.assertThat(top10WordPairs, CoreMatchers.everyItem(CoreMatchers.isA(Word.class)));
        List<Word> expectedSortedList = new ArrayList<>(top10WordPairs);
        Collections.sort(expectedSortedList, new Word.WordComparator());
        Assert.assertThat(top10WordPairs, Matchers.contains(expectedSortedList.toArray()));
    }
}
