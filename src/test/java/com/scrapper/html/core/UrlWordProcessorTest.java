package com.scrapper.html.core;

import com.scrapper.html.model.Word;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class UrlWordProcessorTest {
    public static final int URL_NESTING_MAX_LEVEL = 1;
    final UrlWordProcessor processor = new UrlWordProcessor("https://orrsella.gitbooks.io/soft-eng-interview-prep/content/topics/complexity.html", URL_NESTING_MAX_LEVEL);

    @Test
    public void testProcess() {
        processor.process();
        Assert.assertThat(processor.getTop10Words(), CoreMatchers.everyItem(CoreMatchers.isA(Word.class)));
        // test for decreasing frequency
    }
}
