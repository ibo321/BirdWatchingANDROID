package com.example.bruger.birdwatching;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ItemListActivity extends AppCompatActivity {

    TextView nameEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        nameEnglish = findViewById(R.id.birdItemTextView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Bird obj = (Bird) bundle.getSerializable("NameEnglish");
            this.nameEnglish.setText("ID: " + obj.getId() + "\n" + "English Name: " + obj.getNameEnglish() + "\n" + "Danish Name: " + " " + obj.getNameDanish());
        }
    }

    public void onClickBackButton(View view) {
        Intent intent = new Intent(ItemListActivity.this, BirdsActivity.class);
        startActivity(intent);
    }
}

