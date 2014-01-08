package com.fourpool.goodreads.android.recentupdates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourpool.goodreads.android.R;
import com.fourpool.goodreads.android.model.Actor;
import com.fourpool.goodreads.android.model.Update;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

public class RecentUpdatesAdapter extends ArrayAdapter<Update> {
    private static final int TYPE_READ_STATUS = 0;
    private static final int TYPE_REVIEW = 1;

    public RecentUpdatesAdapter(Context context, List<Update> updates) {
        super(context, R.layout.list_item_update_read_status, updates);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        Update update = getItem(position);

        Actor actor = update.getActor();
        String titleText = "";

        if (update.getType().equals(Update.TYPE_READ_STATUS)) {
            titleText = actor.getName() + " wants to read something";
        } else if (update.getType().equals(Update.TYPE_REVIEW)) {
            titleText = actor.getName() + " rated something blah";
        } else if (update.getType().equals(Update.TYPE_FRIEND)) {
            titleText = actor.getName() + " is friends with pete";
        } else if (update.getType().equals(Update.TYPE_RECOMMENDATION)) {
            titleText = actor.getName() + " recommended foo to bar";
        } else if (update.getType().equals(Update.TYPE_GIVEAWAY_REQUEST)) {
            titleText = actor.getName() + " has entered a giveaway";
        }

        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            switch (getItemViewType(position)) {
                case TYPE_READ_STATUS:
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_update_read_status, parent, false);
                    break;
                case TYPE_REVIEW:
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_update_read_status, parent, false);
                    break;
            }

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Picasso.with(getContext()).load(actor.getImageUrl()).into(holder.actorImage);
        holder.actorName.setText(titleText);

        return convertView;
    }

    @Override public int getItemViewType(int position) {
        Update update = getItem(position);

        if (update.getType().equals(Update.TYPE_READ_STATUS)) {
            return TYPE_READ_STATUS;
        } else if (update.getType().equals(Update.TYPE_REVIEW)) {
            return TYPE_REVIEW;
        } else {
            return TYPE_REVIEW;
            //throw new RuntimeException("Unknown update type: %s", update.getType());
        }
    }

    @Override public int getViewTypeCount() {
        return 2;
    }

    static class ViewHolder {
        @InjectView(R.id.actor_image) ImageView actorImage;
        @InjectView(R.id.actor_name) TextView actorName;
        @InjectView(R.id.book_image) ImageView bookImage;

        public ViewHolder(View v) {
            Views.inject(this, v);
        }
    }
}
