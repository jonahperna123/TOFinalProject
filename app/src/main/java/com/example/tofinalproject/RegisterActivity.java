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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewCreate;
    EditText editTextNewFirstName, editTextNewEmail, editTextConfirmEmail, editTextNewPassword, editTextConfirmPassword, editTextNewLastName;
    Button buttonCreateAccount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textViewCreate = findViewById(R.id.textViewCreate);
        editTextNewEmail = findViewById(R.id.editTextNewEmail);
        editTextConfirmEmail = findViewById(R.id.editTextConfirmEmail);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        editTextNewFirstName = findViewById(R.id.editTextNewFirstName);
        editTextNewLastName = findViewById(R.id.editTextNewLastName);

        buttonCreateAccount.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        String confirmedPassword = editTextConfirmPassword.getText().toString();
        String email = editTextNewEmail.getText().toString();
        String confirmedEmail = editTextConfirmEmail.getText().toString();
        String password = editTextNewPassword.getText().toString();
        String firstName = editTextNewFirstName.getText().toString();
        String lastName = editTextNewLastName.getText().toString();


        if((view == buttonCreateAccount) && confirmedPassword.equals(password) && confirmedEmail.equals(email)){
            makeUsers(firstName, lastName, email, password);
            }
        else{
            Toast.makeText(this, "Failed to confirm email or password.", Toast.LENGTH_SHORT).show();
        }



}

    private void makeUsers(String firstName, String lastName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener< AuthResult >() {
                @Override
                public void onComplete(@NonNull Task< AuthResult > task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(RegisterActivity.this, loginActivity.class);
                        startActivity(mainIntent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterActivity.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String createUserEmail = editTextNewEmail.getText().toString();
            String createFirstName = editTextNewFirstName.getText().toString();
            String createLastName = editTextNewLastName.getText().toString();


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("user");

            User newUser = new User(createUserEmail, createFirstName, createLastName);
            myRef.push().setValue(newUser);
            Toast.makeText(this, "Added user to database", Toast.LENGTH_SHORT).show();
        }
    }
}
