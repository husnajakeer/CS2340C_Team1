package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.Collision;
import com.example.team1game.Model.Player;
import com.example.team1game.Model.PlayerMovement;
import com.example.team1game.Model.Subscriber;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CollisionTest {
    private Collision collision;
    private Player player;
    private PlayerMovement playerMovement;
    @Before
    public void setUp(){
        collision = new Collision();
        player = Player.getPlayer();
        playerMovement = new PlayerMovement(300, 400, 30, 30);
    }

    @Test
    public void addObserver() {
        collision.addObserver(playerMovement);
        ArrayList<PlayerMovement> observers = new ArrayList<>();
        observers.add(playerMovement);
        assertEquals(collision.getObservers(), new ArrayList<>(observers));
        observers.clear();
    }

    @Test
    public void removeObserver() {
        collision.addObserver(playerMovement); // Add the observer first
        ArrayList<Subscriber> observers = (ArrayList<Subscriber>) collision.getObservers();

        assertTrue(observers.contains(playerMovement));

        collision.removeObserver(playerMovement);

        assertFalse(observers.contains(playerMovement));
    }
}