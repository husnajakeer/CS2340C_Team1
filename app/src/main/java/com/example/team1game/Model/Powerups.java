package com.example.team1game.Model;

/**
 * Represents power-up items in a game.
 * Each power-up has a name, an effect, and a duration.
 */
public class Powerups {
    private String powerupName;
    private int effect;
    private int duration;

    /**
     * Constructs a new object with the specified parameters.
     *
     * @param powerupName The name of the power-up.
     * @param effect      The effect value of the power-up.
     * @param duration    The duration of the power-up effect.
     */
    public Powerups(String powerupName, int effect, int duration) {
        this.powerupName = powerupName;
        this.effect = effect;
        this.duration = duration;
    }

    /**
     * Gets the name of the power-up.
     *
     * @return The name of the power-up.
     */
    public String getPowerupName() {
        return powerupName;
    }

    /**
     * Sets the name of the power-up.
     *
     * @param powerupName The new name for the power-up.
     */
    public void setPowerupName(String powerupName) {
        this.powerupName = powerupName;
    }

    /**
     * Gets the effect value of the power-up.
     *
     * @return The effect value of the power-up.
     */
    public int getEffect() {
        return effect;
    }

    /**
     * Sets the effect value of the power-up.
     *
     * @param effect The new effect value for the power-up.
     */
    public void setEffect(int effect) {
        this.effect = effect;
    }

    /**
     * Gets the duration of the power-up effect.
     *
     * @return The duration of the power-up effect.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the power-up effect.
     *
     * @param duration The new duration for the power-up effect.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
