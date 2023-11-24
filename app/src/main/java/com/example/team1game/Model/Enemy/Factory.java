package com.example.team1game.Model.Enemy;
// follows abstract factory creation pattern
public interface Factory {
    abstract Enemy createEnemy(String name, int health, int damage, int movementSpeed);
}

