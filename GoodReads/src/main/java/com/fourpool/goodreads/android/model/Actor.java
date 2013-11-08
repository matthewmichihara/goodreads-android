package com.fourpool.goodreads.android.model;

public class Actor {
    private final int id;
    private final String name;
    private final String imageUrl;

    public Actor(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
