package com.fourpool.goodreads.android.model;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import static com.fourpool.goodreads.android.SecretConstants.CONSUMER_KEY;
import static com.fourpool.goodreads.android.SecretConstants.CONSUMER_SECRET;

@Singleton
public class SessionStore {
    private static final String SESSION_OAUTH_TOKEN = "session_oauth_token";
    private static final String SESSION_OAUTH_TOKEN_SECRET = "session_oauth_token_secret";
    private final SharedPreferences sharedPreferences;

    @Inject public SessionStore(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public Session getSession() {
        String oauthToken = sharedPreferences.getString(SESSION_OAUTH_TOKEN, null);
        String oauthTokenSecret = sharedPreferences.getString(SESSION_OAUTH_TOKEN_SECRET, null);

        if (oauthToken == null || oauthTokenSecret == null) return null;

        OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(oauthToken, oauthTokenSecret);

        return new Session(consumer);
    }

    public void setSession(Session session) {
        OAuthConsumer consumer = session.getConsumer();

        sharedPreferences.edit().putString(SESSION_OAUTH_TOKEN, consumer.getToken()).commit();
        sharedPreferences.edit().putString(SESSION_OAUTH_TOKEN_SECRET, consumer.getTokenSecret()).commit();
    }
}
