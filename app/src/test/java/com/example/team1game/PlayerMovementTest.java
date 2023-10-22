package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.Player;
import com.example.team1game.Model.PlayerMovement;

import org.junit.Before;
import org.junit.Test;

public class PlayerMovementTest {
    private Player player;
    private PlayerMovement playerMovement;
    @Before
    public void setUp(){
        player = Player.getPlayer();
        playerMovement = PlayerMovement.getPlayerMovement();
    }

    @Test
    public void playerMovementConstructorWorks() {
        assertNotNull(playerMovement.getScreenHeight());
        assertEquals(playerMovement.getScreenHeight(), 2160);

        assertNotNull(playerMovement.getScreenWidth());
        assertEquals(playerMovement.getScreenWidth(), 1080);

        assertNotNull(playerMovement.getSpriteHeight());
        assertEquals(playerMovement.getSpriteHeight(), 32);

        assertNotNull(playerMovement.getSpriteWidth());
        assertEquals(playerMovement.getSpriteWidth(), 32);

        assertNotNull(playerMovement.getPlayer());
        assertEquals(playerMovement.getPlayer(), player);
    }
    @Test
    public void leftMovement() {
        player.setX(20);
        playerMovement.moveLeft();
        assertEquals(player.getX(), 10);
    }

    @Test
    public void rightMovement() {
        player.setX(20);
        playerMovement.moveRight();
        assertEquals(player.getX(), 30);
    }

    @Test
    public void upMovement() {
        player.setY(20);
        playerMovement.moveUp();
        assertEquals(player.getY(), 10);
    }

    @Test
    public void downMovement() {
        player.setY(20);
        playerMovement.moveDown();
        assertEquals(player.getY(), 30);
    }
}