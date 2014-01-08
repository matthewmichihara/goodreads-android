package com.fourpool.goodreads.android.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class RecentUpdatesResponse {
    @Element
    @Path("Request[1]")
    private final String authentication;

    @ElementList
    private final List<Update> updates;


    public RecentUpdatesResponse(@Element(name = "authentication") String authentication, @ElementList(name = "updates") List<Update> updates) {
        this.authentication = authentication;
        this.updates = updates;
    }

    public String getAuthentication() {
        return authentication;
    }

    public List<Update> getUpdates() {
        return updates;
    }
}
