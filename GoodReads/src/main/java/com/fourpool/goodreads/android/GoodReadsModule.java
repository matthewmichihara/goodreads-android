package com.fourpool.goodreads.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fourpool.goodreads.android.login.LogInFragment;
import com.fourpool.goodreads.android.recentupdates.RecentUpdatesFragment;
import com.squareup.otto.Bus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {MainActivity.class, LogInFragment.class, RecentUpdatesFragment.class})
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
}
