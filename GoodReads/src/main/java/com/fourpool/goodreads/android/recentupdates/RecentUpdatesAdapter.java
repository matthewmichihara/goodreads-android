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
    public RecentUpdatesAdapter(Context context, List<Update> updates) {
        super(context, R.layout.list_item_update, updates);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_update, parent, false);
            holder = new ViewHolder(convertView);
            holder.actorImage = (ImageView) convertView.findViewById(R.id.actor_image);
            convertView.setTag(holder);
        }

        Update update = getItem(position);
        Actor actor = update.getActor();
        Picasso.with(getContext()).load(actor.getImageUrl()).into(holder.actorImage);
        holder.actorName.setText(actor.getName());

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.actor_image) ImageView actorImage;
        @InjectView(R.id.actor_name) TextView actorName;

        public ViewHolder(View v) {
            Views.inject(this, v);
        }
    }
}
