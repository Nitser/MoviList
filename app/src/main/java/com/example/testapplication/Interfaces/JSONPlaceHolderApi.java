package com.example.testapplication.Interfaces;

import com.example.testapplication.Models.JSONMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Get JSON by nonstatic URL and convert response to JSONObject
 */
public interface JSONPlaceHolderApi {

    @GET("3/discover/movie?primary_release_year=2019&sort_by=popularity.desc&api_key=6306465c1312b9eb83336efbe2a81f54")
    Call<JSONMovieResponse> listRepos(@Query("page") int pageNumber);

}

