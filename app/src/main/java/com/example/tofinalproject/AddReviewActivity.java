package com.example.tofinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class AddReviewActivity extends AppCompatActivity {

    // TODO: take out, replace with info taken from MovieActivity
    String movieTitle = "The Joker";

    TextView textViewHeader, textViewTitle;
    ImageView imageViewMoviePoster;
    RatingBar ratingBar;

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
        // imageViewMoviePoster.setImageDrawable(Drawable(file_path etc));

        // display the current movie's rating
        // TODO: find out if rating should be for user to change, or just an indicator
        ratingBar = findViewById(R.id.ratingBar);
        // ratingBar.setRating(rating);
    }
}
