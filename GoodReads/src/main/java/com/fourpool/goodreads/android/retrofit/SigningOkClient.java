package com.fourpool.goodreads.android.retrofit;

import java.io.IOException;

import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

public class SigningOkClient extends OkClient {
    private final RetrofitHttpOAuthConsumer oauthConsumer;

    public SigningOkClient(RetrofitHttpOAuthConsumer oauthConsumer) {
        this.oauthConsumer = oauthConsumer;
    }

    @Override public Response execute(Request request) throws IOException {
        try {
            RetrofitHttpRequest signedRetrofitHttpRequest = (RetrofitHttpRequest) oauthConsumer.sign(request);
            request = (Request) signedRetrofitHttpRequest.unwrap();
        } catch (Exception e) {
            throw new IOException(e);
        }

        return super.execute(request);
    }
}