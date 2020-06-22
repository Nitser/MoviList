package com.example.testapplication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.testapplication.Interfaces.JSONPlaceHolderApi;
import com.example.testapplication.Models.Movie;
import com.example.testapplication.Models.JSONMovieResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://api.themoviedb.org";
    private Retrofit mRetrofit;
    private LiveDataHelper liveDataHelper;
    private List<Movie> movieList = new ArrayList<>();


    private NetworkService() {
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        liveDataHelper = LiveDataHelper.getInstance();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

    public void loadJsonData(int pageNumber){
        final String TAG = "LoadJSONData";

        NetworkService.getInstance()
                .getJSONApi()
                .listRepos(pageNumber)
                .enqueue(new Callback<JSONMovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<JSONMovieResponse> call, @NonNull Response<JSONMovieResponse> response) {
                        JSONMovieResponse jsonMovieResponse = response.body();

                        if(jsonMovieResponse != null) {
                            List<Movie> newList = new ArrayList<>(Arrays.asList(jsonMovieResponse.getMovieArray()));

                            for (Movie m : newList) {
                            SimpleDateFormat from = new SimpleDateFormat("yyyy-mm-dd", Locale.UK);
                            SimpleDateFormat to = new SimpleDateFormat("d MMMM yyyy", Locale.UK);
                            Date date = null;
                            try {
                                date = from.parse(m.getReleaseDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            m.setReleaseDate(to.format(date));
                            }

                            movieList.addAll(newList);
                            liveDataHelper.updateMovieList(movieList);

                        } else {
                            Log.i(TAG, "null objects " + response.toString());
                        }
                    }

                    /**
                     * Unsuccessful connection calls the NoInternetFragment
                     */
                    @Override
                    public void onFailure(@NonNull Call<JSONMovieResponse> call, @NonNull Throwable t) {
                        Log.i("JsonCall", "Error occurred while getting request!");
//                        loadNoInternetFragment();
                        t.printStackTrace();
                    }
                });
    }
}
