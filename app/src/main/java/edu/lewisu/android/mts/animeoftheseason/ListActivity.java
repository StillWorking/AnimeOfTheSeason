package edu.lewisu.android.mts.animeoftheseason;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity implements RecyclerViewAdapter.AnimeAdapterClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        //Get Intent from MainActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String yearString = extras.getString("year");
        String seasonString = extras.getString("season");

        //Create URI
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.jikan.moe")
                .appendPath("v3")
                .appendPath("season")
                .appendPath(yearString)
                .appendPath(seasonString);

        Uri animeList = builder.build();
        Log.d("url", animeList.toString());

        //Download list
        DownloadAnime downloadAnimeList = new DownloadAnime(this);
        downloadAnimeList.execute(animeList);
    }

    private static class DownloadAnime extends AsyncTask<Uri, Void, ArrayList<Anime>> {

        private WeakReference<ListActivity> activityReference;

        DownloadAnime(ListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<Anime> doInBackground(Uri... uris) {

            OkHttpClient client = new OkHttpClient();
            String jsonData = "";
            ArrayList<Anime> animes = new ArrayList<>();

            try {
                URL url = new URL(uris[0].toString());
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                Request request = builder.build();
                Response response = client.newCall(request).execute();

                if (response.body() != null) {
                    jsonData = response.body().string();
                } else {
                    return null;
                }

            } catch (Exception ex) {
                Log.d(TAG, ex.toString());
            }

            String title;
            String producer;
            String type;
            String imgUrl;
            String synopsis;
            String rating;
            String episodes;

            try {
                JSONObject results = new JSONObject(jsonData);
                JSONArray animeArray = results.getJSONArray("anime");

                for (int i = 0; i < animeArray.length(); i++) {
                    JSONObject animeObject = animeArray.getJSONObject(i);
                    imgUrl = animeObject.getString("image_url");
                    synopsis = animeObject.getString("synopsis");
                    title = animeObject.getString("title");
                    JSONArray producerArray = animeObject.getJSONArray("producers");
                    if (producerArray.length() >= 1) {
                        producer = producerArray.getJSONObject(0).getString("name");
                    } else {
                        producer = "N/A";
                    }
                    type = animeObject.getString("type");
                    rating = animeObject.getString("score");
                    episodes = animeObject.getString("episodes");

                    Anime anime = new Anime(title, imgUrl, type, episodes, producer, rating, synopsis);
                    animes.add(anime);
                }
                return animes;

            } catch (Exception ex) {
                Log.d(TAG, ex.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Anime> animes) {

            ListActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;

            }
            if (animes != null) {
                activity.recyclerViewAdapter.setViewData(animes);
            }
        }
    }

    // Send Data to DetailActivity using Intent
    @Override
    public void onClick(Anime animes) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("anime", animes);
        startActivity(intent);
    }
}
