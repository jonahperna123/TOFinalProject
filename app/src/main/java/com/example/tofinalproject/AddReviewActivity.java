package com.example.tofinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AddReviewActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {

    // TODO: take out, replace with info taken from MovieActivity
    String movieTitle = "The Joker";

    TextView textViewHeader, textViewTitle;
    ImageView imageViewMoviePoster;
    RatingBar ratingBar;
    EditText editTextReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        // set title to "Add Review for {{movie title}}"
        textViewHeader = findViewById(R.id.textViewHeader_AddReviewActivity);
        textViewHeader.setText(getString(R.string.header_AddReviewActivity, movieTitle));

        // display the current movie title
        textViewTitle = findViewById(R.id.textViewTitle_AddReviewActivity);
        textViewTitle.setText(getString(R.string.title_AddReviewActivity, movieTitle));

        // display the current movie's poster
        imageViewMoviePoster = findViewById(R.id.imageViewMoviePoster_AddReviewActivity);
        // TODO: grab image from API
        // imageViewMoviePoster.setImageDrawable(Drawable(file_path etc));

        // user can set rating
        ratingBar = findViewById(R.id.ratingBar);
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
                    editTextReview.setError("Max of 5 words");
                }
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

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        // update rating bar based on user input
        ratingBar.setRating(rating);
    }
}
