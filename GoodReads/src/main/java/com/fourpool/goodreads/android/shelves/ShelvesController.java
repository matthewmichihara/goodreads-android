package com.fourpool.goodreads.android.shelves;

import com.fourpool.goodreads.android.SecretConstants;
import com.fourpool.goodreads.android.model.GoodReadsService;
import com.fourpool.goodreads.android.model.ShelvesResponse;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class ShelvesController {
    private final GoodReadsService goodReadsService;
    private ShelvesDisplay shelvesDisplay;

    @Inject ShelvesController(GoodReadsService goodReadsService) {
        this.goodReadsService = goodReadsService;
    }

    public void onCreateView(final ShelvesDisplay shelvesDisplay) {
        this.shelvesDisplay = shelvesDisplay;

        goodReadsService.getShelves(SecretConstants.CONSUMER_KEY, new Callback<ShelvesResponse>() {
            @Override public void success(ShelvesResponse shelvesResponse, Response response) {
                if (shelvesDisplay == null) {
                    return;
                }

                shelvesDisplay.display(shelvesResponse.getShelves());
            }

            @Override public void failure(RetrofitError retrofitError) {
                Timber.e(retrofitError, "Failed getting shelves");
            }
        });
    }
}
