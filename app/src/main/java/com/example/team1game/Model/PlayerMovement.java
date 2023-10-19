package com.example.team1game.Model;

/**
 * should be implemented w WASD gui buttons on phone
 * some questions. do we need a game loop? do we really need to implement observer pattern?
 * for now I don't think we should (it doesn't make sense even tho there's an ed post)
 */
public class PlayerMovement implements Movement{
    Player player;
    // replace these x and y w the player singleton
    private int x, y; // Current position

    public void moveLeft() {
        x -= 1;
    }

    public void moveRight() {
        x += 1;
    }

    public void moveUp() {
        y -= 1;
    }

    public void moveDown() {
        y += 1;
    }
}
