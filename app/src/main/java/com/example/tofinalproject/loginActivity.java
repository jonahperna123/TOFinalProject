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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.itemLogin) {
            Intent loginIntent = new Intent(this, loginActivity.class);
            startActivity(loginIntent);

        } else if (item.getItemId() == R.id.itemHome) {
            Intent homeIntent = new Intent(this, homePageActivity.class);
            startActivity(homeIntent);

        } else if (item.getItemId() == R.id.itemMessage) {
            Intent messageIntent = new Intent(this, MessageActivity.class);
            startActivity(messageIntent);

        } else if (item.getItemId() == R.id.itemMovie) {
            Intent movieIntent = new Intent(this, MovieActivity.class);
            startActivity(movieIntent);

        } else if (item.getItemId() == R.id.itemReview) {
            Intent reviewIntent = new Intent(this, AddReviewActivity.class);
            startActivity(reviewIntent);

        } else if (item.getItemId() == R.id.itemSocial) {
            Intent socialIntent = new Intent(this, SocialActivity.class);
            startActivity(socialIntent);

        } else if (item.getItemId() == R.id.itemProfile) {
            Intent profileIntent = new Intent(this, ViewProfileActivity.class);
            startActivity(profileIntent);

        }

        return super.onOptionsItemSelected(item);
    }
}
