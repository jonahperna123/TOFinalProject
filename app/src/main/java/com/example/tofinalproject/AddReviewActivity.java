package com.example.tofinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AddReviewActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener, View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    // TODO: take out, replace with info taken from MovieActivity
    String contentType = "movie";
    String movie_poster = "";

    TextView textViewHeader, textViewTitle;
    ImageView imageViewMoviePoster;
    RatingBar ratingBar;
    EditText editTextReview;
    Button buttonSubmit;
    BottomNavigationView nav;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        // add navbar
        nav = findViewById(R.id.nav_AddReviewActivity);
        nav.setOnNavigationItemSelectedListener(this);
        // unselect first item in navbar, we are not on "Home"
        nav.getMenu().getItem(0).setCheckable(false);

        Intent intent = getIntent();

        String movie_title = intent.getStringExtra("movie_title");
        movie_poster = intent.getStringExtra("movie_poster");

        // set title to "Add Review for {{movie title}}"
        textViewHeader = findViewById(R.id.textViewHeader_AddReviewActivity);
        textViewHeader.setText(getString(R.string.header_AddReviewActivity, movie_title));

        // display the current movie title
        textViewTitle = findViewById(R.id.textViewTitle_AddReviewActivity);
        textViewTitle.setText(getString(R.string.title_AddReviewActivity, movie_title));

        // display the current movie's poster
        imageViewMoviePoster = findViewById(R.id.imageViewMoviePoster_AddReviewActivity);
        Picasso.get().load(movie_poster).into(imageViewMoviePoster);

        // user can set rating
        ratingBar = findViewById(R.id.ratingBar_AddReviewActivity);
        ratingBar.setOnRatingBarChangeListener(this);

        // user can add text review
        editTextReview = findViewById(R.id.editTextReview_AddReviewActivity);
        // validate that user's review is at most 5 words
        editTextReview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // grab string from EditText and split into separate words
                String[] reviewWords = editTextReview.getText().toString().split(" ");
                if (reviewWords.length > 5) {
                    editTextReview.setError("Exceeds 5 word max");
                }
            }
        });

        // review is saved on submit
        buttonSubmit = findViewById(R.id.buttonSubmit_AddReviewActivity);
        buttonSubmit.setOnClickListener(this);

        // initialize Firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        // update rating bar based on user input
        ratingBar.setRating(rating);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSubmit) {
            // validate user input
            if (editTextReview.length() == 0 && ratingBar.getRating() == 0) {
                buttonSubmit.setError("Please enter a review.");
                Toast.makeText(this, "Please enter a review.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (editTextReview.getError() != null) {
                buttonSubmit.setError("Please enter a valid 5-word summary.");
                Toast.makeText(this, "Please enter a valid 5-word summary.", Toast.LENGTH_SHORT).show();
                return;
            }

            buttonSubmit.setError(null);

            // use currentUser's email as identifier
            FirebaseUser currentUser = auth.getCurrentUser();

            // create Review object
            float rating = ratingBar.getRating();
            String review = editTextReview.getText().toString();
            Review r = new Review(rating, review, currentUser.getEmail(), contentType, textViewTitle.getText().toString(), movie_poster);

            // access the reviews node of database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference reviews = database.getReference("reviews");
            reviews.push().setValue(r, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(AddReviewActivity.this, "Failed to add review. " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(AddReviewActivity.this, "Added review successfully!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home_BottomNavBar:
                // re-select "Home" upon click
                nav.getMenu().getItem(0).setCheckable(true);
                Intent homeIntent = new Intent(this, homePageActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.search_BottomNavBar:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.profile_BottomNavBar:
                Intent profileIntent = new Intent(this, ViewProfileActivity.class);
                startActivity(profileIntent);
                return true;
            default:
                return false;
        }
    }
}
