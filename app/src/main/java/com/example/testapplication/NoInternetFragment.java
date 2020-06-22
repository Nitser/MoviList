package com.example.testapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment for unsuccessful connection to the Internet
 */
public class NoInternetFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_internet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // refresh albums
                if (NoInternetFragment.this.isNetworkConnected(getView())) {
                    FragmentTransaction trans = NoInternetFragment.this.getFragmentManager().beginTransaction();
                    trans.replace(R.id.root, new AlbumFragment());
                    trans.commit();
                } else
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static NoInternetFragment newInstance() {
        return new NoInternetFragment();
    }

    private boolean isNetworkConnected(View view) {
        ConnectivityManager cm = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null && cm.getActiveNetworkInfo() != null) ;
    }

}
