package com.example.team1game.Model;

import android.graphics.Rect;

/**
 * Grace is working on, no touchie
 */
public interface Subscriber {
    // go into player script and stop the player from moving?
    // player script should have the wasd controls maybe
    public void handleCollision(Rect obstacleRect, Rect playerRect);
}
