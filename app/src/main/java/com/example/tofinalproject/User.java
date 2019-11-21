package com.example.tofinalproject;

import java.util.ArrayList;

public class User {

    public String firstName;
    public String lastName;
    public String username;
    public String email;
    public String id;
    public String password;
    public ArrayList<String> followers;
    public ArrayList<String> following;
    public int numFollowing;
    public int numFollowers;
    public String phoneNumber;
    public ArrayList<String> moviesRated;
    public ArrayList<String> tvShowsRated;

    public User() {

    }

    public User(String firstName, String lastName, String username, String email, String id, String password,
                ArrayList<String> followers, ArrayList<String> following, int numFollowing, int numFollowers,
                String phoneNumber, ArrayList<String> moviesRated, ArrayList<String> tvShowsRated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.id = id;
        this.password = password;
        this.followers = followers;
        this.following = following;
        this.numFollowing = numFollowing;
        this.numFollowers = numFollowers;
        this.phoneNumber = phoneNumber;
        this.moviesRated = moviesRated;
        this.tvShowsRated = tvShowsRated;
    }
}
