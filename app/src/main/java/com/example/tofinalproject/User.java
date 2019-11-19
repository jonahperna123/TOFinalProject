package com.example.tofinalproject;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class User {

    public String username;
    public String email;
    public String id;
    public String password;
    public ArrayList<String> followers;
    public ArrayAdapter<String> following;
    public int numFollowing;
    public int numFollowers;
    public String phoneNumber;

    public User() {

    }

    public User(String username, String email, String id, String password, ArrayList<String> followers, ArrayAdapter<String> following, int numFollowing, int numFollowers, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.password = password;
        this.followers = followers;
        this.following = following;
        this.numFollowing = numFollowing;
        this.numFollowers = numFollowers;
        this.phoneNumber = phoneNumber;
    }
}
