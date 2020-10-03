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

public class UrlSourceParser {

    private final Logger logger = Logger.getLogger(UrlSourceParser.class);
    private final int wordLengthThreshold;
    private final String baseUrl;
    private Document document;

    private List<String> listOfWords = new ArrayList<>();
    private List<String> listOfWordPairs = new ArrayList<>();

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

    public boolean isParsable() {
        return this.document != null && this.document.body() != null;
    }

    public List<String> getListOfWords() {
        return listOfWords;
    }

    public List<String> getListOfWordPairs() {
        return listOfWordPairs;
    }
}
