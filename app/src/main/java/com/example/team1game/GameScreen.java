package com.example.team1game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.Model.Attempt;
import com.example.team1game.Model.Leaderboard;
import com.example.team1game.Model.Player;

public class GameScreen extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView healthPointsTextView;
    private TextView difficultyTextView;
    private TextView scoreTextView;
    private ImageView characterSprite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        playerNameTextView = findViewById(R.id.playerNameTextView);
        healthPointsTextView = findViewById(R.id.healthPointsTextView);
        difficultyTextView = findViewById(R.id.difficultyTextView);
        characterSprite = findViewById(R.id.characterSprite);
        scoreTextView = findViewById(R.id.scoreTextView);
        Button nextButton = findViewById(R.id.nextButton);

        String playerName = getIntent().getStringExtra("playerName");
        String difficulty = getIntent().getStringExtra("difficulty");
        String sprite = getIntent().getStringExtra("sprite");

        int numOfHearts = 0;

        if ("Easy".equals(difficulty)) {
            numOfHearts = 5;
        } else if ("Medium".equals(difficulty)) {
            numOfHearts = 3;
        } else if ("Hard".equals(difficulty)) {
            numOfHearts = 1;
        }

        playerNameTextView.setText("Name: " + playerName);
        healthPointsTextView.setText("Health: " + numOfHearts + " hearts");
        difficultyTextView.setText("Difficulty: " + difficulty);

        if ("Sprite1".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.sprite1);
        } else if ("Sprite2".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.sprite2);
        } else if ("Sprite3".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.sprite3);
        }



        final Handler handler = new Handler();
        Player playerInstance = Player.getPlayer();
        playerInstance.setScore(100);
        final Runnable updateScoreRunnable = new Runnable() {
            @Override
            public void run() {

                scoreTextView.setText("Score: " + playerInstance.getScore());
                if (playerInstance.getScore() > 0) {
                    //postDelayed(this, 1000);  // Recall every second
                    playerInstance.setScore(playerInstance.getScore()-1);
                    scoreTextView.setText("Score: " + playerInstance.getScore());
                    handler.postDelayed(this, 1000);
                }
            }
        };

        nextButton.setOnClickListener(view -> {
            Attempt attempt = new Attempt(playerInstance.getName(), playerInstance.getScore(), playerInstance.getDifficulty());
            Leaderboard.getInstance().addAttempt(attempt);
            handler.removeCallbacks(updateScoreRunnable);

            Intent intent = new Intent(GameScreen.this, EndScreen.class);
            startActivity(intent);
        });

        handler.postDelayed(updateScoreRunnable, 1000);  // Start after 1 second



    }
}