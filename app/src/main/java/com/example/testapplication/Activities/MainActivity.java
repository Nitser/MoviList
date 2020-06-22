package com.example.testapplication.Activities;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.testapplication.AlbumFragment;
import com.example.testapplication.LiveDataHelper;
import com.example.testapplication.Models.Movie;
import com.example.testapplication.NetworkService;
import com.example.testapplication.NoInternetFragment;
import com.example.testapplication.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NetworkService networkService;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if(isNetworkConnected(getApplication())) {
            networkService.loadJsonData(1);
            loadFragment(AlbumFragment.newInstance());
            swipeRefreshLayout.setRefreshing(true);
        } else
            loadFragment(new NoInternetFragment());

        LiveDataHelper.getInstance().observeMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> albumsList) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        loadFragment(AlbumFragment.newInstance());
    }

    private void init(){
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setEnabled(false);
        networkService = NetworkService.getInstance();
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.root, fragment);
        ft.commit();
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null && cm.getActiveNetworkInfo() != null) ;
    }
}
