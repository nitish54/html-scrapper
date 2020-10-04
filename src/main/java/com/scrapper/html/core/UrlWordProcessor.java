package com.scrapper.html.core;

import com.scrapper.html.ds.BoundedPriorityQueue;
import com.scrapper.html.ds.Trie;
import com.scrapper.html.model.Url;
import com.scrapper.html.model.Word;
import com.scrapper.html.props.UrlProcessInitProperties;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Create an instance of {@link UrlWordProcessor } for extracting words from Url body.
 * Provide {@link UrlProcessInitProperties} properties a input to the constructor to provide the initial URL as well as the maximum depth to parse.
 * A Trie is constructed as an internal data structure to keeps the collected words having length more than the threshold provided.
 */
public class UrlWordProcessor {

    private final Trie trie;
    private final UrlProcessInitProperties props;

    /**
     * Constructs {@link UrlWordProcessor}
     * @param props
     */
    public UrlWordProcessor(UrlProcessInitProperties props) {
        this.props = props;
        this.trie = new Trie();
    }

    /**
     * Processes the URL in level order. Each URL is parsed and extracted Urls are kept in the queue for further processing in that order.
     */
    public void process() {
        Queue<Url> queue = new LinkedList<>();
        queue.add(new Url(props.getUrl(), 0));
        while (!queue.isEmpty() && queue.peek().getLevel() <= props.getDepth()) {
            Url current = queue.poll();
            UrlSourceParser urlSourceParser = new UrlSourceParser(current.getValue(), props.getWordLengthThreshold());
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

    /**
     * Returns the top 10 words from the internally maintained {@link Trie}
     * These words were kept while parsing the document of the URL.
     * @return
     */
    public List<Word> getTop10Words() {
        List<Word> words = new ArrayList<>();
        BoundedPriorityQueue<Word> bpq = trie.getTop10Words();
        bpq.forEach(word -> words.add(word));
        return words;
    }

    /**
     * Returns the top 10 pair of words from the internally maintained {@link Trie}
     * These word pairs were kept while parsing the document of the URL.
     * @return
     */
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
