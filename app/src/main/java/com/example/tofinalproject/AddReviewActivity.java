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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddReviewActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    // TODO: take out, replace with info taken from MovieActivity
    String contentTitle = "The Joker";
    String contentType = "movie";

    TextView textViewHeader, textViewTitle;
    ImageView imageViewMoviePoster;
    RatingBar ratingBar;
    EditText editTextReview;
    Button buttonSubmit;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        // set title to "Add Review for {{movie title}}"
        textViewHeader = findViewById(R.id.textViewHeader_AddReviewActivity);
        textViewHeader.setText(getString(R.string.header_AddReviewActivity, contentTitle));

        // display the current movie title
        textViewTitle = findViewById(R.id.textViewTitle_AddReviewActivity);
        textViewTitle.setText(getString(R.string.title_AddReviewActivity, contentTitle));

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
            Review r = new Review(rating, review, currentUser.getEmail(), contentType, contentTitle);

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
                        Toast.makeText(AddReviewActivity.this, "Added review successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
