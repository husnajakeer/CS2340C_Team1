package com.example.team1game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.example.team1game.Model.Attempt;
import com.example.team1game.Model.Leaderboard;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LeaderboardTest2 {
    private Leaderboard leaderboard;

    @Before
    public void setUp() {
        leaderboard = Leaderboard.getInstance();

        leaderboard.clearAttempts();

        leaderboard.addAttempt(new Attempt("Player1", 80, "Hard"));
        leaderboard.addAttempt(new Attempt("Player2", 10, "Hard"));
        leaderboard.addAttempt(new Attempt("Player3", 30, "Easy"));
        leaderboard.addAttempt(new Attempt("Player4", 70, "Medium"));
        leaderboard.addAttempt(new Attempt("Player5", -50, "Easy"));
    }

    @Test
    public void testGetAttempts() {
        List<Attempt> attemptsList = leaderboard.getAttempts();

        assertNotNull(attemptsList);
        assertEquals("Player1", attemptsList.get(0).getPlayerName());
        assertEquals(80, attemptsList.get(0).getScore());
        assertEquals("Hard", attemptsList.get(0).getDifficulty());

        assertEquals("Player3", attemptsList.get(2).getPlayerName());
        assertEquals(30, attemptsList.get(2).getScore());
        assertEquals("Easy", attemptsList.get(2).getDifficulty());


        assertEquals("Player5", attemptsList.get(4).getPlayerName());
        assertEquals(0, attemptsList.get(4).getScore());
        assertEquals("Easy", attemptsList.get(4).getDifficulty());

    }
    @Test
    public void testClearAttempts() {
        leaderboard.clearAttempts();
        for (int i = 0; i < leaderboard.getAttempts().size(); i++) {
            assertEquals(null, leaderboard.getAttempts().get(i));
        }
    }
}