package com.scrapper.html.model;

import java.util.Comparator;

public class Word {
    private String value;
    private int count;

    public Word(String value, int count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Word{" +
                "value='" + value + '\'' +
                ", count=" + count +
                '}';
    }

    public static class WordComparator implements Comparator<Word> {
        @Override
        public int compare(Word o1, Word o2) {
            return o2.count - o1.count;
        }
    }
}
