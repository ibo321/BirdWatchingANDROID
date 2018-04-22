package com.example.bruger.birdwatching.Authentication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.bruger.birdwatching.MainActivity;
import com.example.bruger.birdwatching.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Intializer mine et-eller-andet
    public static final String PREF_FILE_NAME = "loginPref";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    SharedPreferences sharedPref;
    FirebaseAuth mAuth;
    EditText emailEditText, passwordEditText;
    CheckBox rememberMeCB;
    Button login;
    boolean editTextStatus;
    ProgressDialog progressDialog;
    ActionMenuView amvMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //sharedPreference for at gemme email + password når brugeren klikker på checkboxen
        String email = sharedPref.getString(EMAIL, null);
        String password = sharedPref.getString(PASSWORD, null);
        if (sharedPref.contains("checked") && sharedPref.getBoolean("checked", false)) {
            rememberMeCB.setChecked(true);
        } else {
            rememberMeCB.setChecked(false);
        }
        if (email != null && password != null) {
            emailEditText.setText(email);
            passwordEditText.setText(password);
        }

        // Tilføjer onClickListener så jeg kan bruge funktionaliteten der gør at den IKKE CRASHER når jeg trykker LOGIN
        // når tekstfeltene er tomme
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to check EditText is empty or no status.
                checkEditTextIsEmptyOrNot();

                // If EditText is true then this block with execute.
                try {
                    if (editTextStatus) {

                        // If EditText is not empty than onClickLogin method will call.
                        onClickLogin();
                    }

                    // If EditText is false then this block with execute.
                    else {
                        Toast.makeText(LoginActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Log.d("login", ex.toString());
                }
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        //jeg fjerner de navigation-buttons i loginactivity
        menu.findItem(R.id.logout).setVisible(false);
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
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    //her er finder jeg allie mine view-id
    public void findViews() {
        passwordEditText = findViewById(R.id.passwordContent);
        emailEditText = findViewById(R.id.emailContent);
        sharedPref = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        rememberMeCB = findViewById(R.id.user_check_box);
        login = findViewById(R.id.loginButton);
        progressDialog = new ProgressDialog(LoginActivity.this);
    }

    //Metode der trekker om tekstfelterne IKKE er tomme
    public void checkEditTextIsEmptyOrNot() {
        String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();
        try {
            if (TextUtils.isEmpty(email)) {
                editTextStatus = false;
                emailEditText.setError("Check your email!");
            } else if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Check your password!");
            } else {
                editTextStatus = true;
            }
        } catch (Exception ex) {
            Log.d("login", ex.toString());
        }
    }

    //Log in via firebase authentication
    public void onClickLogin() {
        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        //progressbar
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        //Her logger jeg ind
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //her fortæller vi at hvis checkboxen er checked så skal den gemme oplysningerne når brugeren logger ud
                            SharedPreferences.Editor editor = sharedPref.edit();
                            if (rememberMeCB.isChecked()) {
                                editor.putBoolean("checked", true);
                                editor.putString(EMAIL, email);
                                editor.putString(PASSWORD, password);
                            } else {
                                editor.putBoolean("checked", false);
                                editor.remove(EMAIL);
                                editor.remove(PASSWORD);
                            }
                            editor.apply();

                            //Bruger log for at se hvad brugerens email og password er hvorefter der kommer en toast der viser brugeren at det var et success
                            Log.d("login", "Email: " + email + "\n" + "Password: " + password);
                            Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong e-mail or password - try again",
                                    Toast.LENGTH_LONG).show();
                            Log.e("login", "fejl", task.getException());
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void onBackPressed() {
        //Så der ikke sker noget hvis man trykker på tilbage-knappen
    }

    public void onClickNavigateToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}