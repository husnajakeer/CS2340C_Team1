package com.example.team1game.Model;

/**
 * Represents an enemy entity in a game, extending the Entity class.
 * It adds the ability to specify enemy-specific damage.
 */
public class Enemy extends Entity {
    private int damage;

    /**
     * Constructs a new object with the specified parameters, including damage.
     *
     * @param name         The name of the enemy.
     * @param livingStatus The living status of the enemy.
     * @param health       The health points of the enemy.
     * @param stats        The data statistics of the enemy.
     * @param damage       The damage inflicted by the enemy.
     */
    public Enemy(String name, boolean livingStatus, int health, EntityData stats, int damage) {
        super(name, livingStatus, health, stats);
        this.damage = damage;
    }

    /**
     * Constructs a new object with the specified parameters, including damage.
     *
     * @param name         The name of the enemy.
     * @param livingStatus The living status of the enemy.
     * @param health       The health points of the enemy.
     * @param damage       The damage inflicted by the enemy.
     */
    public Enemy(String name, boolean livingStatus, int health, int damage) {
        super(name, livingStatus, health);
        this.damage = damage;
    }

    /**
     * Constructs a new object with the specified parameters, including damage.
     *
     * @param name   The name of the enemy.
     * @param livingStatus The living status of the enemy.
     * @param damage The damage inflicted by the enemy.
     */
    public Enemy(String name, boolean livingStatus, int damage) {
        super(name, livingStatus);
        this.damage = damage;
    }

    /**
     * Constructs a new {@code Enemy} object with the specified parameters, including damage.
     *
     * @param name   The name of the enemy.
     * @param damage The damage inflicted by the enemy.
     */
    public Enemy(String name, int damage) {
        super(name);
        this.damage = damage;
    }

    /**
     * Gets the damage inflicted by the enemy.
     *
     * @return The damage value.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the damage inflicted by the enemy.
     *
     * @param damage The new damage value.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
