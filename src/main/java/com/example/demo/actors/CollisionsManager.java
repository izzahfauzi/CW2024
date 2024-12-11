package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.audio.SoundEffectsManager;
import com.example.demo.powerup.PowerUpManager;
import com.example.demo.powerup.GainHeart;
import javafx.scene.Group;

import java.util.List;

public class CollisionsManager {

    private static final String HIT = "/com/example/demo/audios/sound effects/Hurt.wav";
    private static final String ENEMY_HIT = "/com/example/demo/audios/sound effects/Hurt.wav";
    private static final String POWER_UP = "/com/example/demo/audios/sound effects/Boost.wav";

    private final Group root;
    private final UserPlane user;
    private final SoundEffectsManager soundEffectsManager;
    private final PowerUpManager powerUpManager;

    public CollisionsManager(Group root, UserPlane user, SoundEffectsManager soundEffectsManager, PowerUpManager powerUpManager) {
        this.root = root;
        this.user = user;
        this.soundEffectsManager = soundEffectsManager;
        this.powerUpManager = powerUpManager;
    }

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

    public void handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> friendlyUnits) {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

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
