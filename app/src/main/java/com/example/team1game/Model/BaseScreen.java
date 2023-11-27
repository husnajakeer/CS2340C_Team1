package com.example.team1game.Model;

import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team1game.Model.Enemy.Enemy;
import com.example.team1game.Model.Powerups.AttackPowerUpDecorator;
import com.example.team1game.Model.Powerups.HealthPowerUpDecorator;
import com.example.team1game.Model.Powerups.TimePowerUpDecorator;
import com.example.team1game.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public abstract class BaseScreen extends AppCompatActivity {
    protected Player player;
    protected Weapons sword;
    protected PlayerMovement playerMovement;
    protected ImageView characterSprite;
    protected ImageView playerSwordSprite;
    protected Map<Enemy, ImageView> enemyImageViewMap;
    protected Runnable healthReductionRunnable;
    protected boolean isPlayerInContactWithEnemy = false;

    protected static int score = 0;
    protected static int numOfHearts = -1;
    protected int healthDecrease = 1;
    protected Handler timeHandler = new Handler();
    protected Handler movementHandler = new Handler();
    protected Handler obstacleHandler = new Handler();
    protected Handler enemyMovementHandler = new Handler();
    protected Handler healthReductionHandler = new Handler();
    protected static final int ENEMY_MOVEMENT_INTERVAL = 30;

    protected ArrayList<View> obstacles;
    protected boolean isTransitioning = false;

    protected boolean gameLost = false;

    protected static int scoreMultiplier = 20;

    protected abstract void initializeGame();
    protected abstract void setUpEnemies();
    protected void setupUIElements() {
        TextView playerNameTextView = findViewById(R.id.playerNameTextView);
        TextView healthPointsTextView = findViewById(R.id.healthPointsTextView);
        TextView difficultyTextView = findViewById(R.id.difficultyTextView);

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
        setWeaponSprite();
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
    protected void setWeaponSprite(){
        playerSwordSprite = findViewById(R.id.swordSprite); // Replace with your ImageView ID
        sword = new Weapons("Sword", 10, playerSwordSprite);
        player.setWeapon(sword);
    }

    protected abstract void setupTimeUpdater();
    //protected abstract void setupScoreUpdater();

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
                player.setWeaponPosition();
                playerSwordSprite.setX(player.getX());
                playerSwordSprite.setY(player.getY());
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
        //checkCollisionWithEnemies();
    }

    protected void checkCollisionWithEnemies() {
        TextView healthPointsTextView = findViewById(R.id.healthPointsTextView);
        Rect playerRect = new Rect();
        characterSprite.getHitRect(playerRect);

        boolean isCurrentlyInContact = false;
        int buffer = 80;

        for (ImageView enemyView : enemyImageViewMap.values()) {
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
        for (Map.Entry<Enemy, ImageView> entry : enemyImageViewMap.entrySet()) {
            Enemy enemy = entry.getKey();
            ImageView enemyView = entry.getValue();

            if (enemy.getMovementType().equals("random")) {
                enemy.getEnemyMovement().moveRandomly();
            } else if (enemy.getMovementType().equals("linear")){
                enemy.getEnemyMovement().moveLinearly();
            } else {
                System.out.print("no movement type so just move randomly");
                enemy.getEnemyMovement().moveRandomly();
            }
            enemy.setX(enemy.getX());
            enemy.setY(enemy.getY());

            enemyView.setX(enemy.getX());
            enemyView.setY(enemy.getY());
            checkCollisionWithEnemies();
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
    public void playerSwordAttack(View view) {
        //playerSwordSprite.setVisibility(View.INVISIBLE);
        player.setWeaponPosition();
        playerSwordSprite.setX(player.getX());
        playerSwordSprite.setY(player.getY());

        // Set the background resource of the sword sprite to the sword swing animation
        playerSwordSprite.setBackgroundResource(R.drawable.sword_slash_anim);
        // Retrieve the animation drawable and start the animation
        AnimationDrawable swordAnimation = (AnimationDrawable) playerSwordSprite.getBackground();
        swordAnimation.start();

        // Stop the animation after 1 second
        new Handler().postDelayed(() -> {
            swordAnimation.stop();
            playerSwordSprite.setVisibility(View.VISIBLE); // Show the sword sprite again after 1 second
        }, 1000);
        enemyTakeDamage(sword.getSprite());
    }

    private void enemyTakeDamage(ImageView swordImageView) {
        Rect swordRect = new Rect();
        swordImageView.getHitRect(swordRect);

        Iterator<Map.Entry<Enemy, ImageView>> iterator = enemyImageViewMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Enemy, ImageView> entry = iterator.next();
            ImageView enemyView = entry.getValue();
            Rect enemyRect = new Rect();
            enemyView.getHitRect(enemyRect);

            if (Rect.intersects(swordRect, enemyRect)) {
                // Remove the ImageView from the layout
                ViewGroup viewGroup = (ViewGroup) enemyView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(enemyView);
                }

                // Remove the entry from the HashMap
                iterator.remove();

                // Perform any other necessary actions here
                // For example, update the enemy's status or initiate other logic for the damaged enemy
                score += scoreMultiplier;
            }
        }
    }

    protected boolean checkPowerupCollision(ImageView sprite1, ImageView sprite2) {
        int[] location1 = new int[2];
        sprite1.getLocationOnScreen(location1);
        Rect rect1 = new Rect(location1[0], location1[1],
                location1[0] + sprite1.getWidth(), location1[1] + sprite1.getHeight());

        int[] location2 = new int[2];
        sprite2.getLocationOnScreen(location2);
        Rect rect2 = new Rect(location2[0], location2[1],
                location2[0] + sprite2.getWidth(), location2[1] + sprite2.getHeight());

        return Rect.intersects(rect1, rect2);
    }

    public void showPowerupUsedMessage(String message, TextView powerupMessage) {
        powerupMessage.setText(message);
        powerupMessage.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> powerupMessage.setVisibility(View.GONE), 2000);
    }

    public void removePowerupView(ImageView powerup) {
        powerup.setVisibility(View.GONE);
        if (powerup.getParent() != null) {
            ((ViewGroup) powerup.getParent()).removeView(powerup);
        }
    }

    protected void finishGame() {
        String playerName = player.getName();
        String difficulty = player.getDifficulty();
        Leaderboard.getInstance();
        Attempt attempt = new Attempt(playerName, player.getScore(), difficulty);
        Leaderboard.getInstance().addAttempt(attempt);
        //goToEndScreen();
    }
    @Override
    protected void onPause() {
        super.onPause();
        pauseGame();
    }

    private void pauseGame() {
        timeHandler.removeCallbacksAndMessages(null);
        movementHandler.removeCallbacksAndMessages(null);
        obstacleHandler.removeCallbacksAndMessages(null);
        enemyMovementHandler.removeCallbacksAndMessages(null);
        healthReductionHandler.removeCallbacksAndMessages(null);
        stopHealthReductionTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlayerInContactWithEnemy) {
            startHealthReductionTimer((TextView) findViewById(R.id.healthPointsTextView));
        }
    }

    public static void setScoreMultiplier(int newScoreMultiplier) {
        scoreMultiplier = newScoreMultiplier;
    }

    public void setNumOfHearts(int newNumOfHearts) {
        TextView healthPointsTextView = findViewById(R.id.healthPointsTextView);

        numOfHearts = newNumOfHearts;
        healthPointsTextView.setText("Health: " + numOfHearts + " hearts");
    }

    public static int getScore() {
        return score;
    }

}
