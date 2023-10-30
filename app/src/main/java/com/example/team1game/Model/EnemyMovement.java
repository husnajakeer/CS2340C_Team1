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
    public EnemyMovement(Enemy enemy){

        this.enemy = enemy;
        this.screenWidth = 1080;
        this.screenHeight = 2160;
        this.spriteWidth = 32;
        this.spriteHeight = 32;
    }
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
        System.out.println(enemy.getX() + " " + enemy.getY());
    }
    public void moveLinearly() {
        int currentY = enemy.getY();
        // implement movement speed later
        int movementSpeed = enemy.getMovementSpeed();

        if (currentY <= 0) {
            // At the top of the screen, switch to move downwards
            canMoveUp = false;
            canMoveDown = true;
            moveDown();
        } else if (currentY >= screenHeight - spriteHeight) {
            // At the bottom of the screen, switch to move upwards
            canMoveUp = true;
            canMoveDown = false;
            moveUp();
        } else {
            // Continue moving in the current direction
            if (canMoveUp) {
                moveUp();
            } else {
                moveDown();
            }
        }
    }

    @Override
    public void moveLeft() {
        if (canMoveLeft) {
            int x = enemy.getX();
            x = Math.max(x - 10, 0);
            enemy.setX(x);
        }
    }
    // TODO: implement these methods
    @Override
    public void moveRight() {
        if (canMoveRight) {
            int x = enemy.getX();
            x = Math.min(x + 10, screenWidth - spriteWidth);
            enemy.setX(x);
        }
    }
    @Override
    public void moveUp() {
        if (canMoveUp) {
            int y = enemy.getY();
            y = Math.max(y - 10, 0);
            enemy.setY(y);
        }
    }
    @Override
    public void moveDown() {
        if (canMoveDown) {
            int y = enemy.getY();
            y = Math.min(y + 10, screenHeight - spriteHeight);
            enemy.setY(y);
        }
    }
}
