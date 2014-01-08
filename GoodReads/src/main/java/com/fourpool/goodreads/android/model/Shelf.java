package com.fourpool.goodreads.android.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Shelf {
    @Element(name="name")
    private final String name;

    public Shelf(@Element(name="name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
