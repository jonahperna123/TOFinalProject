package com.example.tofinalproject;

import android.util.Pair;

import java.util.ArrayList;

public class Movie {
    public String titleMovie;
    public String idMovie;
    public String descriptionMovie;
    public String yearReleased;
    public String genre;
    public String director;
    public String posterLink;
    public ArrayList<Pair<String, String>> ratings;

    public Movie(String tite, String descriptionMovie, String yearReleased, String genre, String director, ArrayList<Pair<String, String>> ratings) {

    }

    public Movie(String titleMovie, String idMovie, String descriptionMovie, String yearReleased, String genre, String director,
                 String posterLink, ArrayList<Pair<String, String>> ratings) {
        this.titleMovie = titleMovie;
        this.idMovie = idMovie;
        this.descriptionMovie = descriptionMovie;
        this.yearReleased = yearReleased;
        this.genre = genre;
        this.director = director;
        this.posterLink = posterLink;
        this.ratings = ratings;
    }
}
