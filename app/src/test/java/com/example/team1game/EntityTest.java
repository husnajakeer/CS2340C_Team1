package com.example.team1game;

import static org.junit.Assert.*;

import com.example.team1game.Model.Entity;

import org.junit.Before;
import org.junit.Test;

public class EntityTest {
    //private Entity entity;

    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void testEntityConstructor() {
        String testName = "TestEntity";
        Entity entity = new Entity(testName);

        // Check if the constructor correctly initializes the fields
        assertEquals(testName, entity.getName());
        assertEquals(0, entity.getHealth());
    }
    @Test
    public void testGetXAndSetX() {
        Entity entity = new Entity("testName");

        // Test the getX() method
        int initialX = entity.getX();
        assertEquals(0, initialX); // Assuming it's initially set to 0

        // Test the setX() method
        entity.setX(42);
        int updatedX = entity.getX();
        assertEquals(42, updatedX);
    }
}