package com.scrapper.html.props;

public enum UrlProcessInitProperties {
    INSTANCE;

    private static final int URL_MAX_DEPTH = 1;
    private static final int VALID_WORD_LENGTH_THRESHOLD = 2;

    private String url;
    private Integer depth;
    private Integer wordLengthThreshold;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDepth() {
        return depth != null ? depth : URL_MAX_DEPTH;
    }

    public void setDepth(Integer depth) {
        if(depth < 0) return;
        this.depth = depth;
    }

    public int getWordLengthThreshold() {
        return wordLengthThreshold != null ? wordLengthThreshold : VALID_WORD_LENGTH_THRESHOLD;
    }

    public void setWordLengthThreshold(Integer wordLengthThreshold) {
        if(wordLengthThreshold < 0) return;
        this.wordLengthThreshold = wordLengthThreshold;
    }
}
