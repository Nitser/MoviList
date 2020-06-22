package com.example.testapplication.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Receive object from URL
 */
public class JSONMovieResponse {
    @SerializedName("results")
    @Expose
    private Movie[] movieArray;

    public Movie[] getMovieArray() {
        return movieArray;
    }
}
