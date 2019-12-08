package com.example.tofinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SocialActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextSearch;
    Button buttonSearch;
    TextView textViewResponse, textViewResponse2, textViewResponseNumber, textViewResponse3, textViewResponse4;

    private RecyclerView recyclerView; //recycler view variable
    private RecyclerView.LayoutManager layoutManager; //layout manager for recycler view, need this for a recyclerview

    private ArrayList<Movie> movies;

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.recyclerView); //Link recyclerview variable to xml
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(movies, this); //Linking the adapter to recyclerView,
        //check out the RecyclerViewAdapter (this is the hard part)
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //Setting the layout manager, commonly used is linear

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
//        textViewResponse = findViewById(R.id.textViewResponse);
//        textViewResponse2 = findViewById(R.id.textViewResponse2);
//        textViewResponse3 = findViewById(R.id.textViewResponse3);
//        textViewResponse4 = findViewById(R.id.textViewResponse4);
//        textViewResponseNumber = findViewById(R.id.textViewResponseNumber);

        movies = new ArrayList<Movie>();


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
    public void onClick(View v) {
        String contentSearchText = editTextSearch.getText().toString();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url2 ="https://api.themoviedb.org/3/search/movie?api_key=4c6eb1a29b65b0358211ff79367ee62f&language=en-US&query=" + contentSearchText + "&page=1&include_adult=false";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);

//                    textViewResponse2.setText("Response: "+ response.substring(0, 1000));

                    //grabbing total results number
                    String resultsNumber = "total_results";
                    int firstBreakResults = response.indexOf(resultsNumber) + resultsNumber.length() + 2;
                    int secondBreakResults = response.indexOf("total_pages") - 2;
//                    textViewResponseNumber.setText(response.substring(firstBreakResults, secondBreakResults));

                    //grabs length of response to set boundary for substring
                    int totalLength = response.length();
                    int breakNumber = 0;
                    String title = "original_title";
                    String forId1 = "\"id\":";
                    String forId2 = "adult";

                    String allTitles = "Titles: ";
                    String allIds = "Ids: ";

                    //TO FIX: when number of results is greater than 20, app breaks
                    int index = Integer.parseInt(response.substring(firstBreakResults, secondBreakResults));
                    if (index > 20) {
                        index = 20;
                    }

                    //for loop loops through the number of results returned, searches by indexOf for "original_title"
                    for (int i = 0; i < 10; i++) {

                            String tempString = response.substring(breakNumber, totalLength);

                            int firstBreakTitle = tempString.indexOf(title) + title.length() + 3;
                            int secondBreakTitle = tempString.indexOf("genre_ids") - 3;

                            String movie_id = "00000";

                            while (i < 5) {
                                int firstBreakId = tempString.indexOf(forId1) + forId1.length();
                                int secondBreakId = tempString.indexOf(forId2) - 2;

                                if (tempString.substring(firstBreakId, secondBreakId).length() > 10) {
                                    forId2 = "video";
                                    secondBreakId = tempString.indexOf(forId2) - 2;
                                }

                                movie_id = tempString.substring(firstBreakId, secondBreakId);

                            }

//                            Toast.makeText(SocialActivity.this, "BREAK NUMBER: " + firstBreakTitle + ", " + secondBreakTitle, Toast.LENGTH_SHORT).show();

                            breakNumber = breakNumber + secondBreakTitle + 4;

//                        Toast.makeText(SocialActivity.this, "ID: " + tempString.substring(firstBreakId, secondBreakId), Toast.LENGTH_SHORT).show();

//                        if (i == 0) {
//                                textViewResponse.setText(response.substring(firstBreakTitle, secondBreakTitle));
//                            } else {
//                                textViewResponse2.setText(tempString.substring(firstBreakTitle, secondBreakTitle));
//                            }

                        String movie_title = tempString.substring(firstBreakTitle, secondBreakTitle);

                        Toast.makeText(SocialActivity.this, "Result: " + movie_title + ", " + movie_id, Toast.LENGTH_SHORT).show();

                        movies.add(new Movie(movie_title, movie_id));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SocialActivity.this, "Response: " + error, Toast.LENGTH_SHORT).show();

                String errorMessage = "That didn't work!";

//                textViewResponse.setText(errorMessage);

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}
