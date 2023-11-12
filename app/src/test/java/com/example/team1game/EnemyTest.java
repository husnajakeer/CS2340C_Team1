package com.example.team1game;

import static org.junit.Assert.*;
import com.example.team1game.Model.Enemy.Enemy;
import com.example.team1game.Model.Enemy.EnemyMovement;

import org.junit.Before;
import org.junit.Test;

public class EnemyTest {

    private Enemy enemy;

    @Before
    public void setUp() {
        enemy = new Enemy("name1", true, 20,5, 10);
    }

    @Test
    public void testMovementSpeed() {
        int expectedSpeed = 0;
        Enemy enemy = new Enemy("Name1",expectedSpeed);
        int actualSpeed = enemy.getMovementSpeed();
        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    public void testEnemyMovement() {
        EnemyMovement expectedMovement = new EnemyMovement(enemy);
        enemy.setEnemyMovement(expectedMovement);
        EnemyMovement actualMovement = enemy.getEnemyMovement();
        assertEquals(expectedMovement, actualMovement);
    }


}