package com.kanishk.code.shutterfly.model;

/**
 * Created by kanishk on 7/3/17.
 */

public class Category {
    private String name;
    private String image;

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
