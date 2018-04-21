package com.example.bruger.birdwatching;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    FirebaseAuth mAuth;
    boolean editTextStatus;
    Button signUp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailRegisterEditText);
        passwordEditText = findViewById(R.id.passwordRegisterEditText);
        signUp = findViewById(R.id.registerButton);
        progressDialog = new ProgressDialog(this);

        // Adding click listener to Sign Up Button.
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to check EditText is empty or no status.
                checkEmailEditTextIsEmptyOrNot();
                checkPasswordEditTextIsEmptyOrNot();
                // If EditText is true then this block with execute.
                if (editTextStatus) {

                    // If EditText is not empty than UserRegistrationFunction method will call.
                    onClickRegister();
                }

                // If EditText is false then this block with execute.
                else {
                    Toast.makeText(RegisterActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void checkEmailEditTextIsEmptyOrNot() {
        String email = emailEditText.getText().toString().trim();

        try {
            if (TextUtils.isEmpty(email)) {
                editTextStatus = false;
                emailEditText.setError("Cannot be empty!");
            } else {
                editTextStatus = true;
            }
        } catch (Exception ex){
            Log.d("register", ex.toString());
        }
    }

    public void checkPasswordEditTextIsEmptyOrNot() {
        final String password = passwordEditText.getText().toString().trim();

        try {
            if (TextUtils.isEmpty(password) && password.length() <= 6) {
                editTextStatus = false;
                passwordEditText.setError("Cannot be empty and must contain atleast 6 characters!");
            } else {
                editTextStatus = true;
            }
        } catch (Exception ex){
            Log.d("register", ex.toString());
        }
    }

    public void onClickRegister() {
        checkEmailEditTextIsEmptyOrNot();
        checkPasswordEditTextIsEmptyOrNot();

        String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        progressDialog.setMessage("Please Wait.. Can take up to 5 seconds.");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "Successfully created an account!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Wrong email or password!", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void onClickNavigate(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
