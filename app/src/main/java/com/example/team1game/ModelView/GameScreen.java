package com.example.team1game.ModelView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team1game.Model.Attempt;
import com.example.team1game.Model.BaseScreen;
import com.example.team1game.Model.Enemy.BigEnemy;
import com.example.team1game.Model.Enemy.Enemy;
import com.example.team1game.Model.Enemy.FastEnemy;
import com.example.team1game.Model.Leaderboard;
import com.example.team1game.Model.Player;
import com.example.team1game.Model.Enemy.SlowEnemy;
import com.example.team1game.Model.Enemy.SmallEnemy;
import com.example.team1game.Model.Powerups.AttackPowerUpDecorator;
import com.example.team1game.Model.Powerups.HealthPowerUpDecorator;
import com.example.team1game.Model.Powerups.TimePowerUpDecorator;
import com.example.team1game.R;
import com.example.team1game.View.LoseScreen;
import com.example.team1game.View.Room2;

import java.util.HashMap;

public class GameScreen extends BaseScreen {

    private Handler powerupHandler = new Handler();
    //Handler powerupHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        initializeGame();
        setupTimeUpdater();
        //setupScoreUpdater();
        initializePlayerMovementControls();
        detectPlayerInitialPos();
        startEnemyMovementTimer();
        startEnemyMovementTimer();
        setupPowerupCollisionDetection();
    }

    protected void initializeGame() {
        player = Player.getPlayer();
        // setScore() is actually setting the time here
        player.setScore(100);
        score = 0;
        characterSprite = findViewById(R.id.characterSprite);
        setUpEnemies();
        setupUIElements();
    }
    protected void setUpEnemies() {
        // declaring the factories
        FastEnemy fastEnemyFactory = new FastEnemy();
        SlowEnemy slowEnemyFactory = new SlowEnemy();
        BigEnemy bigEnemyFactory = new BigEnemy();
        SmallEnemy smallEnemyFactory = new SmallEnemy();

        enemyImageViewMap = new HashMap<>();

        // Create a fast enemy and set its sprite
        Enemy fastEnemy = fastEnemyFactory.createEnemy("FastEnemy", 100, 10, 20);
        ImageView fastEnemySprite = findViewById(R.id.fastEnemy);
        fastEnemy.setX(500);
        fastEnemy.setY(100);
        fastEnemy.setMovementType("random");
        enemyImageViewMap.put(fastEnemy, fastEnemySprite);


        // Create a slow enemy and set its sprite
        Enemy slowEnemy = slowEnemyFactory.createEnemy("SlowEnemy", 150, 5, 5);
        ImageView slowEnemySprite = findViewById(R.id.slowEnemy);
        slowEnemy.setX(500);
        slowEnemy.setY(800);
        slowEnemy.setMovementType("random");
        enemyImageViewMap.put(slowEnemy, slowEnemySprite);


        // Create a small enemy and set its sprite
        Enemy smallEnemy = smallEnemyFactory.createEnemy("SmallEnemy", 75, 15, 10);
        ImageView smallEnemySprite = findViewById(R.id.smallEnemy);
        smallEnemy.setX(800);
        smallEnemy.setY(800);
        smallEnemy.setMovementType("linear");
        enemyImageViewMap.put(smallEnemy, smallEnemySprite);

        // Create a big enemy and set its sprite
        Enemy bigEnemy = bigEnemyFactory.createEnemy("BigEnemy", 200, 20, 15);
        ImageView bigEnemySprite = findViewById(R.id.bigEnemy);
        bigEnemy.setX(700);
        bigEnemy.setY(700);
        bigEnemy.setMovementType("linear");
        enemyImageViewMap.put(bigEnemy, bigEnemySprite);
    }
    protected void setupTimeUpdater() {
        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView scoreTextView = findViewById(R.id.scoreTextView);
                TextView timeTextView = findViewById(R.id.timeTextView);
                /*
                if (player.getScore() == 0) {
                    gameLost = true;
                    goToRoom2();
                }
                */
                if (player.getScore() > 0) {
                    player.setScore(player.getScore() - 1);
                    timeTextView.setText("Time " + player.getScore());
                    timeHandler.postDelayed(this, 1000);
                }
                if (score >= 0) {
                    scoreTextView.setText("Score " + score);
                }
            }
        }, 1000);
    }

    protected void setupPowerupCollisionDetection() {
        final ImageView powerup1 = findViewById(R.id.powerup1);
        final ImageView powerup2 = findViewById(R.id.powerup2);
        final ImageView powerup3 = findViewById(R.id.powerup3);
        final TextView powerupMessage = findViewById(R.id.powerupMessage);
        powerupHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Attack Powerup
                if (checkPowerupCollision(characterSprite, powerup1)) {
                    AttackPowerUpDecorator powerOneDecorator = new
                            AttackPowerUpDecorator(scoreMultiplier);
                    powerOneDecorator.applyEffect(player);
                    showPowerupUsedMessage("Attack Powerup Used!", powerupMessage);
                    removePowerupView(powerup1);
                }

                // Time Powerup
                if (checkPowerupCollision(characterSprite, powerup2)) {
                    TimePowerUpDecorator powerTwoDecorator = new
                            TimePowerUpDecorator(player.getScore(), 20);
                    powerTwoDecorator.applyEffect(player);
                    showPowerupUsedMessage("Time Powerup Used!", powerupMessage);
                    removePowerupView(powerup2);
                }

                // Health Powerup
                if (checkPowerupCollision(characterSprite, powerup3)) {
                    HealthPowerUpDecorator powerThreeDecorator = new
                            HealthPowerUpDecorator(numOfHearts, GameScreen.this);
                    powerThreeDecorator.applyEffect(player);
                    showPowerupUsedMessage("Health Powerup Used!", powerupMessage);
                    removePowerupView(powerup3);
                }
                // Re-run this check every 100 milliseconds
                powerupHandler.postDelayed(this, 100);
            }
        }, 100);
    }

    protected void checkPlayerOnExit() {
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

    protected void finishGame() {
        String playerName = player.getName();
        String difficulty = player.getDifficulty();
        Leaderboard.getInstance();
        Attempt attempt = new Attempt(playerName, score, difficulty);
        Leaderboard.getInstance().addAttempt(attempt);
        goToRoom2();
    }

    private void pauseGame() {
        powerupHandler.removeCallbacksAndMessages(null);


    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGame();
    }

    private void goToRoom2() {
        if (!gameLost) {
            String sprite = getIntent().getStringExtra("sprite");
            Intent intent = new Intent(GameScreen.this, Room2.class);
            intent.putExtra("sprite", sprite);
            intent.putExtra("endingScore", player.getScore());
            intent.putExtra("score", score);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(GameScreen.this, LoseScreen.class);
            startActivity(intent);
            finish();
        }
    }
}