package com.example.team1game.Model;

import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScreen extends AppCompatActivity {
    protected Player player;
    protected PlayerMovement playerMovement;
    protected ImageView characterSprite;

    protected List<Enemy> enemies;
    protected List<ImageView> enemyViews;
    protected Runnable healthReductionRunnable;
    protected boolean isPlayerInContactWithEnemy = false;

    protected int numOfHearts = -1;
    protected int healthDecrease = 1;

    protected Handler scoreHandler = new Handler();
    protected Handler movementHandler = new Handler();
    protected Handler obstacleHandler = new Handler();
    protected Handler enemyMovementHandler = new Handler();
    protected Handler healthReductionHandler = new Handler();
    protected static final int ENEMY_MOVEMENT_INTERVAL = 30;

    protected ArrayList<View> obstacles;
    protected boolean isTransitioning = false;

    protected boolean gameLost = false;

    protected abstract void initializeGame();
    protected abstract void setUpEnemies();
    protected void setupUIElements() {
        TextView playerNameTextView = findViewById(R.id.playerNameTextView);
        TextView healthPointsTextView = findViewById(R.id.healthPointsTextView);
        TextView difficultyTextView = findViewById(R.id.difficultyTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);

        String playerName = player.getName();
        String difficulty = player.getDifficulty();
        String sprite = getIntent().getStringExtra("sprite");

        // if num of hearts hasn't been set before, then
        if (numOfHearts == -1) {
            numOfHearts = determineNumberOfHearts(difficulty);
            healthDecrease = determineHealthDecrease(difficulty);
        }

        playerNameTextView.setText("Name: " + playerName);
        healthPointsTextView.setText("Health: " + numOfHearts + " hearts");
        difficultyTextView.setText("Difficulty: " + difficulty);
        setCharacterSprite(sprite);
    }
    protected int determineNumberOfHearts(String difficulty) {
        switch (difficulty) {
        case "Easy":
            return 50;
        case "Medium":
            return 30;
        case "Hard":
            return 20;
        default:
            return 0;
        }
    }

    // determines by how much health will be decresed when collision occurs
    protected int determineHealthDecrease(String difficulty) {
        switch (difficulty) {
        case "Easy":
            return 1;
        case "Medium":
            return 3;
        case "Hard":
            return 5;
        default:
            return 0;
        }
    }

    protected void setCharacterSprite(String sprite) {
        if ("eva_idle".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.eva_idle);
        } else if ("kaya_idle".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.kaya_idle);
        } else if ("rika_idle".equals(sprite)) {
            characterSprite.setImageResource(R.drawable.rika_idle);
        }
    }

    protected abstract void setupScoreUpdater();

    protected void initializePlayerMovementControls() {
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

    protected boolean handleTouch(MotionEvent motionEvent, Runnable movementMethod) {
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

    protected void startContinuousMovement(Runnable movementMethod) {
        movementHandler.removeCallbacksAndMessages(null);
        movementHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                movementMethod.run();
                updateCharacterPosition();
                movementHandler.postDelayed(this, 50);
            }
        }, 0);
    }

    protected void stopContinuousMovement() {
        movementHandler.removeCallbacksAndMessages(null);
    }

    protected void updateCharacterPosition() {
        if (isTransitioning) {
            return;
        }
        characterSprite.setX(player.getX());
        characterSprite.setY(player.getY());
        checkPlayerOnExit();
        checkCollisionWithEnemies();
    }

    protected void checkCollisionWithEnemies() {
        TextView healthPointsTextView = findViewById(R.id.healthPointsTextView);
        Rect playerRect = new Rect();
        characterSprite.getHitRect(playerRect);

        boolean isCurrentlyInContact = false;
        int buffer = 80;
        for (ImageView enemyView : enemyViews) {
            Rect enemyRect = getAdjustedEnemyRect(enemyView, buffer);

            if (Enemy.update(playerRect, enemyRect)) {
                isCurrentlyInContact = true;
                if (!isPlayerInContactWithEnemy) {
                    isPlayerInContactWithEnemy = true;
                    startHealthReductionTimer(healthPointsTextView);
                }
                break;
            }
        }

        if (isPlayerInContactWithEnemy && !isCurrentlyInContact) {
            isPlayerInContactWithEnemy = false;
            stopHealthReductionTimer();
        }
    }

    protected void startHealthReductionTimer(TextView healthPointsTextView) {
        if (healthReductionRunnable != null) {
            healthReductionHandler.removeCallbacks(healthReductionRunnable);
        }
        healthReductionRunnable = new Runnable() {
            @Override
            public void run() {
                if (numOfHearts > 0) {
                    numOfHearts -= healthDecrease;
                    healthPointsTextView.setText("Health: " + numOfHearts + " hearts");
                    if (numOfHearts <= 0) {
                        // Stop the game or transition to game over screen
                        gameLost = true;
                        finishGame();
                    }
                    healthReductionHandler.postDelayed(this, 1000);
                }
            }
        };
        healthReductionHandler.postDelayed(healthReductionRunnable, 500);
    }

    protected void stopHealthReductionTimer() {
        if (healthReductionRunnable != null) {
            healthReductionHandler.removeCallbacks(healthReductionRunnable);
        }
    }

    protected Rect getAdjustedEnemyRect(ImageView enemyView, int buffer) {
        Rect originalRect = new Rect();
        enemyView.getHitRect(originalRect);
        originalRect.left += buffer;
        originalRect.top += buffer;
        originalRect.right -= buffer;
        originalRect.bottom -= buffer;
        return originalRect;
    }


    protected void detectPlayerInitialPos() {
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

    protected abstract void checkPlayerOnExit();

    protected void detectAllObstacles() {
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
    protected void moveEnemies() {
        // first half move randomly
        for (int i = 0; i < enemies.size() / 2; i++) {
            Enemy enemy = enemies.get(i);

            // Update the enemy's position
            enemy.getEnemyMovement().moveRandomly();
            enemy.setX(enemy.getX());
            enemy.setY(enemy.getY());

            checkCollisionWithEnemies();

            enemyViews.get(i).setX(enemy.getX());
            enemyViews.get(i).setY(enemy.getY());
            //System.out.println(enemy.getX() + "" + enemy.getY());
        }
        // 2nd half move linearly (be careful of starting arr size)
        for (int i = enemies.size() / 2; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);

            // Update the enemy's position
            enemy.getEnemyMovement().moveLinearly();
            enemy.setX(enemy.getX());
            enemy.setY(enemy.getY());

            checkCollisionWithEnemies();

            enemyViews.get(i).setX(enemy.getX());
            enemyViews.get(i).setY(enemy.getY());
            //System.out.println(enemy.getX() + "" + enemy.getY());
        }


    }
    protected void startEnemyMovementTimer() {
        enemyMovementHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveEnemies();
                enemyMovementHandler.postDelayed(this, ENEMY_MOVEMENT_INTERVAL);
            }
        }, ENEMY_MOVEMENT_INTERVAL);
    }
    protected void stopEnemyMovementTimer() {
        enemyMovementHandler.removeCallbacksAndMessages(null);
    }
    protected void finishGame() {
        String playerName = player.getName();
        String difficulty = player.getDifficulty();
        Leaderboard.getInstance();
        Attempt attempt = new Attempt(playerName, player.getScore(), difficulty);
        Leaderboard.getInstance().addAttempt(attempt);
        //goToEndScreen();
    }


}
