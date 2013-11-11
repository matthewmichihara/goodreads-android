package com.fourpool.goodreads.android.recentupdates;

import com.fourpool.goodreads.android.model.SessionStore;
import com.fourpool.goodreads.android.model.Update;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import oauth.signpost.OAuthConsumer;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.concurrency.AndroidSchedulers;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public class RecentUpdatesController {
    private final SessionStore sessionStore;

    @Inject RecentUpdatesController(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    public void onCreateView(final RecentUpdatesFragment recentUpdatesFragment) {
        Observable recentUpdatesObservable = Observable.create(new Observable.OnSubscribeFunc() {
            @Override public Subscription onSubscribe(Observer observer) {
                try {
                    URL url = new URL("https://www.goodreads.com/updates/friends.xml");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    OAuthConsumer consumer = sessionStore.getSession().getConsumer();
                    consumer.sign(connection);
                    connection.connect();

                    List<Update> updates = new RecentUpdatesParser().parse(connection.getInputStream());

                    observer.onNext(updates);
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        });

        Observer<List<Update>> recentUpdatesObserver = new Observer<List<Update>>() {
            @Override public void onNext(List<Update> updates) {
                Timber.d("onNext called");
                recentUpdatesFragment.displayUpdates(updates);
            }

            @Override public void onCompleted() {
                Timber.d("onComplete called");
            }

            @Override public void onError(Throwable throwable) {
                Timber.e(throwable, "onError called");
            }
        };

        recentUpdatesObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(recentUpdatesObserver);
    }
}
