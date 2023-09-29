package com.example.team1game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.Model.Leaderboard;
import com.example.team1game.Model.Player;

public class EndScreen extends AppCompatActivity {

    private Button restartButton;
    private Button quitButton;
    private Leaderboard leaderboard;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = Player.getPlayer();
        leaderboard = Leaderboard.getLeaderboard();

        setContentView(R.layout.activity_end_screen);

        restartButton = findViewById(R.id.restartButton);
        quitButton = findViewById(R.id.quitButton);

        restartButton.setOnClickListener(view -> {
            Intent intent = new Intent(EndScreen.this, MainActivity.class);
            startActivity(intent);
        });

        quitButton.setOnClickListener(view -> {
            finishAffinity();  // This will close the entire app
        });
    }
}
