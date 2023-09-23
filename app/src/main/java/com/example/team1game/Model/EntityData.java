package com.example.team1game.Model;

/**
 * Represents an entity's data with position, speed, and direction.
 */
public class EntityData {
    private double x;
    private double y;
    private int speed;
    private int direction;

    /**
     * Constructs an EntityData object with the specified parameters.
     *
     * @param x         The x-coordinate of the entity's position.
     * @param y         The y-coordinate of the entity's position.
     * @param speed     The speed of the entity.
     * @param direction The direction of the entity.
     */
    public EntityData(double x, double y, int speed, int direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * Gets the x-coordinate of the entity's position.
     *
     * @return The x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the entity's position.
     *
     * @param x The new x-coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the entity's position.
     *
     * @return The y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the entity's position.
     *
     * @param y The new y-coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the speed of the entity.
     *
     * @return The speed.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the entity.
     *
     * @param speed The new speed.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Gets the direction of the entity.
     *
     * @return The direction.
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the entity.
     *
     * @param direction The new direction.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
}
