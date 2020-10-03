package com.scrapper.html.core;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UrlSourceParserTest {

    public static final int WORD_LENGTH_THRESHOLD = 2;
    final UrlSourceParser parser = new UrlSourceParser("https://orrsella.gitbooks.io/soft-eng-interview-prep/content/topics/complexity.html", WORD_LENGTH_THRESHOLD);

    @Test
    public void testExtractWords() {
        parser.processWords();
        List<String> words = parser.getListOfWords();
        words.forEach(word -> Assert.assertEquals(word.length() >= WORD_LENGTH_THRESHOLD, true));
    }

    @Test
    public void testGetDocumentUrls() {
        List<String> response = parser.getDocumentUrls();
        Assert.assertThat(response, CoreMatchers.everyItem(CoreMatchers.startsWith("http")));
    }

    @Test
    public void testIsParsable(){
        Assert.assertEquals(parser.isParsable(), true);
    }
}
