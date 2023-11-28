package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.BaseScreen;
import com.example.team1game.Model.Player;
import com.example.team1game.Model.Powerups.AttackPowerUpDecorator;

import org.junit.Test;

public class AttackPowerUpDecoratorTest {

    @Test
    public void testConstructorWithPositiveMultiplier() {
        // Given
        int scoreMultiplier = 2;

        // When
        AttackPowerUpDecorator attackPowerUp = new AttackPowerUpDecorator(scoreMultiplier);

        // Then
        assertEquals(scoreMultiplier, attackPowerUp.getScoreMultiplier());
    }

    @Test
    public void testConstructorWithZeroMultiplier() {
        // Given
        int scoreMultiplier = 0;

        // When
        AttackPowerUpDecorator attackPowerUp = new AttackPowerUpDecorator(scoreMultiplier);

        // Then
        assertEquals(scoreMultiplier, attackPowerUp.getScoreMultiplier());
    }
}