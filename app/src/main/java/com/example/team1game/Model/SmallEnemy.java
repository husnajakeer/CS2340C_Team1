package com.example.team1game.Model;

public class SmallEnemy implements Factory{
    /**
     * Creates a small enemy with the given attributes.
     *
     * @param name          The name of the enemy.
     * @param health        The health points of the enemy.
     * @param damage        The damage points dealt by the enemy.
     * @param movementSpeed The movement speed of the enemy.
     * @return A new small enemy instance with the specified attributes.
     */
    @Override
    public Enemy createEnemy(String name, int health, int damage, int movementSpeed) {
        return new Enemy(name, true, health, damage, movementSpeed);
    }
}
