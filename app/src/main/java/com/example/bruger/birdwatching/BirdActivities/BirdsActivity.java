package com.example.bruger.birdwatching.BirdActivities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
//import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.bruger.birdwatching.Adapters.BirdItemAdapter;
import com.example.bruger.birdwatching.JavaClass.Bird;
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

public class BirdsActivity extends AppCompatActivity  {

    BirdItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birds);

        //Toolbar (actionbar)
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //"BIRD" title
        TextView listHeader = new TextView(this);
        listHeader.setText("Birds");
        listHeader.setTextAppearance(android.R.style.TextAppearance_Large);
        ListView list = findViewById(R.id.birdsView);
        list.addHeaderView(listHeader);

        //REST URL
        final BirdsActivity.ReadJSONFeedTask task = new BirdsActivity.ReadJSONFeedTask();
        task.execute("http://birdobservationservice.azurewebsites.net/Service1.svc/birds");
    }


    //region JSON async and REST
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
            Log.d("mybird", result);
            TextView messageTextView = findViewById(R.id.birds_exception_message);
            final List<Bird> birds = new ArrayList<>();
            try {

                //Jeg sætter "Bird" klassen i et ArrayList så jeg kan udskrive dem i en ListView
                //ArrayList<Bird> birdlist = new ArrayList<>();

                //Servicen starter med et array "[" og derfor skal der bruges et JSONArray før JSONObject
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    final String created = jsonObject.getString("Created");
                    final Integer id = jsonObject.getInt("Id");
                    final String nameDanish = jsonObject.getString("NameDanish");
                    final String nameEnglish = jsonObject.getString("NameEnglish");
                    final String photoUrl = jsonObject.getString("PhotoUrl");
                    final String userId = jsonObject.getString("UserId");
//                    textView.append("created: " + created + "\n"  + "id: " + id + "\n" + "nameDanish: " +  nameDanish + "\n" + "nameEnglish: " +  nameEnglish + "\n" + "photoUrl: " +
//                            photoUrl + "\n" + "userId: " + userId + "\n" + "\n");

                    //Opretter et nyt object af klassen "Bird" og derfra får fat i "set-metoderne" fra klassen
                    Bird bird = new Bird();

                    bird.setCreated(created);
                    bird.setId(id);
                    bird.setNameDanish(nameDanish);
                    bird.setNameEnglish(nameEnglish);
                    bird.setPhotoUrl(photoUrl);
                    bird.setUserId(userId);
                    Log.d("mybird", bird.toString());
                    //tilføjer klassen i en liste
                    birds.add(bird);
                }

            } catch (JSONException ex) {
                messageTextView.setText(ex.toString());
            }

            final ListView listView = findViewById(R.id.birdsView);

            //opretter et nyt object af ArrayAdapter
            //ArrayAdapter<Bird> adapter = new ArrayAdapter<Bird>(BirdsActivity.this, android.R.layout.simple_list_item_1, birds);

            //Custom adapter jeg har oprettet
            BirdItemAdapter adapter = new BirdItemAdapter(getBaseContext(), R.layout.specific_list_birds, birds);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {

                        Intent intent = new Intent(getBaseContext(), ItemListActivity.class);
                        intent.putExtra("NameEnglish", (Bird) listView.getItemAtPosition(i));
                        startActivity(intent);

                    } catch (Exception ex) {
                        Log.d("mybird", ex.toString());
                    }

                }
            });
        }

        @Override
        protected void onCancelled(String message) {
            super.onCancelled(message);
            final TextView textview = findViewById(R.id.birds_exception_message);
            textview.setText(message);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);


        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //openHTTPconnection
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
    //endregion
}
