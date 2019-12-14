package com.example.tofinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextSearch;
    Button buttonSearch;

    private RecyclerView recyclerViewSocial;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLinearLayoutManager; //layout manager for recycler view, need this for a recyclerview

    private ArrayList<Movie> moviesSearch;

    ArrayList<Pair<String, String>> ratings = new ArrayList<>();


    private void initRecyclerView() {

        recyclerViewSocial = findViewById(R.id.recyclerViewSocial); //Link recyclerview variable to xml
        adapter = new RecyclerViewAdapter(moviesSearch, this); //Linking the adapter to recyclerView,
        //check out the RecyclerViewAdapter (this is the hard part)
        mLinearLayoutManager = new LinearLayoutManager(this);

        recyclerViewSocial.setLayoutManager(mLinearLayoutManager);

        recyclerViewSocial.setAdapter(adapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        moviesSearch = new ArrayList<Movie>();

        buttonSearch.setOnClickListener(this);

        initRecyclerView();

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

    @Override
    public void onClick(View v) {
        String contentSearchText = editTextSearch.getText().toString();

        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        String url2 ="https://api.themoviedb.org/3/search/movie?api_key=4c6eb1a29b65b0358211ff79367ee62f&language=en-US&query=" + contentSearchText + "&page=1&include_adult=false";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("API RESPONSE: ", "response is coming through " + response);

                try {

                    Log.d("IN TRY BLOCK: ", "within try block and string parsing code section");
                    JSONObject jsonResponse = new JSONObject(response);

                    //grabbing total results number
                    String resultsNumber = "total_results";
                    int firstBreakResults = response.indexOf(resultsNumber) + resultsNumber.length() + 2;
                    int secondBreakResults = response.indexOf("total_pages") - 2;

                    //grabs length of response to set boundary for substring
                    int totalLength = response.length();
                    int breakNumber = 0;
                    String title = "original_title";
                    String forId1 = "\"id\":";
                    String forId2 = "adult";
                    String movie_id;

                    //TO FIX: when number of results is greater than 20, app breaks
                    int index = Integer.parseInt(response.substring(firstBreakResults, secondBreakResults));
                    if (index > 20) {
                        index = 20;
                    }

                    //for loop loops through the number of results returned, searches by indexOf for "original_title"
                    for (int i = 0; i < index; i++) {
                        Log.d("IN FOR LOOP: ", "in for loop for indexing");

                            String tempString = response.substring(breakNumber, totalLength);

                            int firstBreakTitle = tempString.indexOf(title) + title.length() + 3;
                            int secondBreakTitle = tempString.indexOf("genre_ids") - 3;

                        if (i < 5) {
                            int firstBreakId = tempString.indexOf(forId1) + forId1.length();
                            int secondBreakId = tempString.indexOf(forId2) - 2;

                            if (tempString.substring(firstBreakId, secondBreakId).length() > 10) {
                                forId2 = "video";
                                secondBreakId = tempString.indexOf(forId2);
                            }

                            movie_id = tempString.substring(firstBreakId, secondBreakId);
                        } else {
                            movie_id = "00000";
                        }


                        breakNumber = breakNumber + secondBreakTitle + 4;

                        String movie_title = tempString.substring(firstBreakTitle, secondBreakTitle);

//                        Toast.makeText(SearchActivity.this, "Result: " + movie_title + ", " + movie_id, Toast.LENGTH_SHORT).show();

                       Log.d("MOVIE INFO: ", "Current Movie Object: " + movie_title + ", " + movie_id);

                       moviesSearch.add(new Movie(movie_title, movie_id, "", "", "", "", "", ratings));
                        adapter.notifyItemInserted(i);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(SearchActivity.this, "Error" + e, Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SearchActivity.this, "Response: " + error, Toast.LENGTH_SHORT).show();

                String errorMessage = "That didn't work!";

            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

    }

}
