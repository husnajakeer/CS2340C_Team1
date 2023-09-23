package com.example.team1game.Model;

/**
 * Represents a game instance in a dungeon environment.
 * It contains information about power-ups that can be found within the game.
 */
public class DungeonGame {
    private Powerups powerups;

    /**
     * Constructs a new object with the specified power-ups.
     *
     * @param powerups The power-ups available in the game.
     */
    public DungeonGame(Powerups powerups) {
        this.powerups = powerups;
    }

    /**
     * Gets the power-ups available in the game.
     *
     * @return The power-ups.
     */
    public Powerups getPowerups() {
        return powerups;
    }

    /**
     * Sets the power-ups available in the game.
     *
     * @param powerups The new power-ups for the game.
     */
    public void setPowerups(Powerups powerups) {
        this.powerups = powerups;
    }
}
