package com.example.team1game.Model;

//import android.graphics.Rect;

import java.util.Random;

/**
 * The EnemyMovement class provides methods for controlling
 * the movement of an enemy character within a game.
 */
public class EnemyMovement implements Movement {
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

    /**
     * Constructs an EnemyMovement object for a specific enemy character.
     *
     * @param enemy The enemy character for which movement is controlled.
     */
    public EnemyMovement(Enemy enemy) {
        this.enemy = enemy;
        this.screenWidth = 1080;
        this.screenHeight = 2160;
        this.spriteWidth = 32;
        this.spriteHeight = 60;
    }

    /**
     * Moves the enemy character in a random direction.
     */
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
        default:
            break;
        }
        //System.out.println(enemy.getX() + " " + enemy.getY());
    }

    /**
     * Moves the enemy character linearly, switching direction when
     * reaching the screen's boundaries.
     */
    public void moveLinearly() {
        int currentY = enemy.getY();
        // Implement movement speed later
        int movementSpeed = enemy.getMovementSpeed();

        if (currentY <= 50) {
            // At the top of the screen, switch to move downwards
            canMoveUp = false;
            canMoveDown = true;
            moveDown();
        } else if (currentY >= screenHeight - 800) {
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
            x = Math.max(x - 10, 50);
            enemy.setX(x);
        }
    }

    /**
     * Moves the enemy character to the right.
     */
    @Override
    public void moveRight() {
        if (canMoveRight) {
            int x = enemy.getX();
            x = Math.min(x + 10, screenWidth - spriteWidth - 50);
            enemy.setX(x);
        }
    }

    /**
     * Moves the enemy character upwards.
     */
    @Override
    public void moveUp() {
        if (canMoveUp) {
            int y = enemy.getY();
            y = Math.max(y - 10, 10);
            enemy.setY(y);
        }
    }

    /**
     * Moves the enemy character downwards.
     */
    @Override
    public void moveDown() {
        if (canMoveDown) {
            int y = enemy.getY();
            y = Math.min(y + 10, screenHeight - spriteHeight - 50);
            enemy.setY(y);
        }
    }
}
