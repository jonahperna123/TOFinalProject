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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewCreate;
    EditText editTextNewName, editTextNewEmail, editTextConfirmEmail, editTextNewPassword, editTextConfirmPassword;
    Button buttonCreateAccount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textViewCreate = findViewById(R.id.textViewCreate);
        editTextNewEmail = findViewById(R.id.editTextNewEmail);
        editTextNewName = findViewById(R.id.editTextNewName);
        editTextConfirmEmail = findViewById(R.id.editTextConfirmEmail);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);

        buttonCreateAccount.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        String email = editTextNewEmail.getText().toString();
        String password = editTextNewPassword.getText().toString();
        String name = editTextNewName.getText().toString();


        if(view == buttonCreateAccount)
            makeUsers(name, email, password);

    }

    private void makeUsers(String name, String email, String password) {
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

                        // ...
                    }
                });
    }
}
