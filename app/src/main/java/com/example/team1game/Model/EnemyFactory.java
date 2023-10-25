package com.example.team1game.Model;

public class EnemyFactory {
    public static Enemy createFastEnemy(String name, int health, int damage, int movementSpeed) {
        return new Enemy(name, true, health, damage, movementSpeed);
    }

    public static Enemy createSlowEnemy(String name, int health, int damage, int movementSpeed) {
        return new Enemy(name, true, health, damage, movementSpeed);
    }

    public static Enemy createSmallEnemy(String name, int health, int damage, int movementSpeed) {
        return new Enemy(name, true, health, damage, movementSpeed);
    }

    public static Enemy createBigEnemy(String name, int health, int damage, int movementSpeed) {
        return new Enemy(name, true, health, damage, movementSpeed);
    }
}
