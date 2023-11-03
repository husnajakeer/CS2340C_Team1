package com.example.team1game.Model;

import android.graphics.Rect;
import android.widget.TextView;

import com.example.team1game.ModelView.GameScreen;

/**
 * Represents an enemy entity in a game, extending the Entity class.
 * It adds the ability to specify enemy-specific damage.
 */
public class Enemy extends Entity {
    private int damage;
    // 1, 2, 3, with 3 being the fastest
    private int movementSpeed;
    private EnemyMovement enemyMovement;

    private GameScreen gameScreen;

    private Rect enemyRect = new Rect(); // Cached Rect to avoid allocations
    private boolean isPlayerInContactWithEnemy = false; // State flag

    /**
     * Constructs a new object with the specified parameters, including damage.
     *
     * @param name          The name of the enemy.
     * @param livingStatus  The living status of the enemy.
     * @param health        The health points of the enemy.
     * @param damage        The damage inflicted by the enemy.
     * @param movementSpeed
     */
    public Enemy(String name, boolean livingStatus, int health, int damage, int movementSpeed) {
        super(name, livingStatus, health);
        this.damage = damage;
        this.movementSpeed = movementSpeed;
        this.enemyMovement = new EnemyMovement(this);
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
        this.enemyMovement = new EnemyMovement(this);

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
        this.enemyMovement = new EnemyMovement(this);

    }

    /**
     * Gets the damage inflicted by the enemy.
     *
     * @return The damage value.
     */
    public int getDamage() {
        return damage;
    }

    public static boolean update(Rect playerRect, Rect enemyRect) {
        if (Rect.intersects(playerRect, enemyRect)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the damage inflicted by the enemy.
     *
     * @param damage The new damage value.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public EnemyMovement getEnemyMovement() {
        return enemyMovement;
    }

    public void setEnemyMovement(EnemyMovement enemyMovement) {
        this.enemyMovement = enemyMovement;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }




    /*
    @Override
    public void onCollision(Object object1, Object object2) {
        if (object1 instanceof Player && object2 instanceof Enemy) {
            Player player = (Player) object1;
            player.loseHealth(damage);
        }
    }
     */
}