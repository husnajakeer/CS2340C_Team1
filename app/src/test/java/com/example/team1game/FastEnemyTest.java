package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.Enemy.Enemy;
import com.example.team1game.Model.Enemy.FastEnemy;

import org.junit.Before;
import org.junit.Test;

public class FastEnemyTest {
    FastEnemy factory;
    @Before
    public void setUp(){
        factory = new FastEnemy();
    }

    @Test
    public void createEnemy() {
        Enemy fastEnemy = factory.createEnemy("FastEnemy", 100, 10, 20);
        assertEquals(fastEnemy.getName(), "FastEnemy");
    }
    @Test
    public void createEnemy2() {
        Enemy slowEnemy = factory.createEnemy("SlowEnemy", 100, 10, 20);
        assertEquals(slowEnemy.getName(), "SlowEnemy");
    }
    @Test
    public void createEnemy3() {
        Enemy bigEnemy = factory.createEnemy("BigEnemy", 100, 10, 20);
        assertEquals(bigEnemy.getName(), "BigEnemy");
    }
    @Test
    public void createEnemy4() {
        Enemy smallEnemy = factory.createEnemy("SmallEnemy", 100, 10, 20);
        assertEquals(smallEnemy.getName(), "SmallEnemy");
    }
}