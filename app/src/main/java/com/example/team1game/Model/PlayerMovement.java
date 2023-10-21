package com.example.team1game.Model;

import android.graphics.Rect;

public class PlayerMovement implements Movement, Subscriber {
    Player player;
    private boolean canMoveLeft = true;
    private boolean canMoveRight = true;
    private boolean canMoveUp = true;
    private boolean canMoveDown = true;
    int screenWidth, screenHeight, spriteWidth, spriteHeight;

    public PlayerMovement(int screenWidth, int screenHeight, int spriteWidth, int spriteHeight) {
        this.player = Player.getPlayer();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    public void moveLeft() {
        if (canMoveLeft) {
            int x = player.getX();
            x = Math.max(x - 10, 0);
            player.setX(x);
        }
    }

    public void moveRight() {
        if (canMoveRight) {
            int x = player.getX();
            x = Math.min(x + 10, screenWidth - spriteWidth);
            player.setX(x);
        }
    }

    public void moveUp() {
        if (canMoveUp) {
            int y = player.getY();
            y = Math.max(y - 10, 0);
            player.setY(y);
        }
    }

    public void moveDown() {
        if (canMoveDown) {
            int y = player.getY();
            y = Math.min(y + 10, screenHeight - spriteHeight);
            player.setY(y);
        }
    }


    public void handleCollision(Rect obstacleRect, Rect playerRect) {
        // Calculate the sides of the player and obstacle
        int playerLeft = playerRect.left;
        int playerRight = playerRect.right;
        int playerTop = playerRect.top;
        int playerBottom = playerRect.bottom;

        int obstacleLeft = obstacleRect.left;
        int obstacleRight = obstacleRect.right;
        int obstacleTop = obstacleRect.top;
        int obstacleBottom = obstacleRect.bottom;

        // Check collision on each side and adjust position
        if (playerBottom > obstacleTop && playerTop < obstacleBottom) {
            if (playerRight > obstacleLeft && playerLeft < obstacleRight) {
                if (playerBottom - obstacleTop < obstacleBottom - playerTop) {
                    // Player is above the obstacle
                    player.setY(obstacleTop - spriteHeight);
                } else {
                    // Player is below the obstacle
                    player.setY(obstacleBottom);
                }
            }
        } else if (playerRight > obstacleLeft && playerLeft < obstacleRight) {
            if (playerBottom > obstacleTop && playerTop < obstacleBottom) {
                if (playerRight - obstacleLeft < obstacleRight - playerLeft) {
                    // Player is to the left of the obstacle
                    player.setX(obstacleLeft - spriteWidth);
                } else {
                    // Player is to the right of the obstacle
                    player.setX(obstacleRight);
                }
            }
        }
    }

    public void handleMovementFlags(Rect obstacleRect, Rect playerRect) {
        // Calculate the sides of the player and obstacle
        int playerLeft = playerRect.left;
        int playerRight = playerRect.right;
        int playerTop = playerRect.top;
        int playerBottom = playerRect.bottom;

        int obstacleLeft = obstacleRect.left;
        int obstacleRight = obstacleRect.right;
        int obstacleTop = obstacleRect.top;
        int obstacleBottom = obstacleRect.bottom;

        // Reset movement flags
        setCanMoveLeft(true);
        setCanMoveRight(true);
        setCanMoveUp(true);
        setCanMoveDown(true);

        // Check if player is touching the obstacle and adjust movement flags
        if (playerBottom == obstacleTop) {
            setCanMoveDown(false);
        }
        if (playerTop == obstacleBottom) {
            setCanMoveUp(false);
        }
        if (playerRight == obstacleLeft) {
            setCanMoveRight(false);
        }
        if (playerLeft == obstacleRight) {
            setCanMoveLeft(false);
        }
    }



    public boolean isCanMoveLeft() {
        return canMoveLeft;
    }

    public void setCanMoveLeft(boolean canMoveLeft) {
        this.canMoveLeft = canMoveLeft;
    }

    public boolean isCanMoveRight() {
        return canMoveRight;
    }

    public void setCanMoveRight(boolean canMoveRight) {
        this.canMoveRight = canMoveRight;
    }

    public boolean isCanMoveUp() {
        return canMoveUp;
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public boolean isCanMoveDown() {
        return canMoveDown;
    }

    public void setCanMoveDown(boolean canMoveDown) {
        this.canMoveDown = canMoveDown;
    }
}
