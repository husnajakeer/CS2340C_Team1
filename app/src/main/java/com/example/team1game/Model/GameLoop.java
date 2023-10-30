package com.example.team1game.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.team1game.Model.Enemy;
import com.example.team1game.Model.EnemyFactory;

public class GameLoop extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isPlaying = true;

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private long startTime;
    private long frameTime;
    private Enemy[] enemies; // Array to store enemy objects
    private EnemyMovement enemyMovement;

    public GameLoop(Context context) {
        super(context);
        surfaceHolder = getHolder();
        enemies = new Enemy[4]; // Initialize the array with 4 enemies
        enemies[0] = EnemyFactory.createFastEnemy("FastEnemy", 100, 10, 20);
        enemies[1] = EnemyFactory.createSlowEnemy("SlowEnemy", 150, 5, 5);
        enemies[2] = EnemyFactory.createSmallEnemy("SmallEnemy", 75, 15, 10);
        enemies[3] = EnemyFactory.createBigEnemy("BigEnemy", 200, 20, 15);
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        // Simulate enemy movement and update game logic here
        for (Enemy enemy : enemies) {
            // Update the enemy's position based on its movement speed
            enemyMovement.moveRandomly(); // Example of random movement, you can change this as needed

            // Implement collision detection and other game logic
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            // Draw game elements on the canvas
            // Use canvas.draw... methods to draw your game objects
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        // Control the frame rate of the game loop
        frameTime = System.currentTimeMillis() - startTime;
        long sleepTime = 16 - frameTime; // Aim for 60 FPS

        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
