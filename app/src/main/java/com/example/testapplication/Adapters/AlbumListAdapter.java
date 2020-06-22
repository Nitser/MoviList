package com.example.testapplication.Adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapplication.Activities.AlbumInfoActivity;
import com.example.testapplication.Models.Movie;
import com.example.testapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder> {

    private final String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w300";
    private List<Movie> movieList = new ArrayList<>();

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    static class AlbumListViewHolder extends RecyclerView.ViewHolder {
        private DatePickerDialog.OnDateSetListener dateSetListener;
        CardView mView;
        ImageView poster;
        TextView title;
        TextView date;
        TextView description;
        TextView moreInfo;
        TextView calendar;

        AlbumListViewHolder(final CardView mView){
            super(mView);
            this.mView = mView;
            poster = mView.findViewById(R.id.photo);
            title = mView.findViewById(R.id.title);
            date = mView.findViewById(R.id.date);
            description = mView.findViewById(R.id.description);
            moreInfo = mView.findViewById(R.id.more_info);
            calendar = mView.findViewById(R.id.schedule_viewing);
        }

        void setClickListener(final Movie movie){
            moreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mView.getContext(), AlbumInfoActivity.class);
                    intent.putExtra("movie", movie);
                    mView.getContext().startActivity(intent);
                }
            });
            calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            mView.getContext(),
                            R.style.DialogCalendar,
                            dateSetListener,
                            year, month, day);
                    dialog.show();
                }
            });

            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    //save like notification
                    addCalendarEvent(movie, year, month, dayOfMonth);

                }
            };
        }

        private void addCalendarEvent(Movie movie, int year, int month, int day){

            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", cal.getTimeInMillis());
            intent.putExtra("allDay", true);
            intent.putExtra("title", "Lets watch the movie: " + movie.getMovieTitle());
//            intent.putExtra("rrule", "FREQ=YEARLY");
//            intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
//            intent.putExtra("description", "A Test Description from android app");
//            intent.putExtra("eventLocation", "Geolocation");
            mView.getContext().startActivity(intent);

        }

    }

    @Override
    public AlbumListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView view = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_album, viewGroup, false);
        AlbumListAdapter.AlbumListViewHolder vh = new AlbumListViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(AlbumListViewHolder myViewHolder, int i) {
        Movie movie = movieList.get(i);

        Picasso.with(myViewHolder.mView.getContext()).load(MOVIE_BASE_URL + movie.getPosterPath())
                .error(R.drawable.empty)
                .placeholder(R.drawable.empty)
                .into(myViewHolder.poster);
        myViewHolder.poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
        myViewHolder.title.setText(movie.getMovieTitle());
        myViewHolder.date.setText(movie.getReleaseDate());
        myViewHolder.description.setText(movie.getOverview());
        myViewHolder.setClickListener(movie);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

}
