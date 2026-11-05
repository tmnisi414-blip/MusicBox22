package com.example.musicbox2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Using a Handler to delay the transition to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close splash screen activity
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

public class MainActivity extends AppCompatActivity {

    private ListView songListView;
    private TextView songInfoTextView;
    private Button playButton;
    private MediaPlayer mediaPlayer;

    private String[] songs = {
            "Song1 - Artist1",
            "Song2 - Artist2",
            "Song3 - Artist3",
            "Song4 - Artist4"
    };

    private int[] songResources = {
            R.raw.song1,
            R.raw.song2,
            R.raw.song3,
            R.raw.song4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songListView = findViewById(R.id.songListView);
        songInfoTextView = findViewById(R.id.songInfoTextView);
        playButton = findViewById(R.id.playButton);

        // Set up the ListView with song data
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
        songListView.setAdapter(adapter);

        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update song details when a song is selected
                songInfoTextView.setText(songs[position]);
                playButton.setEnabled(true); // Enable play button
                mediaPlayer = MediaPlayer.create(MainActivity.this, songResources[position]);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.start(); // Start playing the selected song
                    playButton.setText("Pause");
                    playButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                                playButton.setText("Play");
                            } else {
                                mediaPlayer.start();
                                playButton.setText("Pause");
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release the MediaPlayer resource when the activity stops
        }
    }
}