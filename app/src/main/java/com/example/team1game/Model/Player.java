package com.example.team1game.Model;

/**
 * Represents a player character in a game, extending the {@code Entity} class.
 * It includes attributes such as attack, score, difficulty level, and the player's weapon.
 */
public class Player extends Entity {
    private int attack;
    private int score;
    private int difficulty;
    private Weapons weapon;

    /**
     * Constructs a new object with the specified parameters, including attack, score,
     * difficulty level, and weapon.
     *
     * @param name       The name of the player character.
     * @param livingStatus The living status of the player.
     * @param health     The health points of the player.
     * @param stats      The data statistics of the player.
     * @param attack     The attack power of the player.
     * @param score      The score of the player.
     * @param difficulty The difficulty level of the player.
     * @param weapon     The weapon held by the player.
     */
    public Player(String name, boolean livingStatus, int health, EntityData stats, int attack, int score,
                  int difficulty, Weapons weapon) {
        super(name, livingStatus, health, stats);
        this.attack = attack;
        this.score = score;
        this.difficulty = difficulty;
        this.weapon = weapon;
    }

    /**
     * Constructs a new  object with the specified parameters, including attack, score,
     * difficulty level, and weapon.
     *
     * @param name       The name of the player character.
     * @param livingStatus The living status of the player.
     * @param health     The health points of the player.
     * @param attack     The attack power of the player.
     * @param score      The score of the player.
     * @param difficulty The difficulty level of the player.
     * @param weapon     The weapon held by the player.
     */
    public Player(String name, boolean livingStatus, int health, int attack, int score, int difficulty,
                  Weapons weapon) {
        super(name, livingStatus, health);
        this.attack = attack;
        this.score = score;
        this.difficulty = difficulty;
        this.weapon = weapon;
    }

    /**
     * Constructs a new object with the specified parameters, including attack, score,
     * difficulty level, and weapon.
     *
     * @param name       The name of the player character.
     * @param livingStatus The living status of the player.
     * @param attack     The attack power of the player.
     * @param score      The score of the player.
     * @param difficulty The difficulty level of the player.
     * @param weapon     The weapon held by the player.
     */
    public Player(String name, boolean livingStatus, int attack, int score, int difficulty, Weapons weapon) {
        super(name, livingStatus);
        this.attack = attack;
        this.score = score;
        this.difficulty = difficulty;
        this.weapon = weapon;
    }

    /**
     * Constructs a new object with the specified parameters, including attack, score,
     * and difficulty level.
     *
     * @param name       The name of the player character.
     * @param livingStatus The living status of the player.
     * @param health     The health points of the player.
     * @param attack     The attack power of the player.
     * @param score      The score of the player.
     * @param difficulty The difficulty level of the player.
     */
    public Player(String name, boolean livingStatus, int health, int attack, int score, int difficulty) {
        super(name, livingStatus, health);
        this.attack = attack;
        this.score = score;
        this.difficulty = difficulty;
    }

    /**
     * Constructs a new object with the specified parameters, including attack, score,
     * and difficulty level.
     *
     * @param name       The name of the player character.
     * @param attack     The attack power of the player.
     * @param score      The score of the player.
     * @param difficulty The difficulty level of the player.
     */
    public Player(String name, int attack, int score, int difficulty) {
        super(name);
        this.attack = attack;
        this.score = score;
        this.difficulty = difficulty;
    }

    /**
     * Gets the attack power of the player.
     *
     * @return The attack power value.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Sets the attack power of the player.
     *
     * @param attack The new attack power value.
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * Gets the score of the player.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the player.
     *
     * @param score The new score value.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the difficulty level of the player.
     *
     * @return The difficulty level value.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of the player.
     *
     * @param difficulty The new difficulty level value.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the weapon held by the player.
     *
     * @return The player's weapon.
     */
    public Weapons getWeapon() {
        return weapon;
    }

    /**
     * Sets the weapon held by the player.
     *
     * @param weapon The new weapon for the player.
     */
    public void setWeapon(Weapons weapon) {
        this.weapon = weapon;
    }
}