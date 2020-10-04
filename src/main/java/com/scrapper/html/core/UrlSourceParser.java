package com.scrapper.html.core;

import com.scrapper.html.util.UrlUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser for a URL which internally uses {@link Jsoup} and maintains the parsed document in it's memory for further processing if needed.
 */
public class UrlSourceParser {

    private final Logger logger = Logger.getLogger(UrlSourceParser.class);
    private final int wordLengthThreshold;
    private final String baseUrl;
    private Document document;

    private List<String> listOfWords = new ArrayList<>();
    private List<String> listOfWordPairs = new ArrayList<>();

    /**
     * Initializes an instance of {@link UrlSourceParser} with the url and wordLengthThreshold (minimum length of the word needed for consideration).
     * @param url
     * @param wordLengthThreshold
     */
    public UrlSourceParser(String url, int wordLengthThreshold) {
        this.wordLengthThreshold = wordLengthThreshold;
        this.baseUrl = url;
        init(url);
    }

    private void init(String url) {
        try {
            logger.info("Processing Url: " + url);
            Document doc = Jsoup.connect(url).get();
            this.setDocument(doc);
        } catch (IOException ex) {
            //ex.printStackTrace();
            logger.error("Skipping Url: " + url);
        }
    }

    private void setDocument(Document doc) {
        this.document = doc;
    }

    /**
     * Processes words from Document Body of the URL, which maintains a lit of valid words as well as word pairs.
     */
    public void processWords() {
        Elements elements = this.document.body().select("*");
        for (Element element : elements) {
            String text = element.ownText().trim();
            if (!text.isEmpty()) {
                String[] words = Arrays.stream(text.split("\\s+|,\\s*|\\.\\s*"))
                        .map(String::trim)
                        .filter(w -> w.length() != 0)
                        .toArray(String[]::new);
                int sentenceLength = words.length;
                for (int i = 0; i < sentenceLength; i++) {
                    if (!isValidWord(words[i])) continue;
                    listOfWords.add(words[i]);
                    if (i < sentenceLength - 1 && isValidWord(words[i + 1])) {
                        listOfWordPairs.add(words[i] + "$" + words[i + 1]);
                    }
                }
            }
        }
    }

    private boolean isValidWord(String word) {
        if(word.length() < wordLengthThreshold) return false;
        Pattern pattern = Pattern.compile("[^A-Za-z]");
        Matcher matcher = pattern.matcher(word);
        return !matcher.find();
    }

    /**
     * Returns a list of Urls present in the document.
     * It takes care of resolution of the relative URLs as well.
     * @return
     */
    public List<String> getDocumentUrls() {
        Set<String> set = new HashSet<>();
        Elements links = this.document.select("a[href]");
        for (Element link : links) {
            String href = link.attr("href");
            String uri = href;
            if (href.contains("javascript")) {
                continue;
            }
            if (!href.startsWith("http")) {
                uri = UrlUtil.getResolvedUrl(baseUrl, href);
            }
            if (uri != null) {
                set.add(uri);
            }
        }
        return new ArrayList<>(set);
    }

    /**
     * Returns true id the URL document is parsable.
     * Returns false if either document or document's body is null
     * @return
     */
    public boolean isParsable() {
        return this.document != null && this.document.body() != null;
    }

    /**
     * Returns list of words in the document
     * @return
     */
    public List<String> getListOfWords() {
        return listOfWords;
    }

    /**
     * Returns list of word pairs in the document
     * @return
     */
    public List<String> getListOfWordPairs() {
        return listOfWordPairs;
    }
}
