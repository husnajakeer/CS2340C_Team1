package com.example.team1game.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.team1game.Model.BaseScreen;
import com.example.team1game.Model.BigEnemy;
import com.example.team1game.Model.Enemy;
import com.example.team1game.Model.FastEnemy;
import com.example.team1game.Model.Player;
import com.example.team1game.Model.SlowEnemy;
import com.example.team1game.Model.SmallEnemy;
import com.example.team1game.R;

import java.util.ArrayList;
import java.util.List;

public class Room2 extends BaseScreen {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room2_screen);

        initializeGame();
        setupScoreUpdater();
        initializePlayerMovementControls();
        detectPlayerInitialPos();
        startEnemyMovementTimer();
    }
    protected void initializeGame(){
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
    @Override
    protected void setupScoreUpdater() {
        scoreHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView scoreTextView = findViewById(R.id.scoreTextView);
                if (player.getScore() == 0) {
                    gameLost = true;
                    goToRoom3();
                }
                if (player.getScore() > 0) {
                    player.setScore(player.getScore() - 1);
                    scoreTextView.setText("Score: " + player.getScore());
                    scoreHandler.postDelayed(this, 1000);
                }
            }
        }, 1000);
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
            goToRoom3();
        }

    }
    private void goToRoom3() {
        System.out.println(gameLost + "gameLost");
        if (!gameLost) {
            String sprite = getIntent().getStringExtra("sprite");
            Intent intent = new Intent(Room2.this, Room3.class);
            intent.putExtra("sprite", sprite);
            intent.putExtra("endingScore", player.getScore());
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(Room2.this, LoseScreen.class);
            startActivity(intent);
        }
    }
}