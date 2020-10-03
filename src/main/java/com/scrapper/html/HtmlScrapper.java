package com.scrapper.html;

import com.scrapper.html.core.UrlWordProcessor;

public class HtmlScrapper {

    public static void main(String[] args) {
        processUrl();
    }

    public static void processUrl() {
        UrlWordProcessor urlWordProcessor = new UrlWordProcessor("https://orrsella.gitbooks.io/soft-eng-interview-prep/content/topics/complexity.html", 1);
        urlWordProcessor.process();
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Top 10 words with count: " + urlWordProcessor.getTop10Words());
        System.out.println("Top 10 word pairs with count: " + urlWordProcessor.getTop10WordPairs());
        System.out.println("-----------------------------------------------------------------");
    }
}
