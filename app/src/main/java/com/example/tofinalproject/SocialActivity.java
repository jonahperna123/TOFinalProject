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

public class SocialActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextSearch;
    Button buttonSearch;
    TextView textViewResponse, textViewResponse2, textViewResponseNumber, textViewResponse3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewResponse = findViewById(R.id.textViewResponse);
        textViewResponse2 = findViewById(R.id.textViewResponse2);
        textViewResponse3 = findViewById(R.id.textViewResponse3);
        textViewResponseNumber = findViewById(R.id.textViewResponseNumber);

        buttonSearch.setOnClickListener(this);

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

//        String url = "https://api.themoviedb.org/3/search/movie?api_key=4c6eb1a29b65b0358211ff79367ee62f&language=en-US&query=Avengers&page=1&include_adult=false";

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
                    textViewResponseNumber.setText(response.substring(firstBreakResults, secondBreakResults));

                    //grabs length of response to set boundary for substring
                    int totalLength = response.length();
                    int breakNumber = 0;
                    String title = "original_title";

                    String allTitles = "Titles: ";

                    //TO FIX: when number of results is greater than 20, app breaks
                    int index = Integer.parseInt(response.substring(firstBreakResults, secondBreakResults));
                    if (index > 20) {
                        index = 20;
                    }

                    //for loop loops through the number of results returned, searches by indexOf for "original_title"
                    for (int i = 0; i < index; i++) {
                        if (i == 0) {
                            int firstBreakTitle = response.indexOf(title) + title.length() + 3;
                            int secondBreakTitle = response.indexOf("genre_ids") - 3;

                            Toast.makeText(SocialActivity.this, "Break 1: " + firstBreakTitle + " Break 2: " + secondBreakTitle, Toast.LENGTH_SHORT).show();

                            textViewResponse.setText(response.substring(firstBreakTitle, secondBreakTitle));

                            allTitles = allTitles + " || "+ response.substring(firstBreakTitle, secondBreakTitle);

                            breakNumber = secondBreakTitle + 4;

                        } else {

                            String tempString = response.substring(breakNumber, totalLength);

                            int firstBreakTitle = tempString.indexOf(title) + title.length() + 3;
                            int secondBreakTitle = tempString.indexOf("genre_ids") - 3;

//                            Toast.makeText(SocialActivity.this, "BREAK NUMBER: " + firstBreakTitle + ", " + secondBreakTitle, Toast.LENGTH_SHORT).show();

                            breakNumber = breakNumber + secondBreakTitle + 4;

                            textViewResponse2.setText(tempString.substring(firstBreakTitle, secondBreakTitle));

                            allTitles = allTitles + " || "+ tempString.substring(firstBreakTitle, secondBreakTitle);

                            textViewResponse3.setText(allTitles);

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SocialActivity.this, "Response: " + error, Toast.LENGTH_SHORT).show();

                textViewResponse.setText("That didn't work!");

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}
