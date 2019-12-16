package com.example.tofinalproject;

import java.util.ArrayList;

public class User {

    public String firstName;
    public String lastName;
    public String email;
    public ArrayList<String> followers;
    public ArrayList<String> following;
    public int numFollowing;
    public int numFollowers;
    public String phoneNumber;

    public User() {

    }

    public User(String email, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.followers = new ArrayList<String>();
        // hardcoded so everyone is following "ja@ja.com"
        this.following = new ArrayList<String>() {
            {
                add("ja@ja.com");
            }
        };
        this.numFollowing = 0;
        this.numFollowers = 0;
        this.phoneNumber = new String ();
    }
}
