package com.fourpool.goodreads.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fourpool.goodreads.android.login.LogInFragment;
import com.fourpool.goodreads.android.model.GoodReadsService;
import com.fourpool.goodreads.android.model.SessionStore;
import com.fourpool.goodreads.android.recentupdates.RecentUpdatesFragment;
import com.fourpool.goodreads.android.retrofit.RetrofitHttpOAuthConsumer;
import com.fourpool.goodreads.android.retrofit.SigningOkClient;
import com.fourpool.goodreads.android.shelves.ShelvesFragment;
import com.squareup.otto.Bus;

import org.simpleframework.xml.core.Persister;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oauth.signpost.OAuthConsumer;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

@Module(injects = {MainActivity.class, LogInFragment.class, RecentUpdatesFragment.class, ShelvesFragment.class})
public class GoodReadsModule {
    private final GoodReadsApplication application;

    public GoodReadsModule(GoodReadsApplication application) {
        this.application = application;
    }

    @Provides @Named("Application") Context provideContext() {
        return application;
    }

    @Provides @Singleton SharedPreferences provideSharedPreferences(@Named("Application") Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides @Singleton Bus provideBus() {
        return new Bus();
    }

    @Provides @Singleton RestAdapter provideRestAdapter(SessionStore sessionStore) {
        OAuthConsumer consumer = sessionStore.getSession().getConsumer();

        RetrofitHttpOAuthConsumer c = new RetrofitHttpOAuthConsumer(consumer.getConsumerKey(), consumer.getConsumerSecret());
        c.setTokenWithSecret(consumer.getToken(), consumer.getTokenSecret());
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("https://www.goodreads.com")
                .setConverter(new SimpleXMLConverter(new Persister()))
                .setClient(new SigningOkClient(c))
                .build();

        return restAdapter;
    }

    @Provides @Singleton GoodReadsService provideGoodReadsService(RestAdapter restAdapter) {
        GoodReadsService service = restAdapter.create(GoodReadsService.class);
        return service;
    }
}
