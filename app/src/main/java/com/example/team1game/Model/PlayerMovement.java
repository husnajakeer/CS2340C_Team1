package com.example.team1game.Model;

import android.graphics.Rect;

public class PlayerMovement implements Movement, Subscriber {
    Player player;
    private boolean canMoveLeft = true;
    private boolean canMoveRight = true;
    private boolean canMoveUp = true;
    private boolean canMoveDown = true;

    private static volatile  PlayerMovement playerMovement;
    int screenWidth, screenHeight, spriteWidth, spriteHeight;

    public PlayerMovement(int screenWidth, int screenHeight, int spriteWidth, int spriteHeight) {
        this.player = Player.getPlayer();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    public PlayerMovement() {
        this.player = Player.getPlayer();
        this.screenWidth = 1080;
        this.screenHeight = 2160;
        this.spriteWidth = 32;
        this.spriteHeight = 32;
    }

    public static PlayerMovement getPlayerMovement() {
        if (playerMovement == null) {
            synchronized (Player.class) {
                if (playerMovement == null) {
                    playerMovement = new PlayerMovement();
                }
            }
        }
        return playerMovement;
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
                    player.setY(obstacleTop - spriteHeight);
                } else {
                    player.setY(obstacleBottom);
                }
            }
        } else if (playerRight > obstacleLeft && playerLeft < obstacleRight) {
            if (playerBottom > obstacleTop && playerTop < obstacleBottom) {
                if (playerRight - obstacleLeft < obstacleRight - playerLeft) {
                    player.setX(obstacleLeft - spriteWidth);
                } else {
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

        if (playerBottom == obstacleTop && playerLeft <= obstacleRight) {
            setCanMoveDown(false);
        }
        if (playerTop == obstacleBottom && playerRight >= obstacleLeft) {
            setCanMoveUp(false);
        }
        if (playerRight == obstacleLeft) {
            setCanMoveRight(false);
        }
        if (playerLeft == obstacleRight && playerTop >= obstacleBottom) {
            setCanMoveLeft(false);
        }
    }

    public boolean isPlayerOnExit(Rect playerRect, Rect exitRect) {
        return playerRect.bottom >= exitRect.top &&
                playerRect.left < exitRect.right &&
                playerRect.right > exitRect.left;
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
