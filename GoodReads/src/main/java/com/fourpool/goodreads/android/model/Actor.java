package com.fourpool.goodreads.android.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Actor {
    @Element(name = "id") private final int id;
    @Element(name = "name") private final String name;
    @Element(name = "image_url") private final String imageUrl;

    public Actor(@Element(name = "id") int id, @Element(name = "name") String name, @Element(name = "image_url") String imageUrl) {
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
