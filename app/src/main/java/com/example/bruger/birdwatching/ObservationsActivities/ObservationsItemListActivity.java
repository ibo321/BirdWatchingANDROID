package com.example.bruger.birdwatching.ObservationsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.bruger.birdwatching.JavaClass.Observations;
import com.example.bruger.birdwatching.R;

public class ObservationsItemListActivity extends AppCompatActivity {

    TextView nameEnglish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observations_item_list);

        //Toolbar (actionbar)
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //jeg henter min intent.getExtra metode i min MyObservationActivity og putter den ind i en bundle og fremviser det i denne aktivitet
        nameEnglish = findViewById(R.id.observations_content_description_view);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Observations obj = (Observations) bundle.getSerializable("NameEnglish");
            this.nameEnglish.setText("ID: "  + obj.getId() + "\n" + "Danish name: " + obj.getNameDanish());
        }
    }
}
