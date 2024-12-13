package com.example.demo.levels;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;

/**
 * Represents the first level of the game, where the player must destroy enemy planes to advance.
 * <p>
 * This class extends {@link LevelParent} and overrides methods to handle the specific mechanics of the first level,
 * including spawning enemy units, checking if the game is over, and advancing to the next level once the player has
 * reached a kill target.
 * </p>
 */
public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/sky2.png";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * Constructor for the first level.
	 * <p>
	 * Sets up the level with the appropriate background, screen size, and initial player health.
	 * </p>
	 *
	 * @param screenHeight the height of the screen.
	 * @param screenWidth the width of the screen.
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the game is over.
	 * <p>
	 * The game is over if the player is destroyed. Additionally, if the player has reached the required number of kills,
	 * it transitions to the next level.
	 * </p>
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			showTransitionPrompt("TransitionOne");
		}
	}

	/**
	 * Initializes the friendly units (the player).
	 * <p>
	 * Ensures that the player unit is added to the game root if it is not already present.
	 * </p>
	 */
	@Override
	protected void initializeFriendlyUnits() {
		if (!getRoot().getChildren().contains(getUser())) {
			getRoot().getChildren().add(getUser());
		}
	}

	/**
	 * Spawns enemy units for the level.
	 * <p>
	 * Enemies are spawned until the total number of enemies reaches the target. The probability of each enemy spawn is
	 * defined by {@link #ENEMY_SPAWN_PROBABILITY}. If the player hasn't reached the kill target, new enemies are spawned.
	 * </p>
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getUser().getNumberOfKills() < KILLS_TO_ADVANCE) {
			int totalEnemiesToSpawn = TOTAL_ENEMIES - getCurrentNumberOfEnemies();
			for (int i = 0; i < totalEnemiesToSpawn; i++) {
				if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
					double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
					ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, false, false);
					addEnemyUnit(newEnemy);
				}
			}
		}
	}

	/**
	 * Instantiates the level view for the game.
	 * <p>
	 * Creates and returns a new instance of {@link LevelView} for this level.
	 * </p>
	 *
	 * @return the instantiated level view.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Instantiates the level view specific to the boss level.
	 * <p>
	 * Since this is not a boss level, this method returns {@code null}.
	 * </p>
	 *
	 * @return {@code null}, as this level does not have a specific boss level view.
	 */
	@Override
	protected LevelViewLevelBoss instantiateLevelViewLevelBoss() {
		return null;
	}

	/**
	 * Checks if the player has reached the target number of kills to advance to the next level.
	 * <p>
	 * This method compares the player's current kill count with the kill target. If the player has reached or exceeded
	 * the kill target, the level is considered complete.
	 * </p>
	 *
	 * @return {@code true} if the player has reached the kill target, otherwise {@code false}.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

}
