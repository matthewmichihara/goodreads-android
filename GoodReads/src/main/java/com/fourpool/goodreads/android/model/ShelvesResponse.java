package com.fourpool.goodreads.android.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class ShelvesResponse {
    @Element
    @Path("Request[1]")
    private final String authentication;

    @ElementList
    private final List<Shelf> shelves;

    public ShelvesResponse(@Element(name = "authentication") String authentication, @ElementList(name="shelves") List<Shelf> shelves) {
        this.authentication = authentication;
        this.shelves = shelves;
    }

    public String getAuthentication() {
        return authentication;
    }

    public List<Shelf> getShelves() {
        return shelves;
    }
}
