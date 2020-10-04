package com.scrapper.html.ds;

import com.scrapper.html.model.TrieNode;
import com.scrapper.html.model.Word;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of Trie Data structure for keeping the finite list of words in the form of a multi children tree.
 * It can be used to identify a word as well it's count if the word has been added multiple times.
 */
public class Trie {

    private TrieNode root = new TrieNode();

    /**
     * Inserts a word to Data Structure
     * @param word
     */
    public void insert(String word) {
        TrieNode current = root;

        for (char ch : word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(ch, c -> new TrieNode());
        }
        current.setWord(true);
        current.setContent(word);
        current.setCount(current.getCount() + 1);
    }

    /**
     * Returns true if the word is present in the {@link Trie}
     * else false
     * @param word
     * @return
     */
    public boolean find(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren().get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isWord();
    }

    /**
     * Returns top 10 Words using the {@link BoundedPriorityQueue} in the sorted order(DESC)
     * @return
     */
    public BoundedPriorityQueue<Word> getTop10Words() {
        BoundedPriorityQueue<Word> pq = new BoundedPriorityQueue<>(new Word.WordComparator(), 10);
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TrieNode current = queue.poll();
            for(TrieNode trieNode : current.getChildren().values()) {
                if(trieNode.isWord() && !isValidWordPair(trieNode.getContent())) {
                    pq.add(new Word(trieNode.getContent(), trieNode.getCount()));
                }
                queue.add(trieNode);
            }
        }
        return pq;
    }

    /**
     * Returns top 10 Word pairs using the {@link BoundedPriorityQueue} in the sorted order(DESC)
     * @return
     */
    public BoundedPriorityQueue<Word> getTop10WordPairs() {
        BoundedPriorityQueue<Word> pq = new BoundedPriorityQueue<>(new Word.WordComparator(), 10);
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TrieNode current = queue.poll();
            for(TrieNode trieNode : current.getChildren().values()) {
                String content = trieNode.getContent();
                if(trieNode.isWord() && isValidWordPair(content)) {
                    pq.add(new Word(trieNode.getContent(), trieNode.getCount()));
                }
                queue.add(trieNode);
            }
        }
        return pq;
    }

    private boolean isValidWordPair(String content) {
        if(content.contains("$")) {
            String[] words = content.split("\\$", 2);
            if(words.length > 2) return false;
            return true;
        }
        return false;
    }
}
