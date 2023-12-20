package com.example.team1game.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The Attempt class represents a player's attempt at a game,
 * storing information such as the player's name,
 * attempt time, score, and game difficulty level.
 */
public class Attempt {
    private final String playerName;
    private String attemptTime;
    private final int score;
    private final String difficulty;

    /**
     * Constructs an Attempt object with the provided player name, score, and difficulty.
     *
     * @param playerName  the name of the player making the attempt
     * @param score       the score achieved in the attempt (must be non-negative)
     * @param difficulty  the difficulty level of the game
     */
    public Attempt(String playerName, int score, String difficulty) {
        this.playerName = playerName;
        if (score < 0) {
            this.score = 0;
        } else {
            this.score = score;
        }
        this.difficulty = difficulty;
        this.attemptTime = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault()).format(new Date());
    }

    /**
     * Get the player's name associated with this attempt.
     *
     * @return the player's name
     */
    public String getPlayerName() {
        return playerName;
    }
    // Setter for attemptTime
    public void setAttemptTime(String newAttemptTime) {
        // Check if newAttemptTime is valid (optional)
        // For example, you could check if it follows a specific format

        // Set the new attempt time
        this.attemptTime = newAttemptTime;
    }

    /**
     * Get the date and time when the attempt was made.
     *
     * @return the formatted date and time of the attempt
     */
    public String getAttemptTime() {
        return attemptTime;
    }

    /**
     * Get the score achieved in this attempt.
     *
     * @return the score of the attempt
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the difficulty level of the game for this attempt.
     *
     * @return the difficulty level
     */
    public String getDifficulty() {
        return difficulty;
    }
}
