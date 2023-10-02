package com.example.team1game.Model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Leaderboard {
    private final List<Attempt> attempts;
    private static Leaderboard instance;

    private Leaderboard() {
        this.attempts = new CopyOnWriteArrayList<>();
    }

    public static Leaderboard getInstance() {
        if (instance == null) {
            synchronized (Leaderboard.class) {
                if (instance == null) {
                    instance = new Leaderboard();
                }
            }
        }
        return instance;
    }

    public void addAttempt(Attempt attempt) {
        this.attempts.add(attempt);
    }

    public List<Attempt> getAttempts() {
        Collections.sort(attempts, Comparator.comparingInt(Attempt::getScore).reversed());
        return attempts;
    }
}
