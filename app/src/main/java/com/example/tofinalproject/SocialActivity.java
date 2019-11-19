package com.example.tofinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class SocialActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextSearch;
    Button buttonSearch;
    TextView textViewResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewResponse = findViewById(R.id.textViewResponse);

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
            Intent reviewIntent = new Intent(this, ReviewMovieActivity.class);
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

        String url = "https://api.themoviedb.org/3/search/movie?api_key=4c6eb1a29b65b0358211ff79367ee62f&language=en-US&query=Avengers&page=1&include_adult=false";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                textViewResponse.setText("Response is: "+ response.substring(0,500));

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonResponse.get("results"));

                    Log.d("myTag", "Response: " + jsonResponse);

                    Object movieObject = jsonResponse.get("results");


                    Toast.makeText(SocialActivity.this, "Response: " + movieObject, Toast.LENGTH_SHORT).show();


//                    textViewResponse.setText(movieObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                int number = Integer.parseInt(response.substring(1,1));
//
//
//
//                for (int i = 0; i < number; i++) {
//                }


                Toast.makeText(SocialActivity.this, "Response: " + response, Toast.LENGTH_SHORT).show();

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
