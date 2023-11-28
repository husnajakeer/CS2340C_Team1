package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.Powerups.TimePowerUpDecorator;

import org.junit.Test;

public class TimePowerUpDecoratorTest {

    @Test
    public void testConstructorWithPositiveScoreAndTimeBoost() {
        // Given
        int score = 100;
        int timeBoost = 30;

        // When
        TimePowerUpDecorator timePowerUp = new TimePowerUpDecorator(score, timeBoost);

        // Then
        assertEquals(score, timePowerUp.getScore());
        assertEquals(timeBoost, timePowerUp.getTimeBoost());
    }

    @Test
    public void testConstructorWithZeroScoreAndTimeBoost() {
        // Given
        int score = 0;
        int timeBoost = 0;

        // When
        TimePowerUpDecorator timePowerUp = new TimePowerUpDecorator(score, timeBoost);

        // Then
        assertEquals(score, timePowerUp.getScore());
        assertEquals(timeBoost, timePowerUp.getTimeBoost());
    }
}