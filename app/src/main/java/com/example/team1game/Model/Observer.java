package com.example.team1game.Model;

import android.graphics.Rect;
import android.widget.TextView;

/**
 * The Observer interface defines a contract for objects that observe and react to changes in the game.
 * Implementing classes should provide the update method to respond to game state changes.
 */
public interface Observer {
    /**
     * Update the observer with new game state information.
     *
     * @param playerRect            The bounding rectangle of the player character.
     * @param healthPointsTextView  The TextView displaying the player's health points.
     * @return True if the observer was updated successfully, false otherwise.
     */
    boolean update(Rect playerRect, TextView healthPointsTextView);
}
