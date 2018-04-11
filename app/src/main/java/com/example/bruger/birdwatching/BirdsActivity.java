package com.example.bruger.birdwatching;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;

public class BirdsActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ListView list;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birds);

        gestureDetector = new GestureDetector(this, this);
        list = (ListView) findViewById(R.id.birdsView);
        final BirdsActivity.ReadJSONFeedTask task = new BirdsActivity.ReadJSONFeedTask();
        task.execute("http://birdobservationservice.azurewebsites.net/Service1.svc/birds");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BirdsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //Toast.makeText(this, "onFling", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "onFling " + e1.toString() + "::::" + e2.toString());

        boolean leftSwipe = e1.getX() > e2.getX();
        //Log.d(TAG, "onFling left: " + leftSwipe);
        if (leftSwipe) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return true; // done
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventHandlingFinished = true;
        //return eventHandlingFinished;
        return gestureDetector.onTouchEvent(event);
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
            TextView messageTextView = findViewById(R.id.messageText);

            try {

                //Jeg sætter "Bird" klassen i et ArrayList så jeg kan udskrive dem i en ListView
                ArrayList<Bird> birdlist = new ArrayList<>();

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

                    //tilføjer klassen i en liste
                    birdlist.add(bird);
                }


                ListView listView = findViewById(R.id.birdsView);

                //opretter et nyt object af ArrayAdapter
                ArrayAdapter<Bird> adapter = new ArrayAdapter<Bird>(BirdsActivity.this, android.R.layout.simple_list_item_1, birdlist);
                listView.setAdapter(adapter);


            } catch (JSONException ex) {
                messageTextView.setText(ex.toString());
            }
        }


        @Override
        protected void onCancelled(String message) {
            super.onCancelled(message);
            final TextView textview = findViewById(R.id.messageText);
            textview.setText(message);
        }

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

}
