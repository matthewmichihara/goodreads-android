package com.fourpool.goodreads.android.model;

public class Update {
    private final String type;
    private final String imageUrl;
    private final Actor actor;

    public Update(String type, String imageUrl, Actor actor) {
        this.type = type;
        this.imageUrl = imageUrl;
        this.actor = actor;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Actor getActor() {
        return actor;
    }
}
