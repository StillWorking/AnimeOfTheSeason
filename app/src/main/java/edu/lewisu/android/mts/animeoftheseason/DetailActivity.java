package edu.lewisu.android.mts.animeoftheseason;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailActivity extends AppCompatActivity {
    Anime anime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        anime = intent.getParcelableExtra("anime");

        if (anime != null) {
            TextView titleTextView = findViewById(R.id.description_anime_title);
            titleTextView.setText(anime.getTitle());

            TextView typeTextView = findViewById(R.id.description_type);
            typeTextView.setText(anime.getType());

            TextView ratingTextView = findViewById(R.id.description_rating);
            ratingTextView.setText(anime.getRating());

            TextView producerTextView = findViewById(R.id.description_producer);
            producerTextView.setText(anime.getProducers());

            TextView episodesTextView = findViewById(R.id.description_episodes);
            episodesTextView.setText("Episodes: " + anime.getEpisodes());

            ImageView imgView = findViewById(R.id.description_thumbnail);
            Glide.with(this).load(anime.getImg()).into(imgView);

            TextView synopsisView = findViewById(R.id.synopsis);
            synopsisView.setText(anime.getSynopsis());

            CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
            collapsingToolbarLayout.setTitleEnabled(true);
            collapsingToolbarLayout.setTitle(anime.getTitle());
        }
    }
}
