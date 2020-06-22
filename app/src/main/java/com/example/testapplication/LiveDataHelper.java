package com.example.testapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.example.testapplication.Models.Movie;

import java.util.List;

public class LiveDataHelper {
    private MediatorLiveData<List<Movie>> movieList = new MediatorLiveData<>();
    private static LiveDataHelper liveDataHelper;

    public LiveDataHelper() {}

    synchronized public static LiveDataHelper getInstance() {
        if (liveDataHelper == null)
            liveDataHelper = new LiveDataHelper();
        return liveDataHelper;
    }

    public void updateMovieList(List<Movie> newMoviewList) {
        movieList.postValue(newMoviewList);
    }
    public LiveData<List<Movie>> observeMovieList() {
        return movieList;
    }

}