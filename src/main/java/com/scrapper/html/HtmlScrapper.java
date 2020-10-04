package com.scrapper.html;

import com.scrapper.html.core.UrlWordProcessor;
import com.scrapper.html.props.UrlProcessInitProperties;

import java.util.Scanner;

/**
 * Starter class to initialize the HTML Scrapper
 * Can be used to achieve multiple use-cases. In this case it is specifically being used to process an URL and extracts Words from the Document.
 */
public class HtmlScrapper {

    public static void main(String[] args) {
        startProcessing();
    }

    /**
     * Driver method to execute the whole process
     * <ui>
     * <li>Takes input</li>
     * <li>Initializes startup properties</li>
     * <li>Executes Processor</li>
     * <li>Returns Top 10 words and word pairs</li>
     * <ui/>
     */
    public static void startProcessing() {
        UrlProcessInitProperties properties = initProperties();
        UrlWordProcessor urlWordProcessor = new UrlWordProcessor(properties);
        urlWordProcessor.process();
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Top 10 words with count: " + urlWordProcessor.getTop10Words());
        System.out.println("Top 10 word pairs with count: " + urlWordProcessor.getTop10WordPairs());
        System.out.println("-----------------------------------------------------------------");
    }

    /**
     * Initializes initial values for the necessary properties like source url, maximum depth to consider and Word length threshold to mark a word valid.
     * @return
     */
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
