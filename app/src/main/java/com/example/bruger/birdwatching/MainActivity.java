package com.example.bruger.birdwatching;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.bruger.birdwatching.Authentication.LoginActivity;
import com.example.bruger.birdwatching.BirdActivities.BirdsActivity;
import com.example.bruger.birdwatching.ObservationsActivities.MyObservationActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle("");
//        toolbar.setNavigationIcon(R.drawable.birdowl);
        setSupportActionBar(toolbar);

        gestureObject = new GestureDetectorCompat(this, new LearningGesture());
    }


    //region appbar menu items
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        menu.findItem(R.id.search).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                //setting code
                Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contact:
                //contact code
                Toast.makeText(getApplicationContext(), "contact", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                //about code
                Toast.makeText(getApplicationContext(), "code", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                //kalder metoden signout når der bliver klikket på ikonet
                signOut();
            default:
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion


    //region gesture = hvor man slider til venstre/højre for at skifte side
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            this.gestureObject.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

        class LearningGesture extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
                if (event2.getX() > event1.getX()) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
                return true;
            }
        }
    //endregion

    //region onClickButtons
    public void onClickMyObservation(View view) {
        Intent intent = new Intent(MainActivity.this, MyObservationActivity.class);
        startActivity(intent);
    }


    public void onClickBirds(View view) {
        Intent intent = new Intent(MainActivity.this, BirdsActivity.class);
        startActivity(intent);
    }

    //Log Out button
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Goodbye!", Toast.LENGTH_LONG).show();
    }
    //endregion

}
