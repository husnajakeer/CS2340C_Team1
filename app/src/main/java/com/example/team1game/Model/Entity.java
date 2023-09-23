package com.example.team1game.Model;

/**
 * Represents a generic entity in a game or simulation.
 * It includes attributes such as the entity's name, living status, health, and statistics.
 */
public class Entity {
    private String name;
    private boolean livingStatus;
    private int health;
    private EntityData stats;

    /**
     * Constructs a new object with the specified parameters, including name, living status,
     * health, and statistics.
     *
     * @param name         The name of the entity.
     * @param livingStatus The living status of the entity (true if alive, false if not).
     * @param health       The health points of the entity.
     * @param stats        The data statistics of the entity.
     */
    public Entity(String name, boolean livingStatus, int health, EntityData stats) {
        this.name = name;
        this.livingStatus = livingStatus;
        this.health = health;
        this.stats = stats;
    }

    /**
     * Constructs a new object with the specified parameters, including name, living status,
     * and health. Statistics are initialized with default values.
     *
     * @param name         The name of the entity.
     * @param livingStatus The living status of the entity (true if alive, false if not).
     * @param health       The health points of the entity.
     */
    public Entity(String name, boolean livingStatus, int health) {
        this.name = name;
        this.livingStatus = livingStatus;
        this.health = health;
        this.stats = new EntityData(0, 0, 0, 0);
    }

    /**
     * Constructs a new object with the specified parameters, including name and living status.
     * Health and statistics are initialized with default values.
     *
     * @param name         The name of the entity.
     * @param livingStatus The living status of the entity (true if alive, false if not).
     */
    public Entity(String name, boolean livingStatus) {
        this.name = name;
        this.livingStatus = livingStatus;
        this.health = 0;
        this.stats = new EntityData(0, 0, 0, 0);
    }

    /**
     * Constructs a new object with the specified name.
     * The entity is considered alive with default health and statistics.
     *
     * @param name The name of the entity.
     */
    public Entity(String name) {
        this.name = name;
        this.livingStatus = true;
        this.health = 0;
        this.stats = new EntityData(0, 0, 0, 0);
    }

    /**
     * Gets the name of the entity.
     *
     * @return The name of the entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the entity.
     *
     * @param name The new name for the entity.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the entity is alive or not.
     *
     * @return if the entity is alive
     */
    public boolean isLivingStatus() {
        return livingStatus;
    }

    /**
     * Sets the living status of the entity.
     *
     * @param livingStatus sets if the entity is alive
     */
    public void setLivingStatus(boolean livingStatus) {
        this.livingStatus = livingStatus;
    }

    /**
     * Gets the health points of the entity.
     *
     * @return The health points.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health points of the entity.
     *
     * @param health The new health points.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets the data statistics of the entity.
     *
     * @return The entity's statistics.
     */
    public EntityData getStats() {
        return stats;
    }

    /**
     * Sets the data statistics of the entity.
     *
     * @param stats The new statistics for the entity.
     */
    public void setStats(EntityData stats) {
        this.stats = stats;
    }
}
