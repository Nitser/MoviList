package com.example.testapplication.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
    @SerializedName("id")
    @Expose
    private String movieId;

    @SerializedName("title")
    @Expose
    private String movieTitle;

    @SerializedName("original_title")
    @Expose
    private String movieOriginalTitle;

    @SerializedName("vote_count")
    @Expose
    private String voteCount;

    @SerializedName("vote_average")
    @Expose
    private String voteAverage;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("popularity")
    @Expose
    private String popularity;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("adult")
    @Expose
    private boolean adult;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    public String getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getCountry() {
        return country;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }
}
