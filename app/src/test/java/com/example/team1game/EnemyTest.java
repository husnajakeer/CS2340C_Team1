package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.Enemy;
import com.example.team1game.Model.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EnemyTest {
    private Enemy enemy;
    @Before
    public void setUp(){
        enemy = Enemy.getEnemy();
    }

    @Test
    public void enemyConstructorWorks() {
        assertNotNull(enemy.getName());
        assertEquals(enemy.getName(), "enemy");
        assertNotNull(enemy.getHealth());
        assertEquals(enemy.getHealth(), 5);
        assertNotNull(enemy.getDamage());
        assertEquals(enemy.getDamage(), 10);
    }
    @Test
    public void testEnemyIsSingleton() {
        Enemy enemy1 = Enemy.getEnemy();
        Enemy enemy2 = Enemy.getEnemy();

        assertSame(enemy1, enemy2);
    }
    @Test
    public void playerChooseDiffRightDamage(){
        enemy.setDamage(13);
        assertEquals(enemy.getDamage(), 13);
        assertNotNull(enemy.getDamage());

        enemy.setDamage(10);
        assertEquals(enemy.getDamage(), 10);
        assertNotNull(enemy.getDamage());

    }


}