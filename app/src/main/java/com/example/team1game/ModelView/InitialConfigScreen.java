package com.example.team1game.ModelView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// import player class to use it
import com.example.team1game.Model.Player;
import com.example.team1game.R;

public class InitialConfigScreen extends AppCompatActivity {
    private Player player;
    private EditText playerNameEditText;
    private RadioGroup difficultyRadioGroup;
    // difficulty buttons
    private RadioButton easyDifficulty;
    private RadioButton mediumDifficulty;
    private RadioButton hardDifficulty;
    // radio buttons
    private RadioButton radioButtonSprite1;
    private RadioButton radioButtonSprite2;
    private RadioButton radioButtonSprite3;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_config_screen);
        player = Player.getPlayer();

        playerNameEditText = findViewById(R.id.playerNameEditText);
        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        easyDifficulty = findViewById(R.id.easyDifficulty);
        mediumDifficulty = findViewById(R.id.mediumDifficulty);
        hardDifficulty = findViewById(R.id.hardDifficulty);

        radioButtonSprite1 = findViewById(R.id.radioButtonSprite1);
        radioButtonSprite2 = findViewById(R.id.radioButtonSprite2);
        radioButtonSprite3 = findViewById(R.id.radioButtonSprite3);

        continueButton = findViewById(R.id.continueButton);

        View.OnClickListener spriteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset all radio buttons
                radioButtonSprite1.setChecked(false);
                radioButtonSprite2.setChecked(false);
                radioButtonSprite3.setChecked(false);

                // Check the clicked one
                ((RadioButton) v).setChecked(true);
            }
        };

        radioButtonSprite1.setOnClickListener(spriteClickListener);
        radioButtonSprite2.setOnClickListener(spriteClickListener);
        radioButtonSprite3.setOnClickListener(spriteClickListener);

        continueButton.setOnClickListener(view -> {
            // store player info
            String playerName = playerNameEditText.getText().toString().trim();
            String difficulty;

            if (easyDifficulty.isChecked()) {
                difficulty = "Easy";
            } else if (mediumDifficulty.isChecked()) {
                difficulty = "Medium";
            } else if (hardDifficulty.isChecked()) {
                difficulty = "Hard";
            } else {
                Toast.makeText(this, "Please select a difficulty", Toast.LENGTH_SHORT).show();
                return;
            }
            // Player singleton class (make changes to it with accessors
            player.setName(playerName);
            // setDifficulty sees difficulty, changes it based on num of hearts
            player.setDifficulty(difficulty);

            String sprite;
            if (radioButtonSprite1.isChecked()) {
                sprite = "eva_idle";
            } else if (radioButtonSprite2.isChecked()) {
                sprite = "kaya_idle";
            } else if (radioButtonSprite3.isChecked()) {
                sprite = "rika_idle";
            } else {
                Toast.makeText(this, "Please select a sprite", Toast.LENGTH_SHORT).show();
                return;
            }

            if (playerName.isEmpty()) {
                playerNameEditText.setError("Name cannot be empty");
                return;
            }

            Intent intent = new Intent(InitialConfigScreen.this, GameScreen.class);
            intent.putExtra("playerName", playerName);
            intent.putExtra("difficulty", difficulty);
            intent.putExtra("sprite", sprite);
            startActivity(intent);
        });
    }
}