package com.example.demo.levels;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/sky2.png";
    private static final int TOTAL_ENEMIES = 7;
    private static final int KILLS_TO_ADVANCE = 15;
    private static final double ENEMY_SPAWN_PROBABILITY = .25;
    private static final double SPECIAL_ENEMY_PROBABILITY = 0.50;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget())
            goToNextLevel("LevelBoss");
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
                    ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, isSpecialEnemy);
                    addEnemyUnit(newEnemy);
                }
            }
        }
    }
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

}
