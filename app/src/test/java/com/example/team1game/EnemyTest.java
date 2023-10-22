package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.Attempt;
import com.example.team1game.Model.Leaderboard;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LeaderboardTest {
    private Leaderboard leaderboard;

    @Before
    public void setUp() {
        leaderboard = Leaderboard.getInstance();

        leaderboard.clearAttempts();

        leaderboard.addAttempt(new Attempt("Player1", 50, "Medium"));
        leaderboard.addAttempt(new Attempt("Player2", 90, "Hard"));
        leaderboard.addAttempt(new Attempt("Player3", 30, "Easy"));
        leaderboard.addAttempt(new Attempt("Player4", 70, "Medium"));
        leaderboard.addAttempt(new Attempt("Player5", 40, "Easy"));
        leaderboard.addAttempt(new Attempt("Player6", 85, "Hard"));
        leaderboard.addAttempt(new Attempt("Player7", 20, "Easy"));
    }

    @Test
    public void testAttemptsAreSorted() {
        List<Attempt> attempts = leaderboard.getAttempts();

        for (int i = 0; i < attempts.size() - 1; i++) {
            assertTrue(attempts.get(i).getScore() >= attempts.get(i + 1).getScore());
        }
    }

    @Test
    public void testDisplaysTop5Scores() {
        List<Attempt> attempts = leaderboard.getAttempts();

        assertEquals(5, attempts.size());
        assertEquals(90, attempts.get(0).getScore());
        assertEquals(85, attempts.get(1).getScore());
        assertEquals(70, attempts.get(2).getScore());
        assertEquals(50, attempts.get(3).getScore());
        assertEquals(40, attempts.get(4).getScore());
    }

    @Test
    public void testMostRecentAttempt() {
        Attempt mostRecentAttempt = leaderboard.getMostRecentAttempt();

        assertNotNull(mostRecentAttempt);
        assertEquals("Player7", mostRecentAttempt.getPlayerName());
        assertEquals(20, mostRecentAttempt.getScore());
        assertEquals("Easy", mostRecentAttempt.getDifficulty());
    }

    @Test
    public void testLeaderboardIsSingleton() {
        Leaderboard instance1 = Leaderboard.getInstance();
        Leaderboard instance2 = Leaderboard.getInstance();

        assertSame(instance1, instance2);
    }
}