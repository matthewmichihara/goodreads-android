package com.fourpool.goodreads.android.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Update {
    public static final String TYPE_READ_STATUS = "readstatus";
    public static final String TYPE_REVIEW = "review";
    public static final String TYPE_FRIEND = "friend";
    public static final String TYPE_RECOMMENDATION = "recommendation";
    public static final String TYPE_GIVEAWAY_REQUEST = "giveawayrequest";

    @Attribute(name = "type")
    private final String type;

    @Element(name = "image_url")
    private final String imageUrl;

    @Element(name = "actor")
    private final Actor actor;

    public Update(@Attribute(name = "type") String type, @Element(name = "image_url") String imageUrl, @Element(name = "actor") Actor actor) {
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