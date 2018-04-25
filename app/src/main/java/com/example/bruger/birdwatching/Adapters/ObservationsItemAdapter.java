package com.example.bruger.birdwatching.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Observations observations = getItem(position);
        String nameEnglish = observations.getNameEnglish();
        String placename = observations.getPlacename();
        LinearLayout observationsView;
        if (convertView == null) {
            observationsView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, observationsView, true);
        } else {
            observationsView = (LinearLayout) convertView;
        }

        TextView textView = observationsView.findViewById(R.id.specific_item_observation_list);
        TextView textView1 = observationsView.findViewById(R.id.specific_item_observation_list2);

        textView.setText("Name in english: " + nameEnglish);
        textView1.setText("Placename: " + placename);

        textView.setTextColor(getContext().getColor(R.color.purple));
        textView1.setTextColor(getContext().getColor(R.color.red));
        return observationsView;
    }
}
