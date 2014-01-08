package com.fourpool.goodreads.android.recentupdates;

import android.os.Handler;
import android.os.Looper;

import com.fourpool.goodreads.android.model.RecentUpdatesResponse;
import com.fourpool.goodreads.android.model.SessionStore;
import com.fourpool.goodreads.android.model.Update;
import com.fourpool.goodreads.android.model.GoodReadsService;
import com.fourpool.goodreads.android.retrofit.RetrofitHttpOAuthConsumer;
import com.fourpool.goodreads.android.retrofit.SigningOkClient;

import org.simpleframework.xml.core.Persister;

import javax.inject.Inject;

import oauth.signpost.OAuthConsumer;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;
import timber.log.Timber;

public class RecentUpdatesController {
    private final SessionStore sessionStore;

    @Inject RecentUpdatesController(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    public void onCreateView(final RecentUpdatesFragment recentUpdatesFragment) {
        new Thread() {
            @Override public void run() {
                OAuthConsumer consumer = sessionStore.getSession().getConsumer();

                RetrofitHttpOAuthConsumer c = new RetrofitHttpOAuthConsumer(consumer.getConsumerKey(), consumer.getConsumerSecret());
                c.setTokenWithSecret(consumer.getToken(), consumer.getTokenSecret());
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setServer("https://www.goodreads.com")
                        .setConverter(new SimpleXMLConverter(new Persister()))
                        .setClient(new SigningOkClient(c))
                        .build();

                GoodReadsService service = restAdapter.create(GoodReadsService.class);

                final RecentUpdatesResponse response = service.getRecentUpdates();
                Timber.d(response.getAuthentication());

                for (Update update : response.getUpdates()) {
                    Timber.d(update.getImageUrl());
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override public void run() {
                        recentUpdatesFragment.displayUpdates(response.getUpdates());
                    }
                });
            }
        }.start();
    }
}
