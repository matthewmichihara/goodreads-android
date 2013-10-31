package com.fourpool.goodreads.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.fourpool.goodreads.android.GoodReadsApplication;
import com.fourpool.goodreads.android.model.SessionStore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import oauth.signpost.OAuthConsumer;

public class RecentUpdatesFragment extends Fragment {
    @Inject SessionStore sessionStore;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((GoodReadsApplication) activity.getApplication()).inject(this);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        new Thread() {

            @Override public void run() {
                try {

                    URL url = new URL("https://www.goodreads.com/api/auth_user");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    OAuthConsumer consumer = sessionStore.getSession().getConsumer();

                    consumer.sign(connection);
                    connection.connect();
                    //read the result from the server
                    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line + '\n');
                    }

                    Log.i("asdf", sb.toString());
                } catch (Exception e) {
                    Log.e("asdf", "error", e);
                }
            }
        }.start();
    }
}
