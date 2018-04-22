package com.example.bruger.birdwatching.BirdActivities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.bruger.birdwatching.JavaClass.Bird;
import com.example.bruger.birdwatching.R;

public class ItemListActivity extends AppCompatActivity {

    TextView nameEnglish;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEnglish = findViewById(R.id.birdItemTextView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Bird obj = (Bird) bundle.getSerializable("NameEnglish");
            this.nameEnglish.setText("ID: " + obj.getId() + "\n" + "English Name: " + obj.getNameEnglish() + "\n" + "Danish Name: " + " " + obj.getNameDanish());
        }
    }
}

