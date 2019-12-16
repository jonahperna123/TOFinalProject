package com.example.tofinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class homePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private ArrayList<Review> reviews;

    private BottomNavigationView nav;
    private FrameLayout frame;

    private RecyclerView recyclerView;
    private HomeRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        nav = findViewById(R.id.nav_HomePageActivity);
        nav.setOnNavigationItemSelectedListener(this);
        frame = findViewById(R.id.frame_HomePageActivity);

        auth = FirebaseAuth.getInstance();

        // TODO: eventually combine this w/ RecyclerView asynchronously
        // getFollowing();
        reviews = new ArrayList<Review>();

        // init RecyclerView stuff
        recyclerView = findViewById(R.id.recyclerView_HomePageActivity);
        mAdapter = new HomeRecyclerViewAdapter(reviews, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getFollowing();
    }

    private void getFollowing() {
        int currSize = reviews.size();
        if (currSize > 0) {
            reviews.clear();
            mAdapter.notifyItemRangeRemoved(0, currSize);
        }

        FirebaseUser currentUser = auth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");

        // get the following list for the currentUser
        userRef.orderByChild("email").equalTo(currentUser.getEmail()).limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                User user = dataSnapshot.getValue(User.class);

                if (user.following == null) {
                    return;
                }
                getReviews(user.following);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void getReviews(ArrayList<String> following) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reviewRef = database.getReference("reviews");

        // for each user you are following, retrieve their reviews
        for (String followingEmail : following) {
            reviewRef.orderByChild("userEmail").equalTo(followingEmail).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Review review = dataSnapshot.getValue(Review.class);

                    reviews.add(review);
                    mAdapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home_BottomNavBar:
                // already in home
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
