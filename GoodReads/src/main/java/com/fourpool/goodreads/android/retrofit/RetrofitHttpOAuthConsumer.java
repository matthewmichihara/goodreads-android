package com.fourpool.goodreads.android.retrofit;

import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.http.HttpRequest;
import retrofit.client.Request;

public class RetrofitHttpOAuthConsumer extends AbstractOAuthConsumer {
    public RetrofitHttpOAuthConsumer(String consumerKey, String consumerSecret) {
        super(consumerKey, consumerSecret);
    }

    @Override protected HttpRequest wrap(Object request) {
        if (!(request instanceof Request)) {
            throw new IllegalArgumentException("This consumer expects requests of type " + Request.class.getCanonicalName());
        }
        return new RetrofitHttpRequest((Request) request);
    }
}
