package com.example.demo.levels;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/sky2.png";
    private static final int TOTAL_ENEMIES = 7;
    private static final int KILLS_TO_ADVANCE = 20;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.25;
    private static final double SPECIAL_ENEMY_PROBABILITY = 0.25;
    private static final double TANK_ENEMY_PROBABILITY = 0.50;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            showTransitionPrompt("TransitionThree");
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        if (!getRoot().getChildren().contains(getUser())) {
            getRoot().getChildren().add(getUser());
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (getUser().getNumberOfKills() < KILLS_TO_ADVANCE) {
            int totalEnemiesToSpawn = TOTAL_ENEMIES - getCurrentNumberOfEnemies();
            for (int i = 0; i < totalEnemiesToSpawn; i++) {
                if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                    double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();

                    boolean isSpecialEnemy = Math.random() < SPECIAL_ENEMY_PROBABILITY;
                    boolean isTankEnemy = Math.random() < TANK_ENEMY_PROBABILITY;

                    if (isSpecialEnemy || isTankEnemy) {
                        ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, isSpecialEnemy, isTankEnemy);
                        addEnemyUnit(newEnemy);
                    }
                }
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected LevelViewLevelBoss instantiateLevelViewLevelBoss() {
        return null;
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

}
