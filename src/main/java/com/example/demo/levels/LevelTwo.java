package com.example.demo.levels;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;

/**
 * Represents the second level of the game. This class implements the specific gameplay mechanics,
 * including spawning enemy planes, handling progression based on kills, and checking for game over conditions.
 */
public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/sky2.png";
    private static final int TOTAL_ENEMIES = 7;
    private static final int KILLS_TO_ADVANCE = 15;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.25;
    private static final double SPECIAL_ENEMY_PROBABILITY = 0.50;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * Constructor for initializing LevelTwo with the given screen dimensions.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth  the width of the screen
     */
    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the game is over. The game is over if the user's plane is destroyed or if the user has
     * reached the required number of kills to advance to the next level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget()){
            showTransitionPrompt("TransitionTwo");
        }
    }

    /**
     * Initializes the user's friendly units (e.g., user plane) in the game scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        if (!getRoot().getChildren().contains(getUser())) {
            getRoot().getChildren().add(getUser());
        }
    }

    /**
     * Spawns enemy units for this level. The number and type of enemies are based on spawn probabilities
     * and the number of kills the user has achieved.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getUser().getNumberOfKills() < KILLS_TO_ADVANCE) {
            int totalEnemiesToSpawn = TOTAL_ENEMIES - getCurrentNumberOfEnemies();
            for (int i = 0; i < totalEnemiesToSpawn; i++) {
                if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                    double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                    boolean isSpecialEnemy = Math.random() < SPECIAL_ENEMY_PROBABILITY;
                    ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, isSpecialEnemy, false);
                    addEnemyUnit(newEnemy);
                }
            }
        }
    }

    /**
     * Instantiates and returns a LevelView object for this level, including the health display for the user.
     *
     * @return the LevelView object for LevelTwo
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Instantiates and returns the LevelViewLevelBoss for this level. Since this level does not have a boss,
     * this method returns null.
     *
     * @return null, as there is no boss in this level
     */
    @Override
    protected LevelViewLevelBoss instantiateLevelViewLevelBoss() {
        return null;
    }

    /**
     * Checks if the user has reached the required number of kills to advance to the next level.
     *
     * @return true if the user has reached or exceeded the required kills, otherwise false
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

}
