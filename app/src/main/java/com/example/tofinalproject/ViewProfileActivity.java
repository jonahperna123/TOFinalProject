package com.example.tofinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
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

    Button buttonViewTvShows, buttonViewMovies;
    TextView textViewFollowers, textViewFollowing;
    ArrayList<Movie> moviesRated = new ArrayList<>();
    ArrayList<Episode> tvShowsRated = new ArrayList<>();
    ArrayList<Review> userReviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        buttonViewTvShows = findViewById(R.id.buttonViewTVShows);
        buttonViewMovies = findViewById(R.id.buttonViewMovies);

        textViewFollowers = findViewById(R.id.textViewFollowersView);
        textViewFollowing = findViewById(R.id.textViewFollowingView);

        buttonViewMovies.setOnClickListener(this);
        buttonViewTvShows.setOnClickListener(this);

        setTestInformation();
        getUserInformation();
        initRecyclerView();



    }

    public void getUserInformation(){
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


                //DO THIS - grab values from realtime database
//                tvShowsRated =  new ArrayList<>();
//                moviesRated =  new ArrayList<>();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        final ArrayList<Review> firebaseReviews = new ArrayList<>();

        myRef = database.getReference("reviews");
        myRef.orderByChild("userEmail").equalTo("ja@ja.com").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review review = snapshot.getValue(Review.class);
                    firebaseReviews.add(review);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        final Handler handler = new Handler();
        final int delay = 1000;

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                if (!firebaseReviews.isEmpty()) {
                   userReviews = firebaseReviews;
                   initReviewRecyclerView();
                }
            }
        };

        handler.postDelayed(r, 1500);

    }

    public void initReviewRecyclerView() {
        RecyclerView reviewRecycleView = findViewById(R.id.reviewContentContainer);
        ReviewRecyclerViewAdapter reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(userReviews, this);
        reviewRecycleView.setAdapter(reviewRecyclerViewAdapter);
        reviewRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.ratedContentContainer);
        ProfileRecyclerViewAdapter recyclerViewAdapter = new ProfileRecyclerViewAdapter(moviesRated, tvShowsRated, this, true);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setMovieList() {
        RecyclerView recyclerView = findViewById(R.id.ratedContentContainer);
        ProfileRecyclerViewAdapter recyclerViewAdapter = new ProfileRecyclerViewAdapter(moviesRated, tvShowsRated, this, true);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    public void setTvShowList() {
        RecyclerView recyclerView = findViewById(R.id.ratedContentContainer);
        ProfileRecyclerViewAdapter recyclerViewAdapter = new ProfileRecyclerViewAdapter(moviesRated, tvShowsRated, this, false);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onClick(View view) {

        if (view == buttonViewMovies) {
            setMovieList();
        } else if (view == buttonViewTvShows) {
            setTvShowList();
        }

    }


    public void setTestInformation() {

        //set Movie  test information

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");


        String firstName = "Test";
        String lastName = "User";
        String userName = "testUser1";
        String email = "testuser@hotmail.com";
        String id = "0000000";
        String password = "password";
        ArrayList<String> followers = new ArrayList<String>();
        followers.add("testfollower1");
        ArrayList<String> following = new ArrayList<String>();
        following.add("testfollowing1");
        int numFollowing = 12;
        int numFollowers = 16;
        String phoneNumber = "856-701-4203";
        ArrayList<Movie> moviesRated = new ArrayList<>();
        ArrayList<Episode> tvShowsRated = new ArrayList<>();
        ArrayList<Pair<String, String>> ratings = new ArrayList<>();
        ratings.add(Pair.create("IMDB", "10/10"));


        Movie movie1 = new Movie("Shrek","id", "A story of an ogre and his donkey",
                "2000","Adventure", "The GingerBread Man", "asd", ratings);
        Movie movie2 = new Movie("Shrek 2","id", "A story of an ogre and his donkey",
                "2002","Adventure", "The GingerBread Man", "asd", ratings);
        Movie movie3 = new Movie("Shrek 3", "id","A story of an ogre and his donkey",
                "2004","Adventure", "The GingerBread Man", "asd", ratings);
        Movie movie4 = new Movie("Batman Begins", "id", "A story of an ogre and his donkey",
                "2006","Adventure", "The GingerBread Man", "asd", ratings);
        Movie movie5 = new Movie("The Joker", "id", "A story of an ogre and his donkey",
                "2009","Adventure", "The GingerBread Man", "asd", ratings);
        Movie movie6 = new Movie("The Dark Night Rises", "id", "A story of an ogre and his donkey",
                "2013","Adventure", "The GingerBread Man", "asd", ratings);
        Movie movie7 = new Movie("Cinderella", "id", "A story of an ogre and his donkey",
                "1959","Adventure", "The GingerBread Man", "asd", ratings);
        Movie movie8 = new Movie("Back to the Future", "id", "A story of an ogre and his donkey",
                "1980","Adventure", "The GingerBread Man", "asd", ratings);
        Movie movie9 = new Movie("Back to the Future 2", "id", "A story of an ogre and his donkey",
                "1984","Adventure", "The GingerBread Man", "asd", ratings);
        Movie movie10 = new Movie("Back to the Future 3", "id", "A story of an ogre and his donkey",
                "1988","Adventure", "The GingerBread Man", "asd", ratings);

        this.moviesRated.add(movie1);
        this.moviesRated.add(movie2);
        this.moviesRated.add(movie3);
        this.moviesRated.add(movie4);
        this.moviesRated.add(movie5);
        this.moviesRated.add(movie6);
        this.moviesRated.add(movie7);
        this.moviesRated.add(movie8);
        this.moviesRated.add(movie9);
        this.moviesRated.add(movie10);

        User userOne = new User(email, firstName, lastName);


        myRef.setValue(userOne);


        //set test Episode information
         String seriesTitle = "Psych";
         String episodeTitle = "Yang 3 in 2D";
         String seasonNumber = "4";
         String episodeNumber = "11";
         String episodeDescription = "Shawn and gus fight a new yang";
         String episodeRating = "10/10";
         String yearReleased = "2009";

         Episode testEpisode = new Episode(yearReleased, seriesTitle, episodeTitle, seasonNumber,
                 episodeNumber, episodeDescription, episodeRating);
         Episode testEpisode2 = new Episode("2004", "American Horror Story",
                 "Apocolypse", "7", "1", "Spooky", "9/10");
        Episode testEpisode3 = new Episode("2002", "American Horror Story",
                "Asylum", "7", "1", "Spooky", "9/10");
        Episode testEpisode4 = new Episode("2012", "American Horror Story",
                "Spooky Hotel", "7", "1", "Spooky", "9/10");
        Episode testEpisode5 = new Episode("2011", "Psych",
                "Disco didn't die", "6", "1", "Spooky", "9/10");
        Episode testEpisode6 = new Episode("2004", "Family Guy",
                "Bryan wins the Lottery", "7", "1", "Spooky", "9/10");
        Episode testEpisode7 = new Episode("1995", "South Park",
                "Cartman Drives a Boat", "7", "1", "Spooky", "9/10");
        Episode testEpisode8 = new Episode("1999", "South Park",
                "Butters Buys a Gun", "7", "1", "Spooky", "9/10");
        Episode testEpisode9 = new Episode("1962", "Oz",
                "Dorothy & the Yellow Brick Road", "7", "1", "Spooky", "9/10");
        Episode testEpisode10 = new Episode("2010", "Star Wars",
                "Rookies", "7", "1", "Spooky", "9/10");
        //populate episode
        this.tvShowsRated.add(testEpisode);
        this.tvShowsRated.add(testEpisode2);
        this.tvShowsRated.add(testEpisode3);
        this.tvShowsRated.add(testEpisode4);
        this.tvShowsRated.add(testEpisode5);
        this.tvShowsRated.add(testEpisode6);
        this.tvShowsRated.add(testEpisode7);
        this.tvShowsRated.add(testEpisode8);
        this.tvShowsRated.add(testEpisode9);
        this.tvShowsRated.add(testEpisode10);

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

        } else if (item.getItemId() == R.id.itemMovie) {
            Intent movieIntent = new Intent(this, MovieActivity.class);
            startActivity(movieIntent);

        } else if (item.getItemId() == R.id.itemReview) {
            Intent reviewIntent = new Intent(this, AddReviewActivity.class);
            startActivity(reviewIntent);

        } else if (item.getItemId() == R.id.itemSocial) {
            Intent socialIntent = new Intent(this, SearchActivity.class);
            startActivity(socialIntent);

        } else if (item.getItemId() == R.id.itemProfile) {
            Intent profileIntent = new Intent(this, ViewProfileActivity.class);
            startActivity(profileIntent);

        }

        return super.onOptionsItemSelected(item);
    }

}
