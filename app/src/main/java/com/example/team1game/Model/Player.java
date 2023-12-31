package com.example.team1game.Model;


/**
 * Represents a player character in a game, extending the {@code Entity} class.
 * It includes attributes such as attack, score, difficulty level, and the player's weapon.
 */
public class Player extends Entity {
    private int attack;
    private int score;
    private String difficulty;
    private Weapons weapon;
    private Attempt currentAttempt;

    private static volatile  Player player;
    public static Player getPlayer() {
        if (player == null) {
            synchronized (Player.class) {
                if (player == null) {
                    player = new Player();
                }
            }
        }
        return player;
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
    private Player(String name, boolean livingStatus, int health, int attack,
                   int score, String difficulty, Weapons weapon) {
        super(name, livingStatus, health);
        this.attack = attack;
        this.score = score;
        this.difficulty = difficulty;
        this.weapon = weapon;
        this.currentAttempt = null;
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
    private Player(String name, boolean livingStatus, int attack, int score,
                   String difficulty, Weapons weapon) {
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
    private Player(String name, boolean livingStatus, int health, int attack,
                   int score, String difficulty) {
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
    private Player(String name, int attack, int score, String difficulty) {
        super(name);
        this.attack = attack;
        this.score = score;
        this.difficulty = difficulty;
    }
    private Player() {
        this("player", true, 5, 10, 0, "Medium",
                null);
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
        if (score < 0) {
            this.score = 0;
        } else {
            this.score = score;
        }
    }

    public void decrementScore(int amount) {
        this.score -= amount;
        if (this.score < 0) {
            this.score = 0;
        }
    }

    /**
     * Gets the difficulty level of the player.
     *
     * @return The difficulty level value.
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of the player.
     *
     * @param difficulty The new difficulty level value.
     */
    public void setDifficulty(String difficulty) {

        this.difficulty = difficulty;
        switch (this.difficulty) {
        case "Easy":
            setAttack(5);
            setHealth(5);
            break;
        case "Medium":
            setAttack(3);
            setHealth(3);
            break;
        case "Hard":
            setAttack(1);
            setHealth(1);
            break;
        default:
            break;
        }
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

    public void setWeaponPosition() {
        if (weapon != null && weapon.getSprite() != null) {
            // Set the weapon's position relative to the player
            weapon.getSprite().setX(getX());
            weapon.getSprite().setY(getY());
        }
    }

    public Attempt getCurrentAttempt() {
        return currentAttempt;
    }

    public void setCurrentAttempt(Attempt currentAttempt) {
        this.currentAttempt = currentAttempt;
    }
}