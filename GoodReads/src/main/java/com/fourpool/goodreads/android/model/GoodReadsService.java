package com.fourpool.goodreads.android.model;

import com.fourpool.goodreads.android.shelf.ShelfResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GoodReadsService {

    @GET("/updates/friends.xml")
    void getRecentUpdates(Callback<RecentUpdatesResponse> cb);

    @GET("/shelf/list.xml")
    void getShelves(@Query("key") String key, Callback<ShelvesResponse> cb);

    @GET("/review/list?format=xml&v=2")
    void getShelf(@Query("key") String key, Callback<ShelfResponse> cb);
}
