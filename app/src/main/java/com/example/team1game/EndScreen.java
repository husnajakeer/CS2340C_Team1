package com.example.team1game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EndScreen extends AppCompatActivity {

    Button restartButton;
    Button quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
