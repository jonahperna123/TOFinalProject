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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    TextView textViewMovieTitle, textViewDescription;
    Button buttonReviewMovie;
    ImageView imageViewPoster;
    BottomNavigationView nav;

    String movie_title;
    String poster_url;
    String movie_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        textViewMovieTitle = findViewById(R.id.textViewMovieTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        buttonReviewMovie = findViewById(R.id.buttonReviewMovie);
        imageViewPoster = findViewById(R.id.imageViewPoster);

        // handle nav menu
        nav = findViewById(R.id.nav_MovieActivity);
        nav.setOnNavigationItemSelectedListener(this);
        // unselect first item in navbar, we are not on "Home"
        nav.getMenu().getItem(0).setCheckable(false);

        buttonReviewMovie.setOnClickListener(this);

        Intent intent = getIntent();

        String movieId = intent.getStringExtra("movie_id");

//        Toast.makeText(this, "Movie Id: " + movieId, Toast.LENGTH_SHORT).show();

        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=4c6eb1a29b65b0358211ff79367ee62f&language=en-US";

        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response

                        String original_title = "original_title";

                        int firstBreakTitle = response.indexOf(original_title) + original_title.length() + 3;
                        int secondBreakTitle = response.indexOf("overview") - 3;

                        movie_title = response.substring(firstBreakTitle, secondBreakTitle);

                        textViewMovieTitle.setText(movie_title);

                        String poster_path = "backdrop_path";

                        int firstBreakPoster = response.indexOf(poster_path) + poster_path.length() + 3;
                        int secondBreakPoster = response.indexOf("belongs_to_collection") - 3;

                        poster_url = "https://image.tmdb.org/t/p/w500" + response.substring(firstBreakPoster, secondBreakPoster);

                        Picasso.get().load(poster_url).into(imageViewPoster);

                        String overview = "overview";

                        int firstBreakOverview = response.indexOf(overview) + overview.length() + 3;
                        int secondBreakOverview = response.indexOf("popularity") - 3;

                        movie_description = response.substring(firstBreakOverview, secondBreakOverview);

                        textViewDescription.setText(movie_description);

//                        Toast.makeText(MovieActivity.this, "Response: " + response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

// Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        Intent movieReviewIntent = new Intent(this, AddReviewActivity.class);

        movieReviewIntent.putExtra("movie_title", movie_title);
        movieReviewIntent.putExtra("movie_poster", poster_url);
        movieReviewIntent.putExtra("movie_description", movie_description);

        startActivity(movieReviewIntent);

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
