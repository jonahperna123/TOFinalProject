package com.example.tofinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonRegister;
    TextView textViewWelcome;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewWelcome = findViewById(R.id.textViewWelcome);

        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        }


    @Override
    public void onClick(View view) {
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (view == buttonLogin)
            loginUsers (email, password);


        if (view == buttonRegister) {
            Intent mainIntent = new Intent(loginActivity.this,RegisterActivity.class);
            startActivity(mainIntent);
        }


    }

    private void loginUsers(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener< AuthResult >() {
                    @Override
                    public void onComplete(@NonNull Task< AuthResult > task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent mainIntent = new Intent(loginActivity.this,homePageActivity.class);
                            startActivity(mainIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(loginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

}
