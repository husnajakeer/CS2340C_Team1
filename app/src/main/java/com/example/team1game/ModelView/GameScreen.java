package com.example.team1game.ModelView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.team1game.Model.Player;
import com.example.team1game.Model.PlayerMovement;
import com.example.team1game.R;
import com.example.team1game.View.Room2;
import com.example.team1game.View.Room3;

public class GameScreen extends AppCompatActivity {
    private Player player;
    private PlayerMovement playerMovement;
    private ImageView characterSprite;
    private Handler scoreHandler = new Handler();
    private Handler movementHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        initializeGame();
        setupScoreUpdater();
        initializePlayerMovementControls();
        detectPlayerInitialPos();

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(view -> goToRoom2());
    }

    private void initializeGame() {
        player = Player.getPlayer();
        player.setScore(100);

        characterSprite = findViewById(R.id.characterSprite);
        setupUIElements();
    }

    private void setupUIElements() {
        TextView playerNameTextView = findViewById(R.id.playerNameTextView);
        TextView healthPointsTextView = findViewById(R.id.healthPointsTextView);
        TextView difficultyTextView = findViewById(R.id.difficultyTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);

        String playerName = player.getName();
        String difficulty = player.getDifficulty();
        String sprite = getIntent().getStringExtra("sprite");

        int numOfHearts = determineNumberOfHearts(difficulty);

        playerNameTextView.setText("Name: " + playerName);
        healthPointsTextView.setText("Health: " + numOfHearts + " hearts");
        difficultyTextView.setText("Difficulty: " + difficulty);
        setCharacterSprite(sprite);
    }

    private int determineNumberOfHearts(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return 5;
            case "Medium":
                return 3;
            case "Hard":
                return 1;
            default:
                return 0;
        }
    }

    private void setCharacterSprite(String sprite) {
        if ("eva_idle".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.eva_idle);
        } else if ("kaya_idle".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.kaya_idle);
        } else if ("rika_idle".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.rika_idle);
        }
    }

    private void setupScoreUpdater() {
        scoreHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView scoreTextView = findViewById(R.id.scoreTextView);
                if (player.getScore() > 0) {
                    player.setScore(player.getScore() - 1);
                    scoreTextView.setText("Score: " + player.getScore());
                    scoreHandler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void initializePlayerMovementControls() {
        Button upButton = findViewById(R.id.upButton);
        Button downButton = findViewById(R.id.downButton);
        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);

        upButton.setOnTouchListener((view, motionEvent) -> handleTouch(motionEvent, () -> playerMovement.moveUp()));
        downButton.setOnTouchListener((view, motionEvent) -> handleTouch(motionEvent, () -> playerMovement.moveDown()));
        leftButton.setOnTouchListener((view, motionEvent) -> handleTouch(motionEvent, () -> playerMovement.moveLeft()));
        rightButton.setOnTouchListener((view, motionEvent) -> handleTouch(motionEvent, () -> playerMovement.moveRight()));
    }

    private boolean handleTouch(MotionEvent motionEvent, Runnable movementMethod) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startContinuousMovement(movementMethod);
                break;
            case MotionEvent.ACTION_UP:
                stopContinuousMovement();
                break;
        }
        return true;
    }

    private void startContinuousMovement(Runnable movementMethod) {
        movementHandler.removeCallbacksAndMessages(null);
        movementHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                movementMethod.run();
                updateCharacterPosition();
                movementHandler.postDelayed(this, 50); // Adjust this delay for movement speed
            }
        }, 0);
    }

    private void stopContinuousMovement() {
        movementHandler.removeCallbacksAndMessages(null);
    }

    private void updateCharacterPosition() {
        characterSprite.setX(player.getX());
        characterSprite.setY(player.getY());
        checkCharacterPosition();
    }

    private void detectPlayerInitialPos() {
        ViewTreeObserver viewTreeObserver = characterSprite.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                player.setX(characterSprite.getLeft());
                player.setY(characterSprite.getTop());
                characterSprite.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int spriteWidth = characterSprite.getWidth();
                int spriteHeight = characterSprite.getHeight();
                int screenWidth = findViewById(android.R.id.content).getWidth();
                int screenHeight = findViewById(android.R.id.content).getHeight();

                playerMovement = new PlayerMovement(screenWidth, screenHeight, spriteWidth, spriteHeight);
            }
        });
    }

    private void checkCharacterPosition() {
        if (player.getX() == 0 && player.getY() == findViewById(android.R.id.content).getHeight() - characterSprite.getHeight()) {
            String sprite = getIntent().getStringExtra("sprite");
            Intent intent = new Intent(GameScreen.this, Room2.class);
            intent.putExtra("sprite", sprite);
            startActivity(intent);
        }
    }
    private void goToRoom2() {
        String sprite = getIntent().getStringExtra("sprite");
        Intent intent = new Intent(GameScreen.this, Room2.class);
        intent.putExtra("sprite", sprite);
        startActivity(intent);
    }
}