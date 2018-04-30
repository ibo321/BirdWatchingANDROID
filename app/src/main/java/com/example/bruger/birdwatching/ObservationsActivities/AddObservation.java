package com.example.bruger.birdwatching.ObservationsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.bruger.birdwatching.R;

public class AddObservation extends AppCompatActivity {

    EditText userId, englishName, danishName, population, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        Toolbar toolbar = findViewById(R.id.app_bar);
//        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewsById();
    }

    public void findViewsById(){
        userId = findViewById(R.id.addUserId);
        englishName = findViewById(R.id.addEnglishName);
        danishName = findViewById(R.id.addDanishName);
        population = findViewById(R.id.addPopulation);
        latitude = findViewById(R.id.addLatitude);
        longitude = findViewById(R.id.addLongitude);

    }
}
