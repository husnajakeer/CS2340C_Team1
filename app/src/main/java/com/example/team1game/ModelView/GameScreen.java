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

import com.example.team1game.Model.Attempt;
import com.example.team1game.Model.BigEnemy;
import com.example.team1game.Model.Enemy;
import com.example.team1game.Model.FastEnemy;
import com.example.team1game.Model.Leaderboard;
import com.example.team1game.Model.Player;
import com.example.team1game.Model.PlayerMovement;
import com.example.team1game.Model.SlowEnemy;
import com.example.team1game.Model.SmallEnemy;
import com.example.team1game.R;
import com.example.team1game.View.LoseScreen;
import com.example.team1game.View.Room2;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends AppCompatActivity {
    protected Player player;
    private PlayerMovement playerMovement;
    private ImageView characterSprite;
    protected List<Enemy> enemies;
    private List<ImageView> enemyViews;


    private Runnable healthReductionRunnable;
    private boolean isPlayerInContactWithEnemy = false;

    private int numOfHearts;
    private int healthDecrease = 1;

    private Handler scoreHandler = new Handler();
    private Handler movementHandler = new Handler();
    private Handler obstacleHandler = new Handler();
    private Handler enemyMovementHandler = new Handler();
    private Handler healthReductionHandler = new Handler();
    private static final int ENEMY_MOVEMENT_INTERVAL = 30;

    private ArrayList<View> obstacles;
    private boolean isTransitioning = false;

    private boolean gameLost = false;
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
        setUpEnemies();
        setupUIElements();
    }
    private void setUpEnemies() {
        FastEnemy fastEnemyFactory = new FastEnemy();
        SlowEnemy slowEnemyFactory = new SlowEnemy();
        BigEnemy bigEnemyFactory = new BigEnemy();
        SmallEnemy smallEnemyFactory = new SmallEnemy();
        enemies = new ArrayList<>();
        enemyViews = new ArrayList<>();

        // Create a fast enemy and set its sprite
        Enemy fastEnemy = fastEnemyFactory.createEnemy("FastEnemy", 100, 10, 20);
        ImageView fastEnemySprite = findViewById(R.id.fastEnemy);
        fastEnemy.setX(500);
        fastEnemy.setY(100);
        enemies.add(fastEnemy);
        enemyViews.add(fastEnemySprite);

        // Create a slow enemy and set its sprite
        Enemy slowEnemy = slowEnemyFactory.createEnemy("SlowEnemy", 150, 5, 5);
        ImageView slowEnemySprite = findViewById(R.id.slowEnemy);
        slowEnemy.setX(500);
        slowEnemy.setY(800);
        enemies.add(slowEnemy);
        enemyViews.add(slowEnemySprite);

        // Create a small enemy and set its sprite
        Enemy smallEnemy = smallEnemyFactory.createEnemy("SmallEnemy", 75, 15, 10);
        ImageView smallEnemySprite = findViewById(R.id.smallEnemy);
        smallEnemy.setX(800);
        smallEnemy.setY(800);
        enemies.add(smallEnemy);
        enemyViews.add(smallEnemySprite);

        // Create a big enemy and set its sprite
        Enemy bigEnemy = bigEnemyFactory.createEnemy("BigEnemy", 200, 20, 15);
        ImageView bigEnemySprite = findViewById(R.id.bigEnemy);
        bigEnemy.setX(700);
        bigEnemy.setY(700);
        enemies.add(bigEnemy);
        enemyViews.add(bigEnemySprite);
    }

    private void setupUIElements() {
        TextView playerNameTextView = findViewById(R.id.playerNameTextView);
        TextView healthPointsTextView = findViewById(R.id.healthPointsTextView);
        TextView difficultyTextView = findViewById(R.id.difficultyTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);

        String playerName = player.getName();
        String difficulty = player.getDifficulty();
        String sprite = getIntent().getStringExtra("sprite");

        numOfHearts = determineNumberOfHearts(difficulty);
        healthDecrease = determineHealthDecrease(difficulty);

        playerNameTextView.setText("Name: " + playerName);
        healthPointsTextView.setText("Health: " + numOfHearts + " hearts");
        difficultyTextView.setText("Difficulty: " + difficulty);
        setCharacterSprite(sprite);
    }

    private int determineNumberOfHearts(String difficulty) {
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
    private int determineHealthDecrease(String difficulty) {
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
                /*
                if (player.getScore() == 0) {
                    gameLost = true;
                    goToRoom2();
                }
                */
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
                movementHandler.postDelayed(this, 50);
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
        checkCollisionWithEnemies();
    }

    private void checkCollisionWithEnemies() {
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

    public void startHealthReductionTimer(TextView healthPointsTextView) {
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

    public void stopHealthReductionTimer() {
        if (healthReductionRunnable != null) {
            healthReductionHandler.removeCallbacks(healthReductionRunnable);
        }
    }

    private Rect getAdjustedEnemyRect(ImageView enemyView, int buffer) {
        Rect originalRect = new Rect();
        enemyView.getHitRect(originalRect);
        originalRect.left += buffer;
        originalRect.top += buffer;
        originalRect.right -= buffer;
        originalRect.bottom -= buffer;
        return originalRect;
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
        for (int i = enemies.size() / 2; i < enemies.size() ; i++) {
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

    private void finishGame() {
        String playerName = player.getName();
        String difficulty = player.getDifficulty();
        Leaderboard.getInstance();
        Attempt attempt = new Attempt(playerName, player.getScore(), difficulty);
        Leaderboard.getInstance().addAttempt(attempt);
        goToRoom2();
    }

    private void goToRoom2() {
        if (!gameLost) {
            String sprite = getIntent().getStringExtra("sprite");
            Intent intent = new Intent(GameScreen.this, Room2.class);
            intent.putExtra("sprite", sprite);
            intent.putExtra("endingScore", player.getScore());
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(GameScreen.this, LoseScreen.class);
            startActivity(intent);
            finish();
        }

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
        enemyMovementHandler.removeCallbacksAndMessages(null);

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



}