package com.example.demo.powerup;

import com.example.demo.actors.UserPlane;
import com.example.demo.ActiveActorDestructible;

public class GainHeart extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "hearts.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final int HEART_GAIN = 1;
    private final UserPlane user;

    public GainHeart(double x, double y, UserPlane user) {
        super(IMAGE_NAME, IMAGE_HEIGHT, x, y);
        this.user = user;
    }

    public void plusHealth() {
        user.gainHealth(HEART_GAIN);
        destroy();
    }

    @Override
    public void destroy() {
        System.out.println("Destroying power-up: " + this);
        super.destroy();
    }

    @Override
    public void updateActor() {
    }

    @Override
    public void updatePosition() {
    }

    @Override
    public void takeDamage() {
    }

}
