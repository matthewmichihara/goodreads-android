package com.fourpool.goodreads.android.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Update {
    @Element(name = "image_url")
    private final String imageUrl;

    @Element(name = "actor")
    private final Actor actor;

    public Update(@Element(name = "image_url") String imageUrl, @Element(name = "actor") Actor actor) {
        this.imageUrl = imageUrl;
        this.actor = actor;
    }

    public String getType() {
        return "";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Actor getActor() {
        return actor;
    }
}