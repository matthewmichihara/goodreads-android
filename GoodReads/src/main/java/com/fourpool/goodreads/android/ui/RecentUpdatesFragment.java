package com.fourpool.goodreads.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourpool.goodreads.android.GoodReadsApplication;
import com.fourpool.goodreads.android.R;
import com.fourpool.goodreads.android.controller.RecentUpdatesController;
import com.fourpool.goodreads.android.model.SessionStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

public class RecentUpdatesFragment extends Fragment {
    @Inject SessionStore sessionStore;
    @Inject RecentUpdatesController recentUpdatesController;

    @InjectView(R.id.example) TextView exampleText;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((GoodReadsApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_updates, container, false);
        Views.inject(this, view);

        recentUpdatesController.onCreateView(this);

        return view;
    }

    public void displayUpdates(List<String> updates) {
        String s = "";
        for (String update : updates) {
            s += update + " ";
        }

        exampleText.setText(s);
    }

    private void saveStringToFile(String string) {
        File file = new File(Environment.getExternalStorageDirectory(), "example.xml");
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(string.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
