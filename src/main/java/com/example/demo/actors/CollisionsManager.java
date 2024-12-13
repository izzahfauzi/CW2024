package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.audio.SoundEffectsManager;
import com.example.demo.powerup.PowerUpManager;
import com.example.demo.powerup.GainHeart;
import javafx.scene.Group;

import java.util.List;

/**
 * Manages the collisions between various game entities such as the user's plane, enemy units, projectiles, and power-ups.
 * <p>
 * The {@link CollisionsManager} class is responsible for handling the interactions between different game objects
 * that result in collisions, such as the user's plane colliding with enemy units, projectiles hitting their targets,
 * and the player collecting power-ups. It also plays the appropriate sound effects when collisions occur.
 * </p>
 */
public class CollisionsManager {

    private static final String HIT = "/com/example/demo/audios/sound effects/Hurt.wav";
    private static final String ENEMY_HIT = "/com/example/demo/audios/sound effects/Hurt.wav";
    private static final String POWER_UP = "/com/example/demo/audios/sound effects/Boost.wav";

    private final Group root;
    private final UserPlane user;
    private final SoundEffectsManager soundEffectsManager;
    private final PowerUpManager powerUpManager;

    /**
     * Constructs a new {@link CollisionsManager} to handle collisions for the specified game entities.
     *
     * @param root the root group of the game scene, used to remove power-ups when collected
     * @param user the player's plane, used to check for collisions with other units and power-ups
     * @param soundEffectsManager the manager responsible for playing sound effects when collisions occur
     * @param powerUpManager the manager responsible for managing power-ups in the game
     */
    public CollisionsManager(Group root, UserPlane user, SoundEffectsManager soundEffectsManager, PowerUpManager powerUpManager) {
        this.root = root;
        this.user = user;
        this.soundEffectsManager = soundEffectsManager;
        this.powerUpManager = powerUpManager;
    }

    /**
     * Handles collisions between friendly units and enemy units.
     * When a collision is detected, both units take damage.
     *
     * @param friendlyUnits the list of friendly units (e.g., player's plane)
     * @param enemyUnits the list of enemy units (e.g., enemy planes)
     */
    public void handlePlaneCollisions(List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits) {
        for (ActiveActorDestructible friendly : friendlyUnits) {
            for (ActiveActorDestructible enemy : enemyUnits) {
                if (friendly.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    soundEffectsManager.playSound(HIT);
                    if (enemy instanceof FighterPlane) {
                        enemy.takeDamage();
                        friendly.takeDamage();
                    }
                }
            }
        }
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     * When a collision occurs, the enemy takes damage, and the projectile is destroyed.
     * If the enemy is destroyed, the player's kill count is incremented.
     *
     * @param userProjectiles the list of projectiles fired by the user
     * @param enemyUnits the list of enemy units
     */
    public void handleUserProjectileCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyUnits) {
        for (ActiveActorDestructible projectile : userProjectiles) {
            for (ActiveActorDestructible enemy : enemyUnits) {
                if (projectile.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.takeDamage();
                    projectile.destroy();
                    soundEffectsManager.playSound(ENEMY_HIT);

                    if (enemy.isDestroyed()) {
                        user.incrementKillCount();
                    }
                }
            }
        }
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     *
     * @param enemyProjectiles the list of projectiles fired by the enemy
     * @param friendlyUnits the list of friendly units (e.g., player's plane)
     */
    public void handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> friendlyUnits) {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Handles collisions between two lists of actors.
     * When a collision occurs, both actors take damage.
     *
     * @param actors1 the first list of actors
     * @param actors2 the second list of actors
     */
    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                    soundEffectsManager.playSound(HIT);
                }
            }
        }
    }

    /**
     * Handles collisions between the user and power-ups.
     * When a power-up collides with the user, it is removed, and the corresponding power-up effect is applied
     */
    public void handlePowerUpCollisions() {
        List<ActiveActorDestructible> toRemove = powerUpManager.getActivePowerUps().stream()
                .filter(powerUp -> user.getBoundsInParent().intersects(powerUp.getBoundsInParent()))
                .toList();

        for (ActiveActorDestructible powerUp : toRemove) {
            if (powerUp instanceof GainHeart) {
                user.gainHealth(1);
                soundEffectsManager.playSound(POWER_UP);
            }
            powerUpManager.getActivePowerUps().remove(powerUp);
            root.getChildren().remove(powerUp);
        }
    }

    /**
     * Handles enemy units that penetrate past the screen width (i.e., go beyond the player's plane).
     * If an enemy crosses the screen width, the user takes damage, and the enemy is destroyed.
     *
     * @param enemyUnits the list of enemy units
     * @param screenWidth the width of the game screen, used to detect if an enemy has penetrated past it
     */
    public void handleEnemyPenetration(List<ActiveActorDestructible> enemyUnits, double screenWidth) {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemy.getLayoutX() + enemy.getTranslateX() > screenWidth) {
                user.takeDamage();
                enemy.destroy();
                soundEffectsManager.playSound(HIT);
            }
        }
    }
}
