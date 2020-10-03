package com.scrapper.html.model;

public class Url {
    
    private String value;
    private int level;

    public Url(String value, int level) {
        this.value = value;
        this.level = level;
    }

    public String getValue() {
        return value;
    }

    public int getLevel() {
        return level;
    }

}
