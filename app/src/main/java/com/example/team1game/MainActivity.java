package com.example.team1game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default code, sets default view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* start button */
        // refered to https://www.youtube.com/watch?v=nEe7bx_IuOQ
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            // on click, switch class
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InitialConfigScreen.class);
                startActivity(intent);
            }
        });
    }
}