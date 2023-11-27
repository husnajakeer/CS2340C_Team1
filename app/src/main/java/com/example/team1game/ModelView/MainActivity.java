package com.example.team1game.ModelView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.Model.GlobalMusicPlayer;
import com.example.team1game.R;


public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button quitButton;
    private GlobalMusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musicPlayer = new GlobalMusicPlayer(this, R.raw.background_music);
        musicPlayer.start();

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InstructionsScreen.class);
            startActivity(intent);
        });

        quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(view -> finish());


    }
}