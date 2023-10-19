package com.example.team1game.ModelView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.Model.Player;
import com.example.team1game.R;
import com.example.team1game.View.Room2;
//TODO make this file easier to read, not so big

public class GameScreen extends AppCompatActivity {
    private Player player;
    private int characterX;
    private int characterY;
    private TextView playerNameTextView;
    private TextView healthPointsTextView;
    private TextView difficultyTextView;
    private TextView scoreTextView;
    private ImageView characterSprite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        Player player = Player.getPlayer();
        player.setScore(100);

        // Textviews and buttons
        playerNameTextView = findViewById(R.id.playerNameTextView);
        healthPointsTextView = findViewById(R.id.healthPointsTextView);
        difficultyTextView = findViewById(R.id.difficultyTextView);
        characterSprite = findViewById(R.id.characterSprite);
        scoreTextView = findViewById(R.id.scoreTextView);
        Button nextButton = findViewById(R.id.nextButton);

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

            Intent intent = new Intent(GameScreen.this, Room2.class);
            intent.putExtra("sprite", sprite);
            startActivity(intent);
        });

        handler.postDelayed(updateScoreRunnable, 1000);  // Start after 1 second
        
        // detect player pos and move it
        detectPlayerPos(player);
    }
    public void detectPlayerPos(Player player){
        // Set up a ViewTreeObserver to get the position of the character ImageView
        ViewTreeObserver viewTreeObserver = characterSprite.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                characterX = characterSprite.getLeft();
                characterY = characterSprite.getTop();
                characterSprite.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        player.setX(characterX);
        player.setY(characterY);
    }
    // for testing purposes ONLY, repl w real coordinates later
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_W:
                // Move character up
                characterX -= 10;
                break;
            case KeyEvent.KEYCODE_A:
                // Move character left
                characterX -= 10;
                break;
            case KeyEvent.KEYCODE_S:
                // Move character down
                characterY += 10;
                break;
            case KeyEvent.KEYCODE_D:
                // Move character right
                characterX += 10;
                break;
        }

        // Update the character's position
        characterSprite.setX(characterX);
        characterSprite.setY(characterY);

        return true;
    }
}