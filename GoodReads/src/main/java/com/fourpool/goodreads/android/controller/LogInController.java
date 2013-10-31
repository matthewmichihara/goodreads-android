package com.fourpool.goodreads.android.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.fourpool.goodreads.android.event.OAuthVerifierFetchedEvent;
import com.fourpool.goodreads.android.event.SessionCreatedEvent;
import com.fourpool.goodreads.android.model.Session;
import com.fourpool.goodreads.android.model.SessionStore;
import com.fourpool.goodreads.android.ui.LogInFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;
import javax.inject.Named;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.concurrency.AndroidSchedulers;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

import static com.fourpool.goodreads.android.Constants.ACCESS_URL;
import static com.fourpool.goodreads.android.Constants.AUTHORIZE_URL;
import static com.fourpool.goodreads.android.Constants.CALLBACK_URL;
import static com.fourpool.goodreads.android.SecretConstants.CONSUMER_KEY;
import static com.fourpool.goodreads.android.SecretConstants.CONSUMER_SECRET;
import static com.fourpool.goodreads.android.Constants.REQUEST_URL;

public class LogInController {
    private final OAuthProvider provider = new DefaultOAuthProvider(REQUEST_URL, ACCESS_URL, AUTHORIZE_URL);
    private final OAuthConsumer consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

    private final Context context;
    private final SessionStore sessionStore;
    private final Bus bus;

    private LogInFragment logInFragment;

    @Inject public LogInController(@Named("Application") Context context, SessionStore sessionStore, Bus bus) {
        this.context = context;
        this.sessionStore = sessionStore;
        this.bus = bus;
    }

    public void onCreate(LogInFragment logInFragment) {
        bus.register(this);
    }

    public void onStart(LogInFragment logInFragment) {
        this.logInFragment = logInFragment;
    }

    public void onStop(LogInFragment logInFragment) {
        this.logInFragment = null;
    }

    public void onDestroy(LogInFragment logInFragment) {
        bus.unregister(this);
    }

    public void onLogInClicked() {
        Timber.d("onLoginClicked called");

        Observable requestTokenUrlObservable = Observable.create(new Observable.OnSubscribeFunc<String>() {
            @Override public Subscription onSubscribe(Observer<? super String> observer) {
                try {
                    String url = provider.retrieveRequestToken(consumer, CALLBACK_URL);

                    observer.onNext(url);
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        });

        Observer<String> requestTokenUrlObserver = new Observer<String>() {
            @Override public void onNext(String url) {
                Timber.d("onNext called with %s", url);

                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                logInFragment.getActivity().startActivity(myIntent);
            }

            @Override public void onCompleted() {
                Timber.d("onCompleted called");
            }

            @Override public void onError(Throwable throwable) {
                Timber.d("onError called", throwable);
            }
        };

        requestTokenUrlObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(requestTokenUrlObserver);
    }

    @Subscribe public void onOAuthVerifierFetched(OAuthVerifierFetchedEvent event) {
        Timber.d("onOAuthVerifierFetched called.");
        final String oauthVerifier = event.getOauthVerifier();

        Observable sessionObservable = Observable.create(new Observable.OnSubscribeFunc<Session>() {
            @Override public Subscription onSubscribe(Observer<? super Session> observer) {
                try {
                    Timber.d("Retrieving access token for OAuth Verifier: %s", oauthVerifier);
                    provider.retrieveAccessToken(consumer, oauthVerifier);

                    String token = consumer.getToken();
                    String tokenSecret = consumer.getTokenSecret();

                    Timber.d("OAuth token: %s, OAuth token secret: %s", token, tokenSecret);

                    consumer.setTokenWithSecret(token, tokenSecret);
                    Session session = new Session(consumer);
                    sessionStore.setSession(session);
                    Timber.d("Session saved.");

                    observer.onNext(session);
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        });

        Observer<Session> sessionObserver = new Observer<Session>() {
            @Override public void onNext(Session session) {
                Timber.d("onNext called with %s", session);
                bus.post(new SessionCreatedEvent());
            }

            @Override public void onCompleted() {
                Timber.d("onCompleted called");
            }

            @Override public void onError(Throwable throwable) {
                Timber.d("onError called", throwable);
            }
        };

        sessionObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(sessionObserver);
    }
}
