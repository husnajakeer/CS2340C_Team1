package com.example.team1game.Model;

import java.util.ArrayList;
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
        List<Attempt> sortedAttempts = new CopyOnWriteArrayList<>(attempts);  // clone the list
        Collections.sort(sortedAttempts, Comparator.comparingInt(Attempt::getScore).reversed());
        int start = sortedAttempts.size() - 5;
        return start >= 0 ? sortedAttempts.subList(start, sortedAttempts.size()) : sortedAttempts;
    }

    public Attempt getMostRecentAttempt() {
        return attempts.size() > 0 ? attempts.get(attempts.size() - 1) : null;
    }
}
