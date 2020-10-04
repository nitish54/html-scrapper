package com.scrapper.html.core;

import com.scrapper.html.props.UrlProcessInitProperties;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class UrlSourceParserTest {

    private static UrlSourceParser urlSourceParser;

    @BeforeClass
    public static void init(){
        UrlProcessInitProperties properties = UrlProcessInitProperties.INSTANCE;
        properties.setUrl("https://spring.io/guides/gs/securing-web/");
        properties.setDepth(1);
        properties.setWordLengthThreshold(3);
        urlSourceParser = new UrlSourceParser(properties.getUrl(), properties.getWordLengthThreshold());
    }

    @Test
    public void testExtractWords() {
        urlSourceParser.processWords();
        List<String> words = urlSourceParser.getListOfWords();
        words.forEach(word -> Assert.assertEquals(word.length() >= UrlProcessInitProperties.INSTANCE.getWordLengthThreshold(), true));
    }

    @Test
    public void testGetDocumentUrls() {
        List<String> response = urlSourceParser.getDocumentUrls();
        Assert.assertThat(response, CoreMatchers.everyItem(CoreMatchers.startsWith("http")));
    }

    @Test
    public void testIsParsable(){
        Assert.assertEquals(urlSourceParser.isParsable(), true);
    }
}
