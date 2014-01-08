package com.fourpool.goodreads.android.model;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GoodReadsService {
    @GET("/updates/friends.xml") void getRecentUpdates(Callback<RecentUpdatesResponse> cb);

    @GET("/shelf/list.xml") void getShelves(@Query("key") String key, Callback<ShelvesResponse> cb);
}
