package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.Leaderboard;
import com.example.team1game.Model.Player;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Player player;
    private Leaderboard leaderboard;
    @Before
    public void setUp(){
        player = Player.getPlayer();
    }
    @Test
    public void playerConstructorWorks() {
        assertNotNull(player.getName());
        assertEquals(player.getName(), "player");
        assertNotNull(player.getDifficulty());
        assertEquals(player.getDifficulty(), "Medium");
        assertEquals(player.getScore(), 0);
    }
    @Test
    public void testPlayerIsSingleton() {
        Player player1 = Player.getPlayer();
        Player player2 = Player.getPlayer();

        assertSame(player1, player2);
    }

    @Test
    public void initializeDifficulty() {
        player.setDifficulty("Easy");
        assertEquals(player.getDifficulty(), "Easy");
        assertNotNull(player.getDifficulty());
        player.setDifficulty("Medium");
        assertEquals(player.getDifficulty(), "Medium");
        assertNotNull(player.getDifficulty());
        player.setDifficulty("Hard");
        assertEquals(player.getDifficulty(), "Hard");
        assertNotNull(player.getDifficulty());
    }
    @Test
    public void scoreNotNegative() {
        player.setScore(-100);
        assertEquals(0, player.getScore());
    }
    @Test
    public void playerChooseDiffRightAttack(){
        player.setDifficulty("Medium");
        assertEquals(player.getDifficulty(), "Medium");
        // default medium attack for player
        assertEquals(player.getAttack(), 3);
    }
    @Test
    public void playerChooseDiffRightHealth(){
        player.setDifficulty("Medium");
        assertEquals(player.getDifficulty(), "Medium");
        // default medium health for player
        assertEquals(player.getHealth(), 3);
    }


}