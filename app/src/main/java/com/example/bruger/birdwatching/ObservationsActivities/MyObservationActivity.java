package com.example.bruger.birdwatching.ObservationsActivities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bruger.birdwatching.Adapters.ObservationsItemAdapter;
import com.example.bruger.birdwatching.JavaClass.Observations;
import com.example.bruger.birdwatching.R;

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
import java.util.ArrayList;
import java.util.List;

public class MyObservationActivity extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_observation);

        TextView titleText = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.app_bar);
        titleText.setText("Observations");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = findViewById(R.id.list_item);

        //Tilføjer en titel til mit listView "Observations"
//        TextView listHeader = new TextView(this);
//        listHeader.setText("Observations");
//        listHeader.setTextAppearance(android.R.style.TextAppearance_Large);
//        list.addHeaderView(listHeader);

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

        //Her definer jeg JsonArray og JsonObject for at tilføje mine properties fra min java-klasse "Observations" og ind i mit listview
        @Override
        protected void onPostExecute(String result) {
            TextView messageText = findViewById(R.id.observations_exception_message);
            final List<Observations> observations = new ArrayList<>();
            try {

//                ArrayList<Observations> observationsList = new ArrayList<>();
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

                    Observations observation = new Observations();

                    observation.setBirdId(birdId);
                    observation.setComment(comment);
                    observation.setDate(date);
                    observation.setId(id);
                    observation.setLatitude(latitude);
                    observation.setLongitude(longitude);
                    observation.setPlacename(placename);
                    observation.setPopulation(population);
                    observation.setUserId(userId);
                    observation.setNameDanish(nameDanish);
                    observation.setNameEnglish(nameEnglish);

                    observations.add(observation);
                }
            } catch (JSONException ex) {
                messageText.append(ex.toString());
            }

            final ListView listView = findViewById(R.id.list_item);
            //ArrayAdapter<Observations> adapter = new ArrayAdapter<Observations>(MyObservationActivity.this, android.R.layout.simple_list_item_1, observationsList);
            ObservationsItemAdapter adapter = new ObservationsItemAdapter(getBaseContext(), R.layout.specific_list_observations, observations);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        Intent intent = new Intent(MyObservationActivity.this, ObservationsItemListActivity.class);
                        intent.putExtra("NameEnglish", (Observations) listView.getItemAtPosition(i));
                        intent.putExtra("Placename", (Observations) listView.getItemAtPosition(i));
                        startActivity(intent);
                    } catch (Exception ex){
                        Log.d("myobserv", ex.toString());
                    }

                }
            });
        }

        @Override
        protected void onCancelled(String message) {
            super.onCancelled(message);
            final TextView messageText = findViewById(R.id.observations_exception_message);
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
