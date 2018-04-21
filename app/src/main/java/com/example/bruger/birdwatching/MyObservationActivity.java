package com.example.bruger.birdwatching;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyObservationActivity extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_observation);

        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = findViewById(R.id.list_item);
        final ReadJSONFeedTask task = new ReadJSONFeedTask();
        task.execute("http://birdobservationservice.azurewebsites.net/Service1.svc/observations?userid=");
    }

    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return readJSonFeed(urls[0]);
            } catch (IOException ex) {
                Log.e("SHIT", ex.toString());
                cancel(true);
                return ex.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            TextView messageText = findViewById(R.id.messageText);
            try {

                ArrayList<Observations> observationsList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    final Integer birdId = jsonObject.getInt("BirdId");
                    final String comment = jsonObject.getString("Comment");
                    final String date = jsonObject.getString("Created");
                    final Integer id = jsonObject.getInt("Id");
                    final Double latitude = jsonObject.getDouble("Latitude");
                    final Double longitude = jsonObject.getDouble("Longitude");
                    final String placename = jsonObject.getString("Placename");
                    final Integer population = jsonObject.getInt("Population");
                    final String userId = jsonObject.getString("UserId");
                    final String nameDanish = jsonObject.getString("NameDanish");
                    final String nameEnglish = jsonObject.getString("NameEnglish");
//                    textView.append("Bird ID: " + birdId + "\n" + "Comment: " + comment + "\n" + "Date: " + date + "\n" + "ID: " + id + "\n" + "Latitude: " +
//                            latitude + "\n" + "Longitude: " + longitude + "\n" + "Placename: " + placename + "\n" +
//                            "Population: " + population + "\n" + "User ID: " + userId + "\n" + "Danish name: " + nameDanish + "\n" + "English Name: " + nameEnglish + "\n" + "\n");

                    Observations observations = new Observations();

                    observations.setBirdId(birdId);
                    observations.setComment(comment);
                    observations.setDate(date);
                    observations.setId(id);
                    observations.setLatitude(latitude);
                    observations.setLongitude(longitude);
                    observations.setPlacename(placename);
                    observations.setPopulation(population);
                    observations.setUserId(userId);
                    observations.setNameDanish(nameDanish);
                    observations.setNameEnglish(nameEnglish);

                    observationsList.add(observations);
                }

                ListView listView = findViewById(R.id.list_item);
                ArrayAdapter<Observations> adapter = new ArrayAdapter<Observations>(MyObservationActivity.this, android.R.layout.simple_list_item_1, observationsList);
                listView.setAdapter(adapter);

            } catch (JSONException ex) {
                messageText.append(ex.toString());
            }
        }

        @Override
        protected void onCancelled(String message) {
            super.onCancelled(message);
            final TextView messageText = findViewById(R.id.messageText);
            messageText.setText(message);

        }

    }

    private String readJSonFeed(String urlString) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        final InputStream content = openHttpConnection(urlString);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        while (true) {
            final String line = reader.readLine();
            if (line == null)
                break;
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    private InputStream openHttpConnection(final String urlString)
            throws IOException {
        final URL url = new URL(urlString);
        final URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        final HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setAllowUserInteraction(false);
        // No user interaction like dialog boxes, etc.
        httpConn.setInstanceFollowRedirects(true);
        // follow redirects, response code 3xx
        httpConn.setRequestMethod("GET");
        httpConn.connect();
        final int response = httpConn.getResponseCode();
        if (response == HttpURLConnection.HTTP_OK) {
            return httpConn.getInputStream();
        } else {
            throw new IOException("HTTP response not OK");
        }
    }
}
