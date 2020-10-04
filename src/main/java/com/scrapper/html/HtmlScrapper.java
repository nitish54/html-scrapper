package com.scrapper.html;

import com.scrapper.html.core.UrlWordProcessor;
import com.scrapper.html.props.UrlProcessInitProperties;

import java.util.Scanner;

public class HtmlScrapper {

    public static void main(String[] args) {
        processUrl();
    }

    public static void processUrl() {
        UrlProcessInitProperties properties = initProperties();
        UrlWordProcessor urlWordProcessor = new UrlWordProcessor(properties);
        urlWordProcessor.process();
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Top 10 words with count: " + urlWordProcessor.getTop10Words());
        System.out.println("Top 10 word pairs with count: " + urlWordProcessor.getTop10WordPairs());
        System.out.println("-----------------------------------------------------------------");
    }

    private static UrlProcessInitProperties initProperties() {
        UrlProcessInitProperties properties = UrlProcessInitProperties.INSTANCE;
        Scanner sc = new Scanner(System.in);
        print("Enter the url to parse: ");
        properties.setUrl(sc.next());
        print("Enter the max depth to visit (-1 for skipping, default is 1): ");
        properties.setDepth(sc.nextInt());
        print("Enter the minimum valid word length (-1 for skipping, default is 2): ");
        properties.setWordLengthThreshold(sc.nextInt());
        return properties;
    }

    private static void print(String value) {
        System.out.print(value);
    }
}
