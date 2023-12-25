package com.example.team1game.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.team1game.Model.Attempt;
import com.example.team1game.Model.UnusedClasses.Leaderboard;
import com.example.team1game.Model.Player;
import com.example.team1game.ModelView.MainActivity;
import com.example.team1game.R;

public class LoseScreen extends AppCompatActivity {

    private Button restartButton;
    private Button quitButton;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = Player.getPlayer();
        setContentView(R.layout.activity_lose_screen);

        setLeaderboard();
        setCurrentAttempt();

        restartButton = findViewById(R.id.restartButton);
        quitButton = findViewById(R.id.quitButton);

        restartButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoseScreen.this, MainActivity.class);
            startActivity(intent);
        });

        quitButton.setOnClickListener(view -> {
            finishAffinity();  // This will close the entire app
        });
    }

    private void setLeaderboard() {
        Leaderboard leaderboard = Leaderboard.getInstance();
        TableLayout leaderboardTable = findViewById(R.id.leaderboardTable);

        for (Attempt attempt : leaderboard.getAttempts()) {
            TableRow row = new TableRow(this);
            TextView playerName = new TextView(this);
            TextView score = new TextView(this);
            TextView attemptTime = new TextView(this);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.MATCH_PARENT, 1);

            playerName.setLayoutParams(layoutParams);
            score.setLayoutParams(layoutParams);
            attemptTime.setLayoutParams(layoutParams);

            playerName.setGravity(Gravity.CENTER);
            score.setGravity(Gravity.CENTER);
            attemptTime.setGravity(Gravity.CENTER);

            playerName.setPadding(8, 8, 8, 8);
            score.setPadding(8, 8, 8, 8);
            attemptTime.setPadding(8, 8, 8, 8);

            playerName.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            score.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            attemptTime.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

            playerName.setText(attempt.getPlayerName());
            score.setText(String.valueOf(attempt.getScore()));
            attemptTime.setText(attempt.getAttemptTime().toString());

            row.addView(playerName);
            row.addView(score);
            row.addView(attemptTime);

            leaderboardTable.addView(row);
        }
    }

    private void setCurrentAttempt() {
        Leaderboard leaderboard = Leaderboard.getInstance();
        TableLayout mostRecentAttemptTable = findViewById(R.id.mostRecentAttemptTable);
        Attempt recentAttempt = leaderboard.getMostRecentAttempt();

        if (recentAttempt != null) {
            TableRow row = new TableRow(this);
            TextView playerName = new TextView(this);
            TextView score = new TextView(this);
            TextView attemptTime = new TextView(this);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.MATCH_PARENT, 1);

            playerName.setLayoutParams(layoutParams);
            score.setLayoutParams(layoutParams);
            attemptTime.setLayoutParams(layoutParams);

            playerName.setGravity(Gravity.CENTER);
            score.setGravity(Gravity.CENTER);
            attemptTime.setGravity(Gravity.CENTER);

            playerName.setPadding(8, 8, 8, 8);
            score.setPadding(8, 8, 8, 8);
            attemptTime.setPadding(8, 8, 8, 8);

            playerName.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            score.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
            attemptTime.setBackground(ContextCompat.getDrawable(this, R.drawable.border));

            playerName.setText(recentAttempt.getPlayerName());
            score.setText(String.valueOf(recentAttempt.getScore()));
            attemptTime.setText(recentAttempt.getAttemptTime().toString());

            row.addView(playerName);
            row.addView(score);
            row.addView(attemptTime);

            mostRecentAttemptTable.addView(row);
        }
    }
}
