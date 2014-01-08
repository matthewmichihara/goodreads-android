package com.fourpool.goodreads.android.recentupdates;

import android.os.Handler;
import android.os.Looper;

import com.fourpool.goodreads.android.model.GoodReadsService;
import com.fourpool.goodreads.android.model.RecentUpdatesResponse;
import com.fourpool.goodreads.android.model.Update;

import javax.inject.Inject;

import timber.log.Timber;

public class RecentUpdatesController {
    private final GoodReadsService goodReadsService;

    @Inject RecentUpdatesController(GoodReadsService goodReadsService) {
        this.goodReadsService = goodReadsService;
    }

    public void onCreateView(final RecentUpdatesDisplay recentUpdatesDisplay) {
        new Thread() {
            @Override public void run() {
                final RecentUpdatesResponse response = goodReadsService.getRecentUpdates();
                Timber.d(response.getAuthentication());

                for (Update update : response.getUpdates()) {
                    Timber.d(update.getImageUrl());
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override public void run() {
                        recentUpdatesDisplay.displayUpdates(response.getUpdates());
                    }
                });
            }
        }.start();
    }
}
