package com.example.team1game.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Attempt {
    private final String playerName;
    private final String attemptTime;
    private final int score;
    private final String difficulty;

    public Attempt(String playerName, int score, String difficulty) {
        this.playerName = playerName;
        this.score = score;
        this.difficulty = difficulty;
        this.attemptTime = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault()).format(new Date());
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAttemptTime() {
        return attemptTime;
    }

    public int getScore() {
        return score;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
