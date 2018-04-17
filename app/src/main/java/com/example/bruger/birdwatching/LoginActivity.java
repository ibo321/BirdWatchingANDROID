package com.example.bruger.birdwatching;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public static final String PREF_FILE_NAME = "loginPref";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    private SharedPreferences sharedPref;
    private FirebaseAuth mAuth;
    Button loginButton;
    EditText emailEditText, passwordEditText;
    CheckBox rememberMeCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        mAuth = FirebaseAuth.getInstance();

        //sharedPreference for at gemme email + password når brugeren klikker på checkboxen
        String email = sharedPref.getString(EMAIL, null);
        String password = sharedPref.getString(PASSWORD, null);
        if (sharedPref.contains("checked") && sharedPref.getBoolean("checked", false))
        {
            rememberMeCB.setChecked(true);
        } else {
            rememberMeCB.setChecked(false);
        }
        if (email != null && password != null){
            emailEditText.setText(email);
            passwordEditText.setText(password);
        }
    }

    //her er finder jeg allie mine view-id
    public void findViews() {
        loginButton = findViewById(R.id.loginButton);
        passwordEditText = findViewById(R.id.passwordContent);
        emailEditText = findViewById(R.id.emailContent);
        sharedPref = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        rememberMeCB = findViewById(R.id.user_check_box);
    }

    //Log in via firebase authentication
    public void onClickLogin(View view) {
        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //her fortæller vi at hvis checkboxen er checked så skal den gemme oplysningerne når brugeren logger ud
                            SharedPreferences.Editor editor = sharedPref.edit();
                            if (rememberMeCB.isChecked()){
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
                            Log.e("login", email + " " + "password: " + password);
                            Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong e-mail or password - try again",
                                    Toast.LENGTH_LONG).show();
                            Log.e("login", "fejl", task.getException());
                        }
                    }
                });
    }

    //Register via firebase authentication
    public void onClickRegister(View view) {
        String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Successfully created an account!", Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Something went wrong! (i dont know what)",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void onBackPressed() {
        //Så der ikke sker noget hvis man trykker på tilbage-knappen
    }
}