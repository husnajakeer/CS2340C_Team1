package com.example.team1game.Model;

/**
 * Represents a battle between a player and an enemy in a game.
 * It holds information about the player, the enemy, and the status of the battle.
 */
public class Battle {

    /**
     * The player participating in the battle.
     */
    private Player player;

    /**
     * The enemy the player is battling against.
     */
    private Enemy enemy;

    /**
     * The status of the battle, where {@code true} indicates that the battle is ongoing,
     * and {@code false} indicates that the battle has ended.
     */
    private boolean status;

    /**
     * Constructs a new Battle object with the specified player, enemy, and status.
     *
     * @param player The player participating in the battle.
     * @param enemy The enemy the player is battling against.
     * @param status The status of the battle (true for ongoing, false for ended).
     */
    public Battle(Player player, Enemy enemy, boolean status) {
        this.player = player;
        this.enemy = enemy;
        this.status = status;
    }

    /**
     * Retrieves the player participating in the battle.
     *
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player participating in the battle.
     *
     * @param player The player object to set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Retrieves the enemy the player is battling against.
     *
     * @return The enemy object.
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Sets the enemy the player is battling against.
     *
     * @param enemy The enemy object to set.
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    /**
     * Checks the status of the battle.
     *
     * @return the outcome of the battle.
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the status of the battle.
     *
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
}
