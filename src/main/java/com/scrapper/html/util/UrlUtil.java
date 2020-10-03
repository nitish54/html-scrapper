package com.scrapper.html.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {

    public static String getResolvedUrl(String baseUrlString, String urlString) {
        try{
            URL relativeURL, baseURL;
            baseURL = new URL(baseUrlString);
            relativeURL = new URL( baseURL, urlString);
            return relativeURL.toExternalForm();
        } catch (MalformedURLException mex) {
            mex.printStackTrace();
        }
        return null;
    }
}
