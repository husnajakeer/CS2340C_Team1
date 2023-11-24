package com.example.team1game.ModelView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.team1game.R;


public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InitialConfigScreen.class);
            startActivity(intent);
        });

        quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(view -> finish());


    }
}