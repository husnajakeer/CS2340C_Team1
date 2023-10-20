package com.example.team1game.Model;

public class PlayerMovement implements Movement {
    Player player;
    int screenWidth, screenHeight, spriteWidth, spriteHeight;

    public PlayerMovement(int screenWidth, int screenHeight, int spriteWidth, int spriteHeight) {
        this.player = Player.getPlayer();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    public void moveLeft() {
        int x = player.getX();
        x = Math.max(x - 10, 0);
        player.setX(x);
    }

    public void moveRight() {
        int x = player.getX();
        x = Math.min(x + 10, screenWidth - spriteWidth);
        player.setX(x);
    }

    public void moveUp() {
        int y = player.getY();
        y = Math.max(y - 10, 0);
        player.setY(y);
    }

    public void moveDown() {
        int y = player.getY();
        y = Math.min(y + 10, screenHeight - spriteHeight);
        player.setY(y);
    }
}
