package com.example.team1game.Model;

/**
 * The EnemyFactory class provides static methods for creating different types of enemy characters.
 */
public class EnemyFactory {
    /**
     * Creates a fast enemy with the given attributes.
     *
     * @param name          The name of the enemy.
     * @param health        The health points of the enemy.
     * @param damage        The damage points dealt by the enemy.
     * @param movementSpeed The movement speed of the enemy.
     * @return A new fast enemy instance with the specified attributes.
     */
    public static Enemy createFastEnemy(String name, int health, int damage, int movementSpeed) {
        return new Enemy(name, true, health, damage, movementSpeed);
    }

    /**
     * Creates a slow enemy with the given attributes.
     *
     * @param name          The name of the enemy.
     * @param health        The health points of the enemy.
     * @param damage        The damage points dealt by the enemy.
     * @param movementSpeed The movement speed of the enemy.
     * @return A new slow enemy instance with the specified attributes.
     */
    public static Enemy createSlowEnemy(String name, int health, int damage, int movementSpeed) {
        return new Enemy(name, true, health, damage, movementSpeed);
    }

    /**
     * Creates a small enemy with the given attributes.
     *
     * @param name          The name of the enemy.
     * @param health        The health points of the enemy.
     * @param damage        The damage points dealt by the enemy.
     * @param movementSpeed The movement speed of the enemy.
     * @return A new small enemy instance with the specified attributes.
     */
    public static Enemy createSmallEnemy(String name, int health, int damage, int movementSpeed) {
        return new Enemy(name, true, health, damage, movementSpeed);
    }

    /**
     * Creates a big enemy with the given attributes.
     *
     * @param name          The name of the enemy.
     * @param health        The health points of the enemy.
     * @param damage        The damage points dealt by the enemy.
     * @param movementSpeed The movement speed of the enemy.
     * @return A new big enemy instance with the specified attributes.
     */
    public static Enemy createBigEnemy(String name, int health, int damage, int movementSpeed) {
        return new Enemy(name, true, health, damage, movementSpeed);
    }
}
