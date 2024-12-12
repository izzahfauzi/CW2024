package com.example.demo.projectiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossProjectileTest {

    private BossProjectile bossProjectile;

    @BeforeEach
    void setUp() {

        bossProjectile = new BossProjectile(100);
    }

    @Test
    void testUpdatePosition() {
        double initialXPosition = bossProjectile.getLayoutX();

        bossProjectile.updatePosition();

        assertEquals(initialXPosition - 15, bossProjectile.getLayoutX(), "The X position should move by -15");
    }

    @Test
    void testUpdateActor() {
        double initialXPosition = bossProjectile.getLayoutX();

        bossProjectile.updateActor();

        assertEquals(initialXPosition - 15, bossProjectile.getLayoutX(), "The X position should move by -15 after updateActor()");
    }
}
