package com.example.team1game.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team1game.Model.Attempt;
import com.example.team1game.Model.BaseScreen;
import com.example.team1game.Model.Leaderboard;

import com.example.team1game.Model.Enemy.BigEnemy;
import com.example.team1game.Model.Enemy.Enemy;
import com.example.team1game.Model.Enemy.FastEnemy;
import com.example.team1game.Model.Player;
import com.example.team1game.Model.Enemy.SlowEnemy;
import com.example.team1game.Model.Enemy.SmallEnemy;
import com.example.team1game.R;

import java.util.ArrayList;
import java.util.HashMap;
//TODO make this file easier to read, not so big

public class Room3 extends BaseScreen {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room3_screen);

        initializeGame();
        setupTimeUpdater();
        initializePlayerMovementControls();
        detectPlayerInitialPos();
        startEnemyMovementTimer();
    }


    protected void initializeGame() {
        Intent intent = getIntent();
        player = Player.getPlayer();
        player.setScore(intent.getIntExtra("endingScore", 0));
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + player.getScore());

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
                    goToEndScreen();
                }
                */
                if (player.getScore() > 0) {
                    player.setScore(player.getScore() - 1);
                    timeTextView.setText("Time: " + player.getScore());
                    timeHandler.postDelayed(this, 1000);
                }
                if (score >= 0) {
                    scoreTextView.setText("Score " + score);
                }
            }
        }, 1000);
    }

    /*
    protected void setupScoreUpdater() {
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        if (score >= 0) {
            scoreTextView.setText("Score " + score);
        }
    }
    */

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
            finishGame();
        }

    }
    @Override
    protected void finishGame() {
        String playerName = player.getName();
        String difficulty = player.getDifficulty();
        Leaderboard.getInstance();
        Attempt attempt = new Attempt(playerName, player.getScore(), difficulty);
        Leaderboard.getInstance().addAttempt(attempt);
        goToEndScreen();
    }

    private void goToEndScreen() {
        if (!gameLost) {
            Intent intent = new Intent(Room3.this, EndScreen.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(Room3.this, LoseScreen.class);
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
        timeHandler.removeCallbacksAndMessages(null);
        movementHandler.removeCallbacksAndMessages(null);
        obstacleHandler.removeCallbacksAndMessages(null);

    }
}