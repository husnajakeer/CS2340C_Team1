package com.example.team1game.Model;

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
}

