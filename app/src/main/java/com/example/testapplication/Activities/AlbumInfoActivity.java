package com.example.testapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.testapplication.Models.Movie;
import com.example.testapplication.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class AlbumInfoActivity extends AppCompatActivity {

    private Movie currentMovie;
    private final String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_info);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("movie"))
            currentMovie = (Movie) bundle.getSerializable("movie");

        init();
    }

    private void init() {
        ImageView poster = findViewById(R.id.photo);
        TextView title = findViewById(R.id.title);
        TextView originalTitle = findViewById(R.id.original_title);
        TextView date = findViewById(R.id.date);
        TextView popularity = findViewById(R.id.popularity);
        TextView description = findViewById(R.id.description);
        ProgressBar progressBar = findViewById(R.id.progress_circular);
        TextView progressText = findViewById(R.id.progress_text);

        double vote = Double.valueOf(currentMovie.getVoteAverage());
        String strDate = getResources().getText(R.string.release_date) + " " +currentMovie.getReleaseDate();
        String strPopularity = getResources().getText(R.string.popularity) + " " + currentMovie.getPopularity();

        Picasso.with(this).load(MOVIE_BASE_URL + currentMovie.getPosterPath())
                .error(R.drawable.empty)
                .placeholder(R.drawable.empty)
                .into(poster);

        title.setText(currentMovie.getMovieTitle());
        originalTitle.setText(currentMovie.getMovieOriginalTitle());
        description.setText(currentMovie.getOverview());
        progressBar.setProgress(Double.valueOf(vote*10).intValue());
        progressText.setText(currentMovie.getVoteAverage());

        SpannableString spanDate = new SpannableString(strDate);
        spanDate.setSpan(new TextAppearanceSpan(this, R.style.MediumText), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanDate.setSpan(new TextAppearanceSpan(this, R.style.LightText), 13, strDate.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        date.setText(spanDate, TextView.BufferType.SPANNABLE);

        SpannableString spanPopularity = new SpannableString(strPopularity);
        spanPopularity.setSpan(new TextAppearanceSpan(this, R.style.MediumText), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanPopularity.setSpan(new TextAppearanceSpan(this, R.style.LightText), 11, strPopularity.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        popularity.setText(spanPopularity, TextView.BufferType.SPANNABLE);
    }

    public void onClickBack(View view) {
        finish();
    }
}
