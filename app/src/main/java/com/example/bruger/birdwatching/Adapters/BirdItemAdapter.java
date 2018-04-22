package com.example.bruger.birdwatching.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bruger.birdwatching.JavaClass.Bird;
import com.example.bruger.birdwatching.R;

import java.util.List;

public class BirdItemAdapter extends ArrayAdapter<Bird> {
    private final int resource;

    public BirdItemAdapter(Context context, int resource, Bird[] objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    public BirdItemAdapter(Context context, int resource, List<Bird> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Bird bird = getItem(position);
        String nameEnglish = bird.getNameEnglish();
        LinearLayout birdView;
        if (convertView == null) {
            birdView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, birdView, true);
        } else {
            birdView = (LinearLayout) convertView;
        }
        TextView userIdView = birdView.findViewById(R.id.bird_item_title);
        userIdView.setText(nameEnglish);
        return birdView;
    }
}
