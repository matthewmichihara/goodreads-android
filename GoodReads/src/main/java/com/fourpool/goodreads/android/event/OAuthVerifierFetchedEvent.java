package com.fourpool.goodreads.android.event;

public class OAuthVerifierFetchedEvent {
    private final String oauthVerifier;

    public OAuthVerifierFetchedEvent(String oauthVerifier) {
        this.oauthVerifier = oauthVerifier;
    }

    public String getOauthVerifier() {
        return oauthVerifier;
    }
}
