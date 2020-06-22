package com.example.testapplication;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testapplication.Adapters.AlbumListAdapter;
import com.example.testapplication.Models.Movie;

import java.util.List;

/**
 * Fragment for news feed
 */
public class AlbumFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AlbumListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isNetworkConnected(view.getContext()))
            //no access to the Internet - load NoInternetFragment
            loadNoInternetFragment();
        else
            init(view);
    }

    private void init(View view){
        //initialize RecyclerView components
        final NetworkService networkService = NetworkService.getInstance();
        progressBar = view.findViewById(R.id.load);
        recyclerView = view.findViewById(R.id.albums_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AlbumListAdapter();
        recyclerView.setAdapter(adapter);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i("PAGES", " " + page + " size: " + totalItemsCount);

                if(isNetworkConnected(view.getContext())){
                    progressBar.setVisibility(View.VISIBLE);
                    networkService.loadJsonData(page+1);
                } else {
                    Toast.makeText(view.getContext(), "No internet", Toast.LENGTH_SHORT).show();
                }

            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        LiveDataHelper.getInstance().observeMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> albumsList) {
                if(adapter.getItemCount() == 0)
                    adapter.setMovieList(albumsList);
                progressBar.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void loadNoInternetFragment(){
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.root, new NoInternetFragment());
        trans.commit();
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null && cm.getActiveNetworkInfo() != null) ;
    }

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

}
