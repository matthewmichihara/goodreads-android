package com.fourpool.goodreads.android.shelves;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fourpool.goodreads.android.GoodReadsApplication;
import com.fourpool.goodreads.android.R;
import com.fourpool.goodreads.android.model.Shelf;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

public class ShelvesFragment extends Fragment implements ShelvesDisplay {
    @Inject ShelvesController shelvesController;

    @InjectView(R.id.shelves) ListView shelvesList;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((GoodReadsApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shelves, container, false);
        Views.inject(this, view);

        shelvesController.onCreateView(this);

        return view;
    }

    @Override public void display(List<Shelf> shelves) {
        shelvesList.setAdapter(new ShelvesAdapter(getActivity(), shelves));
    }
}
