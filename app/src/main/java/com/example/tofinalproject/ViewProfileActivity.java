package com.example.tofinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Button testButton, buttonViewTvShows, buttonViewMovies;
    TextView textViewFollowers, textViewFollowing;
    ArrayList<String> moviesRated, tvShowsRated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


       // testButton = findViewById(R.id.button);
        buttonViewTvShows = findViewById(R.id.buttonViewTVShows);
        buttonViewMovies = findViewById(R.id.buttonViewMovies);

        textViewFollowers = findViewById(R.id.textViewFollowersView);
        textViewFollowing = findViewById(R.id.textViewFollowingView);

        //testButton.setOnClickListener(this);
        buttonViewMovies.setOnClickListener(this);
        buttonViewTvShows.setOnClickListener(this);

        setFollowingAndFollowers();


    }

    public void setFollowingAndFollowers(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");



        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);

                textViewFollowers.setText(String.valueOf(value.numFollowers));
                textViewFollowing.setText(String.valueOf(value.numFollowing));

                moviesRated = value.moviesRated;
                tvShowsRated = value.tvShowsRated;


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }


    @Override
    public void onClick(View view) {

        if (view == buttonViewMovies) {

        } else if (view == buttonViewTvShows) {

        }














//        if (view == testButton) {
//
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference("user");
//
//
//            String firstName = "Test";
//            String lastName = "User";
//            String userName = "testUser1";
//            String email = "testuser@hotmail.com";
//            String id = "0000000";
//            String password = "password";
//            ArrayList<String> followers = new ArrayList<String>();
//            followers.add("testfollower1");
//            ArrayList<String> following = new ArrayList<String>();
//            following.add("testfollowing1");
//            int numFollowing = 1;
//            int numFollowers = 1;
//            String phoneNumber = "856-701-4203";
//
//            User userOne = new User(firstName, lastName, userName, email, id, password, followers, followers, numFollowing, numFollowers, phoneNumber);
//
//
//            myRef.setValue(userOne);
//
//        }
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
