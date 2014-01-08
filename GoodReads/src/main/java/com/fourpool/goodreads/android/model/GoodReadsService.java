package com.fourpool.goodreads.android.model;

import retrofit.http.GET;

public interface GoodReadsService {
    @GET("/updates/friends.xml") RecentUpdatesResponse getRecentUpdates();
}
