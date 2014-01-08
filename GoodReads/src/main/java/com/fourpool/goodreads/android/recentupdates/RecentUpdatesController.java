package com.fourpool.goodreads.android.recentupdates;

import com.fourpool.goodreads.android.model.GoodReadsService;
import com.fourpool.goodreads.android.model.RecentUpdatesResponse;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class RecentUpdatesController {
    private final GoodReadsService goodReadsService;

    @Inject RecentUpdatesController(GoodReadsService goodReadsService) {
        this.goodReadsService = goodReadsService;
    }

    public void onCreateView(final RecentUpdatesDisplay recentUpdatesDisplay) {
        goodReadsService.getRecentUpdates(new Callback<RecentUpdatesResponse>() {
            @Override public void success(RecentUpdatesResponse recentUpdatesResponse, Response response) {
                if (recentUpdatesDisplay == null) {
                    return;
                }
                recentUpdatesDisplay.displayUpdates(recentUpdatesResponse.getUpdates());
            }

            @Override public void failure(RetrofitError retrofitError) {
                Timber.e(retrofitError, "Failed getting updates");
            }
        });
    }
}
