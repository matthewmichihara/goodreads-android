package com.fourpool.goodreads.android;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;
import timber.log.Timber;

public class GoodReadsApplication extends Application {
    private ObjectGraph objectGraph;

    @Override public void onCreate() {
        super.onCreate();

        configureLogging();

        objectGraph = ObjectGraph.create(getModules().toArray());
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    private List<Object> getModules() {
        List<Object> modules = new ArrayList<Object>();
        modules.add(new GoodReadsModule(this));
        return modules;
    }

    private static void configureLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
