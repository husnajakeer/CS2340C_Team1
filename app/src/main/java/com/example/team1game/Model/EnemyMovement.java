package com.example.team1game.Model;


import android.graphics.Rect;

import java.util.Random;

public class EnemyMovement implements Movement{
    private Enemy enemy;
    private boolean canMoveLeft = true;
    private boolean canMoveRight = true;
    private boolean canMoveUp = true;
    private boolean canMoveDown = true;
    private int screenWidth;
    private int screenHeight;
    private int spriteWidth;
    private int spriteHeight;
    private Random random = new Random();
    public void moveRandomly() {
        int randomDirection = random.nextInt(4); // 0: Left, 1: Right, 2: Up, 3: Down

        switch (randomDirection) {
            case 0:
                moveLeft();
                break;
            case 1:
                moveRight();
                break;
            case 2:
                moveUp();
                break;
            case 3:
                moveDown();
                break;
        }
    }
    public void moveLinearly() {
        int currentY = enemy.getY();
        int movementSpeed = enemy.getMovementSpeed();

        if (canMoveUp && currentY - movementSpeed >= 0) {
            moveUp();
        } else {
            canMoveUp = false;
            canMoveDown = true;
            moveDown();
        }
    }
    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }
}
