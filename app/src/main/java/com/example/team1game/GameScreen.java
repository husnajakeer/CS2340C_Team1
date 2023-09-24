package com.example.team1game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameScreen extends AppCompatActivity {

    private TextView playerNameTextView;
    private TextView healthPointsTextView;
    private TextView difficultyTextView;
    private ImageView characterSprite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        playerNameTextView = findViewById(R.id.playerNameTextView);
        healthPointsTextView = findViewById(R.id.healthPointsTextView);
        difficultyTextView = findViewById(R.id.difficultyTextView);
        characterSprite = findViewById(R.id.characterSprite);
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

        nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(GameScreen.this, EndScreen.class);
            startActivity(intent);
        });
    }
}
