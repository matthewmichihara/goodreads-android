package com.fourpool.goodreads.android.shelves;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fourpool.goodreads.android.R;
import com.fourpool.goodreads.android.model.Shelf;

import java.util.List;

public class ShelvesAdapter extends ArrayAdapter<Shelf> {
    public ShelvesAdapter(Context context, List<Shelf> shelves) {
        super(context, R.layout.list_item_shelf, shelves);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        Shelf shelf = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_shelf, parent, false);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);
        nameTextView.setText(shelf.getName());

        return convertView;
    }
}
