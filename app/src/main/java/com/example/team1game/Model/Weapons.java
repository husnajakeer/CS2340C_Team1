package com.example.team1game.Model;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Represents a weapon with attributes like name, attack power, and associated sprite.
 */
public class Weapons {

    /** Name of the weapon. */
    private String weaponName;

    /** Attack power associated with the weapon. */
    private int attackPower;

    /** Sprite or image associated with the weapon. */
    private ImageView sprite;

    /**
     * Constructs a new weapon with the specified name, attack power, and sprite.
     *
     * @param weaponName  the name of the weapon
     * @param attackPower the attack power of the weapon
     * @param sprite      the image or sprite of the weapon
     */
    public Weapons(String weaponName, int attackPower, ImageView sprite) {
        this.weaponName = weaponName;
        this.attackPower = attackPower;
        this.sprite = sprite;
    }

    /**
     * Constructs a new weapon with the specified name and sprite.
     * Attack power is not provided in this constructor.
     *
     * @param weaponName the name of the weapon
     * @param sprite     the image or sprite of the weapon
     */
    public Weapons(String weaponName, ImageView sprite) {
        this.weaponName = weaponName;
        this.sprite = sprite;
    }

    /**
     * Returns the name of the weapon.
     *
     * @return the weapon name
     */
    public String getWeaponName() {
        return weaponName;
    }

    /**
     * Sets the name of the weapon.
     *
     * @param weaponName the new name of the weapon
     */
    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    /**
     * Returns the attack power of the weapon.
     *
     * @return the attack power
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Sets the attack power of the weapon.
     *
     * @param attackPower the new attack power
     */
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    /**
     * Returns the sprite or image associated with the weapon.
     *
     * @return the weapon sprite
     */
    public ImageView getSprite() {
        return sprite;
    }

    /**
     * Sets the sprite or image for the weapon.
     *
     * @param sprite the new sprite or image
     */
    public void setSprite(ImageView sprite) {
        this.sprite = sprite;
    }
}
