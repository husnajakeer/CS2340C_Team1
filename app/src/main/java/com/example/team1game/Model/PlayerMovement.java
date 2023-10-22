package com.example.team1game.Model;
import android.view.WindowManager;
import android.util.DisplayMetrics;
import android.content.res.Resources;

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

    public PlayerMovement() {
        this.player = Player.getPlayer();
        this.screenWidth = 1080;
        this.screenHeight = 2160;
        this.spriteWidth = 32;
        this.spriteHeight = 32;
    }
    private static volatile  PlayerMovement playerMovement;
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

    @Override
    public void onCollision(Object object1, Object object2) {
        // maybe here we stop the correct player movement
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

    public Player getPlayer() {
        return player;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }
}

