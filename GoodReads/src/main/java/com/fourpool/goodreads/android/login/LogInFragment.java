package com.fourpool.goodreads.android.login;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fourpool.goodreads.android.GoodReadsApplication;
import com.fourpool.goodreads.android.R;

import javax.inject.Inject;

import butterknife.OnClick;
import butterknife.Views;

public class LogInFragment extends Fragment implements LogInDisplay {
    @Inject LogInController controller;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((GoodReadsApplication) activity.getApplication()).inject(this);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller.onCreate();
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        Views.inject(this, view);

        return view;
    }

    @Override public void onStart() {
        super.onStart();
        controller.onStart(this);
    }

    @Override public void onStop() {
        super.onStop();
        controller.onStop();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        controller.onDestroy();
    }

    @Override public void inProgress() {
        // Show a spinner or something.
    }

    @OnClick(R.id.log_in) void onLogInButtonClicked(Button button) {
        controller.onLogInClicked();
    }
}