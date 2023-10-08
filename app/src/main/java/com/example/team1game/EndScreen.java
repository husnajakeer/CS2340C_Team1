package com.example.team1game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.Model.Attempt;
import com.example.team1game.Model.Leaderboard;
import com.example.team1game.Model.Player;



public class EndScreen extends AppCompatActivity {

    private Button restartButton;
    private Button quitButton;
    private Player player;
    private TextView leaderboardTextView;
    private TextView currentScoreTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = Player.getPlayer();

        setContentView(R.layout.activity_end_screen);
        setLeaderboard();

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
    private void setLeaderboard() {
        leaderboardTextView = findViewById(R.id.leaderboardTextView);
        currentScoreTextView = findViewById(R.id.currentScoreTextView);

        Leaderboard leaderboard = Leaderboard.getInstance();
        StringBuilder leaderboardStr = new StringBuilder("Leaderboard\n");

        for (Attempt attempt : leaderboard.getAttempts()) {
            leaderboardStr.append(attempt.getPlayerName()).append(": ")
                    .append(attempt.getScore()).append(" - ")
                    .append(attempt.getAttemptTime()).append("\n");
        }

        leaderboardTextView.setText(leaderboardStr.toString());
        currentScoreTextView.setText("Current Score" + player.getScore());
    }
}