package com.fourpool.goodreads.android.model;

import oauth.signpost.OAuthConsumer;

public class Session {
    public final OAuthConsumer consumer;

    public Session(OAuthConsumer consumer) {
        this.consumer = consumer;
    }

    public OAuthConsumer getConsumer() {
        return consumer;
    }
}
