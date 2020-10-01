package edu.lewisu.android.mts.animeoftheseason;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner yearSpinner = findViewById(R.id.yearSpinner);
        final Spinner seasonSpinner = findViewById(R.id.seasonSpinner);
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent animeIntent = new Intent(getApplicationContext(), ListActivity.class);
                String year = yearSpinner.getSelectedItem().toString();
                String season = seasonSpinner.getSelectedItem().toString();
                animeIntent.putExtra("year", year);
                animeIntent.putExtra("season", season);
                startActivity(animeIntent);
            }
        });
    }
}
