package com.example.demo.powerup;

import com.example.demo.actors.UserPlane;
import com.example.demo.ActiveActorDestructible;

/**
 * The GainHeart class represents a power-up that increases the user's health by 1.
 * This power-up is represented by a heart icon and is collected by the user when they collide with it.
 */
public class GainHeart extends ActiveActorDestructible {
    private static final String IMAGE_NAME = "hearts.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final int HEART_GAIN = 1;
    private final UserPlane user;

    /**
     * Constructs a GainHeart power-up at the specified position and associates it with a user plane.
     *
     * @param x the x-coordinate of the power-up's position
     * @param y the y-coordinate of the power-up's position
     * @param user the UserPlane instance that will gain health from this power-up
     */
    public GainHeart(double x, double y, UserPlane user) {
        super(IMAGE_NAME, IMAGE_HEIGHT, x, y);
        this.user = user;
    }

    /**
     * Increases the health of the user plane by the specified amount (HEART_GAIN).
     * This method is called when the user collects the power-up.
     */
    public void plusHealth() {
        user.gainHealth(HEART_GAIN);
        destroy();
    }

    /**
     * Destroys the power-up and prints a log message indicating its destruction.
     */
    @Override
    public void destroy() {
        System.out.println("Destroying power-up: " + this);
        super.destroy();
    }

    /**
     * Updates the state of the actor. This method is not implemented for GainHeart as the power-up
     * doesn't have any dynamic changes outside of its collection and destruction.
     */
    @Override
    public void updateActor() {
    }

    /**
     * Updates the position of the power-up. This method is not implemented for GainHeart
     * as its position does not change after creation.
     */
    @Override
    public void updatePosition() {
    }

    /**
     * Handles taking damage for the power-up. This method is not implemented for GainHeart
     * as the power-up cannot take damage.
     */
    @Override
    public void takeDamage() {
    }
}
