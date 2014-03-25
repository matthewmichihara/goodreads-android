package com.fourpool.goodreads.android.shelf;

import com.fourpool.goodreads.android.SecretConstants;
import com.fourpool.goodreads.android.model.GoodReadsService;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class ShelfController {
    private final GoodReadsService goodReadsService;
    private ShelfDisplay shelfDisplay;

    @Inject ShelfController(GoodReadsService goodReadsService) {
        this.goodReadsService = goodReadsService;
    }

    public void onCreateView(final ShelfDisplay shelvesDisplay) {
        this.shelfDisplay = shelvesDisplay;

        goodReadsService.getShelf(SecretConstants.CONSUMER_KEY, new Callback<ShelfResponse>() {
            @Override public void success(ShelfResponse shelvesResponse, Response response) {
                if (shelvesDisplay == null) {
                    return;
                }

                //shelvesDisplay.display(shelvesResponse.getShelves());
            }

            @Override public void failure(RetrofitError retrofitError) {
                Timber.e(retrofitError, "Failed getting shelf");
            }
        });
    }
}

