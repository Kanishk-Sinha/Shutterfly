package com.kanishk.code.shutterfly.model;

/**
 * Created by kanishk on 7/3/17.
 */

public class Image {
    private String url;
    private String description;

    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
