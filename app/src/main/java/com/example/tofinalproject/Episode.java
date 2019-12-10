package com.example.tofinalproject;

public class Episode {

    public String seriesTitle;
    public String episodeTitle;
    public String seasonNumber;
    public String episodeNumber;
    public String episodeDescription;
    public String episodeRating;
    public String yearReleased;

    public Episode() {

    }

    public Episode(String yearReleased, String seriesTitle, String episodeTitle, String seasonNumber, String episodeNumber, String episodeDescription, String episodeRating) {
        this.seriesTitle = seriesTitle;
        this.episodeTitle = episodeTitle;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeDescription = episodeDescription;
        this.episodeRating = episodeRating;
        this.yearReleased = yearReleased;
    }
}
