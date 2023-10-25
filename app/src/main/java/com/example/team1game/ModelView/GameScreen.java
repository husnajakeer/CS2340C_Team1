package com.example.team1game.ModelView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.Model.Enemy;
import com.example.team1game.Model.EnemyFactory;
import com.example.team1game.Model.GameLoop;
import com.example.team1game.Model.Player;
import com.example.team1game.Model.PlayerMovement;
import com.example.team1game.R;
import com.example.team1game.View.Room2;
import com.google.android.gms.games.Game;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends AppCompatActivity {
    private Player player;
    private PlayerMovement playerMovement;
    private ImageView characterSprite;
    private List<Enemy> enemies;
    private List<ImageView> enemyViews;
    private GameLoop gameLoop;

    private Handler scoreHandler = new Handler();
    private Handler movementHandler = new Handler();
    private Handler obstacleHandler = new Handler();
    private Handler enemyMovementHandler = new Handler();
    private static final int ENEMY_MOVEMENT_INTERVAL = 30;

    private ArrayList<View> obstacles;
    private boolean isTransitioning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        initializeGame();
        setupScoreUpdater();
        initializePlayerMovementControls();
        detectPlayerInitialPos();
        startEnemyMovementTimer();

    }

    private void initializeGame() {
        player = Player.getPlayer();
        player.setScore(100);

        characterSprite = findViewById(R.id.characterSprite);
        enemies = new ArrayList<>();
        enemyViews = new ArrayList<>();

        // Create a fast enemy and set its sprite
        Enemy fastEnemy = EnemyFactory.createFastEnemy("FastEnemy", 100, 10, 20);
        ImageView fastEnemySprite = findViewById(R.id.FastEnemy);
        enemies.add(fastEnemy);
        enemyViews.add(fastEnemySprite);

        /*// Create a slow enemy and set its sprite
        Enemy slowEnemy = EnemyFactory.createSlowEnemy("SlowEnemy", 150, 5, 5);
        //slowEnemy.setSprite(R.drawable.slow_enemy_sprite); // Set the appropriate sprite resource
        enemies.add(slowEnemy);

        // Create a small enemy and set its sprite
        Enemy smallEnemy = EnemyFactory.createSmallEnemy("SmallEnemy", 75, 15, 10);
        //smallEnemy.setSprite(R.drawable.small_enemy_sprite); // Set the appropriate sprite resource
        enemies.add(smallEnemy);

        // Create a big enemy and set its sprite
        Enemy bigEnemy = EnemyFactory.createBigEnemy("BigEnemy", 200, 20, 15);
        //bigEnemy.setSprite(R.drawable.big_enemy_sprite); // Set the appropriate sprite resource
        enemies.add(bigEnemy);*/
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

        upButton.setOnTouchListener((view, motionEvent) ->
                handleTouch(motionEvent, () -> playerMovement.moveUp()));
        downButton.setOnTouchListener((view, motionEvent) ->
                handleTouch(motionEvent, () -> playerMovement.moveDown()));
        leftButton.setOnTouchListener((view, motionEvent) ->
                handleTouch(motionEvent, () -> playerMovement.moveLeft()));
        rightButton.setOnTouchListener((view, motionEvent) ->
                handleTouch(motionEvent, () -> playerMovement.moveRight()));
    }

    private boolean handleTouch(MotionEvent motionEvent, Runnable movementMethod) {
        switch (motionEvent.getAction()) {
        case MotionEvent.ACTION_DOWN:
            startContinuousMovement(movementMethod);
            break;
        case MotionEvent.ACTION_UP:
            stopContinuousMovement();
            break;
        default:
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
        if (isTransitioning) {
            return;
        }
        characterSprite.setX(player.getX());
        characterSprite.setY(player.getY());
        checkPlayerOnExit();
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
                int screenHeight = findViewById(android.R.id.content).
                        getHeight();

                playerMovement = new PlayerMovement(screenWidth, screenHeight,
                        spriteWidth, spriteHeight);
                detectAllObstacles();
            }
        });
    }

    private void checkPlayerOnExit() {
        TextView exitArea = findViewById(R.id.exitArea);

        int[] exitLocation = new int[2];
        exitArea.getLocationOnScreen(exitLocation);

        int exitX = exitLocation[0];
        int exitY = exitLocation[1];
        int exitWidth = exitArea.getWidth();
        int exitHeight = exitArea.getHeight();

        int playerX = (int) characterSprite.getX();
        int playerY = (int) characterSprite.getY();
        int playerWidth = characterSprite.getWidth();
        int playerHeight = characterSprite.getHeight();

        boolean overlap = playerX + playerWidth > exitX
                && playerX < exitX + exitWidth
                && playerY + playerHeight > exitY
                && playerY < exitY + exitHeight;

        if (overlap) {
            if (isTransitioning) {
                return;
            }
            isTransitioning = true;
            goToRoom2();
        }

    }

    private void detectAllObstacles() {
        obstacles = new ArrayList<>();
        obstacles.add(findViewById(R.id.obstacleView1));
        obstacles.add(findViewById(R.id.obstacleView2));

        obstacleHandler = new Handler();
        Runnable collisionCheckRunnable = new Runnable() {
            @Override
            public void run() {
                Rect playerRect = new Rect();
                characterSprite.getHitRect(playerRect);

                for (View obstacle : obstacles) {
                    Rect obstacleRect = new Rect();
                    obstacle.getHitRect(obstacleRect);

                    playerMovement.handleMovementFlags(obstacleRect,
                            playerRect); // Update movement flags
                    playerMovement.handleCollision(obstacleRect, playerRect); // Handle collisions
                }

                obstacleHandler.postDelayed(this, 16); // Check for collisions every 16 milliseconds
            }
        };
        obstacleHandler.post(collisionCheckRunnable);
    }
    private void moveEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);

            // Calculate random position changes for each enemy
            int deltaX = getRandomDelta();
            int deltaY = getRandomDelta();

            // Update the enemy's position
            enemy.setX(enemy.getX() + deltaX);
            enemy.setY(enemy.getY() + deltaY);
            enemyViews.get(i).setX(enemy.getX() + deltaX);
            enemyViews.get(i).setY(enemy.getY() + deltaY);
            System.out.println(enemy.getX() + "" + enemy.getY());
        }

    }
    private void startEnemyMovementTimer() {
        enemyMovementHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveEnemies();
                enemyMovementHandler.postDelayed(this, ENEMY_MOVEMENT_INTERVAL);
            }
        }, ENEMY_MOVEMENT_INTERVAL);
    }
    private void stopEnemyMovementTimer() {
        enemyMovementHandler.removeCallbacksAndMessages(null);
    }

    private int getRandomDelta() {
        // Generate a random delta value within a reasonable range
        return (int) (Math.random() * 20 - 10); // Adjust the range as needed
    }

    private void goToRoom2() {
        String sprite = getIntent().getStringExtra("sprite");
        Intent intent = new Intent(GameScreen.this, Room2.class);
        intent.putExtra("sprite", sprite);
        intent.putExtra("endingScore", player.getScore());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGame();
    }

    private void pauseGame() {
        scoreHandler.removeCallbacksAndMessages(null);
        movementHandler.removeCallbacksAndMessages(null);
        obstacleHandler.removeCallbacksAndMessages(null);

    }


}