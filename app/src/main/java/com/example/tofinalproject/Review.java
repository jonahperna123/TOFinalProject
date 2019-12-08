package com.example.tofinalproject;

public class Review {
    public float starRating;
    public String review;
    public String userEmail;
    public String contentType;
    public String contentTitle;

    public Review() {}

    public Review(float starRating, String review, String userEmail, String contentType, String contentTitle) {
        this.starRating = starRating;
        this.review = review;
        this.userEmail = userEmail;
        this.contentType = contentType;
        this.contentTitle = contentTitle;
    }
}
