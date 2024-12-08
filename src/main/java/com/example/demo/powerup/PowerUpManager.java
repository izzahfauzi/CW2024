package com.example.demo.powerup;

import com.example.demo.actors.UserPlane;
import com.example.demo.ActiveActorDestructible;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUpManager {

    private static final int SPAWN_CHANCE = 10;
    private static final double X_RIGHT_BOUND = 1100.0;
    private static final double Y_UPPER_BOUND = 65.0;
    private static final double Y_LOWER_BOUND = 650.0;
    private final Group root;
    private final UserPlane user;
    private final List<ActiveActorDestructible> activePowerUps;

    public PowerUpManager(Group root, UserPlane user) {
        this.root = root;
        this.user = user;
        this.activePowerUps = new ArrayList<>();
    }

    public void spawnPowerUp() {
        if (activePowerUps.isEmpty() && new Random().nextInt(100) < SPAWN_CHANCE) {
            double x = new Random().nextDouble() * (X_RIGHT_BOUND - 50);
            double y = new Random().nextDouble() * (Y_LOWER_BOUND - Y_UPPER_BOUND - 50) + Y_UPPER_BOUND;

            GainHeart gainHeart = new GainHeart(x, y, user);
            root.getChildren().add(gainHeart);
            activePowerUps.add(gainHeart);
        }
    }

    public void updatePowerUps() {
        activePowerUps.removeIf(powerUp -> {
            if (powerUp.isDestroyed()) {
                root.getChildren().remove(powerUp);
                return true;
            }
            return false;
        });
    }


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

    public List<ActiveActorDestructible> getActivePowerUps() {
        return activePowerUps;
    }
}
