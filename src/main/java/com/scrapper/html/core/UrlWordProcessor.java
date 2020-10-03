package com.scrapper.html.core;

import com.scrapper.html.ds.BoundedPriorityQueue;
import com.scrapper.html.ds.Trie;
import com.scrapper.html.model.Url;
import com.scrapper.html.model.Word;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class UrlWordProcessor {

    private static final int WORD_LENGTH_THRESHOLD = 0;
    private final String initialUrl;
    private final int maxLevel;
    private final Trie trie;

    public UrlWordProcessor(String initialUrl, int maxLevel) {
        this.initialUrl = initialUrl;
        this.maxLevel = maxLevel;
        this.trie = new Trie();
    }

    public void process() {
        Queue<Url> queue = new LinkedList<>();
        queue.add(new Url(initialUrl, 0));
        while (!queue.isEmpty() && queue.peek().getLevel() <= maxLevel) {
            Url current = queue.poll();
            UrlSourceParser urlSourceParser = new UrlSourceParser(current.getValue(), WORD_LENGTH_THRESHOLD);
            if (urlSourceParser.isParsable()) {
                urlSourceParser.processWords();
                processWords(urlSourceParser.getListOfWords());
                processWords(urlSourceParser.getListOfWordPairs());
                List<String> urlList = urlSourceParser.getDocumentUrls();
                if (urlList != null && urlList.size() > 0) {
                    urlList.forEach(value -> {
                        queue.add(new Url(value, current.getLevel() + 1));
                    });
                }
            }
        }
    }

    private void processWords(List<String> words) {
        words.forEach(word -> trie.insert(word));
    }

    public List<Word> getTop10Words() {
        List<Word> words = new ArrayList<>();
        BoundedPriorityQueue<Word> bpq = trie.getTop10Words();
        bpq.forEach(word -> words.add(word));
        return words;
    }

    public List<Word> getTop10WordPairs() {
        List<Word> words = new ArrayList<>();
        BoundedPriorityQueue<Word> bpq = trie.getTop10WordPairs();
        bpq.forEach(word -> {
            String wordPair = word.getValue().replace('$', ' ');
            words.add(new Word(wordPair, word.getCount()));
        });
        return words;
    }
}
