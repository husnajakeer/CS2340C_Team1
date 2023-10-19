package com.example.team1game.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.Model.Player;
import com.example.team1game.R;

public class Room2 extends AppCompatActivity {
    private Player player;
    private TextView playerNameTextView;
    private TextView healthPointsTextView;
    private TextView difficultyTextView;
    private TextView scoreTextView;
    private ImageView characterSprite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room2_screen);
        Player player = Player.getPlayer();

        // Textviews and buttons
        playerNameTextView = findViewById(R.id.playerNameTextView);
        healthPointsTextView = findViewById(R.id.healthPointsTextView);
        difficultyTextView = findViewById(R.id.difficultyTextView);
        characterSprite = findViewById(R.id.characterSprite);
        scoreTextView = findViewById(R.id.scoreTextView);
        Button nextButton = findViewById(R.id.nextButton);
        scoreTextView.setText("Score: " + player.getScore());

        // Player singleton variables
        String playerName = player.getName();
        String difficulty = player.getDifficulty();
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

        if ("eva_idle".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.eva_idle);
        } else if ("kaya_idle".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.kaya_idle);
        } else if ("rika_idle".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.rika_idle);
        }

        final Handler handler = new Handler();
        final Runnable updateScoreRunnable = new Runnable() {
            @Override
            public void run() {

                scoreTextView.setText("Score: " + player.getScore());
                if (player.getScore() > 0) {
                    //postDelayed(this, 1000);  // Recall every second
                    player.setScore(player.getScore() - 1);
                    scoreTextView.setText("Score: " + player.getScore());
                    handler.postDelayed(this, 1000);
                }
            }
        };

        nextButton.setOnClickListener(view -> {
            handler.removeCallbacks(updateScoreRunnable);

            Intent intent = new Intent(Room2.this, Room3.class);
            intent.putExtra("sprite", sprite);
            startActivity(intent);
        });

        handler.postDelayed(updateScoreRunnable, 1000);  // Start after 1 second
    }
}