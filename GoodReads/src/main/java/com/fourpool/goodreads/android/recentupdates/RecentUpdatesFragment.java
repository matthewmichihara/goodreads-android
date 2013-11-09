package com.fourpool.goodreads.android.recentupdates;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fourpool.goodreads.android.GoodReadsApplication;
import com.fourpool.goodreads.android.R;
import com.fourpool.goodreads.android.model.Update;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

public class RecentUpdatesFragment extends Fragment implements RecentUpdatesDisplay {
    @Inject RecentUpdatesController recentUpdatesController;

    @InjectView(R.id.recent_update_list) ListView recentUpdateList;

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

    @Override public void displayUpdates(List<Update> updates) {
        recentUpdateList.setAdapter(new RecentUpdatesAdapter(getActivity(), updates));
    }
}
