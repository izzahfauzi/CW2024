package com.example.demo.powerup;

import com.example.demo.actors.UserPlane;
import com.example.demo.ActiveActorDestructible;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The PowerUpManager class is responsible for managing the spawning, updating,
 * and collision detection of power-ups in the game. It handles the creation
 * and collection of power-ups, such as hearts that increase the player's health.
 */
public class PowerUpManager {
    private static final int SPAWN_CHANCE = 1;
    private static final double X_RIGHT_BOUND = 1100.0;
    private static final double Y_UPPER_BOUND = 65.0;
    private static final double Y_LOWER_BOUND = 650.0;
    private final Group root;
    private final UserPlane user;
    private final List<ActiveActorDestructible> activePowerUps;

    /**
     * Constructs a PowerUpManager that manages the spawning and collision of power-ups.
     *
     * @param root the root group in the scene where power-ups are displayed
     * @param user the UserPlane instance that will collect the power-ups
     */
    public PowerUpManager(Group root, UserPlane user) {
        this.root = root;
        this.user = user;
        this.activePowerUps = new ArrayList<>();
    }

    /**
     * Spawns a new power-up (a heart) at a random location within the defined bounds,
     * with a certain spawn chance.
     */
    public void spawnPowerUp() {
        if (activePowerUps.isEmpty() && new Random().nextInt(100) < SPAWN_CHANCE) {
            double x = new Random().nextDouble() * (X_RIGHT_BOUND - 50);
            double y = new Random().nextDouble() * (Y_LOWER_BOUND - Y_UPPER_BOUND - 50) + Y_UPPER_BOUND;
            GainHeart gainHeart = new GainHeart(x, y, user);
            root.getChildren().add(gainHeart);
            activePowerUps.add(gainHeart);
        }
    }

    /**
     * Updates the list of active power-ups by removing any that have been destroyed.
     */
    public void updatePowerUps() {
        activePowerUps.removeIf(powerUp -> {
            if (powerUp.isDestroyed()) {
                root.getChildren().remove(powerUp);
                return true;
            }
            return false;
        });
    }

    /**
     * Checks if any of the active power-ups have collided with the user's plane.
     * If a collision occurs, the corresponding power-up (e.g., heart) is activated.
     */
    public void checkForCollisions() {
        List<ActiveActorDestructible> toRemove = new ArrayList<>();
        for (ActiveActorDestructible powerUp : activePowerUps) {
            if (powerUp.getBoundsInParent().intersects(user.getBoundsInParent())) {
                if (powerUp instanceof GainHeart) {
                    ((GainHeart) powerUp).plusHealth();
                    toRemove.add(powerUp);
                }
            }
        }
        activePowerUps.removeAll(toRemove);
        root.getChildren().removeAll(toRemove);
    }

    /**
     * Returns a list of currently active power-ups.
     *
     * @return a list of active power-ups
     */
    public List<ActiveActorDestructible> getActivePowerUps() {
        return activePowerUps;
    }
}
