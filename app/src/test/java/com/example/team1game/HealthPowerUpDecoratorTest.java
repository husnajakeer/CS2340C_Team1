package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.BaseScreen;
import com.example.team1game.Model.Powerups.HealthPowerUpDecorator;
import com.example.team1game.ModelView.GameScreen;

import org.junit.Test;

public class HealthPowerUpDecoratorTest {
    @Test
    public void testConstructorWithPositiveHearts() {
        // Given
        int hearts = 5;
        //BaseScreen baseScreen = new GameScreen(); // Assuming BaseScreen can be instantiated

        // When
        HealthPowerUpDecorator healthPowerUp = new HealthPowerUpDecorator(hearts, null);

        // Then
        assertEquals(hearts, healthPowerUp.getHearts());
    }

    @Test
    public void testConstructorWithZeroHearts() {
        // Given
        int hearts = 0;
        //BaseScreen baseScreen = new GameScreen(); // Assuming BaseScreen can be instantiated

        // When
        HealthPowerUpDecorator healthPowerUp = new HealthPowerUpDecorator(hearts, null);

        // Then
        assertEquals(hearts, healthPowerUp.getHearts());
    }
}