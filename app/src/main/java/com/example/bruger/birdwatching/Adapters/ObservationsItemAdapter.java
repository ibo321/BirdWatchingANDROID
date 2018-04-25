package com.example.bruger.birdwatching.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bruger.birdwatching.JavaClass.Observations;
import com.example.bruger.birdwatching.R;

import java.util.List;

public class ObservationsItemAdapter extends ArrayAdapter<Observations> {


    private final int resource;

    public ObservationsItemAdapter(Context context, int resource, Observations[] objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    public ObservationsItemAdapter(Context context, int resource, List<Observations> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Observations observations = getItem(position);
        String nameEnglish = observations.getNameEnglish();
        String userId = observations.getUserId();
        LinearLayout observationsView;
        if (convertView == null) {
            observationsView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, observationsView, true);
        } else {
            observationsView = (LinearLayout) convertView;
        }

        //find views by id
        TextView nameEnglishView = observationsView.findViewById(R.id.observation_listview_title_for_each_item);
        TextView userIdView = observationsView.findViewById(R.id.observation_listview_title_for_each_item2);
        ImageView imageView = observationsView.findViewById(R.id.imageView);

        nameEnglishView.setText(nameEnglish);
        userIdView.setText("User ID: " + userId );
        imageView.setImageResource(R.drawable.birdowl);

        nameEnglishView.setTextColor(getContext().getColor(R.color.colorAccent));
        userIdView.setTextColor(getContext().getColor(R.color.AntiqueWhite));
        return observationsView;
    }
}
