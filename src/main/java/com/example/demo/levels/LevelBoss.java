package com.example.demo.levels;

import com.example.demo.actors.Boss;

/**
 * This class represents the boss level in the game, where the player faces the final boss.
 * <p>
 * It extends {@link LevelParent} and overrides the necessary methods to handle the specific mechanics of the boss level,
 * such as initializing friendly units, checking if the game is over, spawning enemy units, and creating the level view.
 * </p>
 */
public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/sky2.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelBoss levelViewLevelBoss;

	/**
	 * Constructor to initialize the boss level.
	 * <p>
	 * This constructor sets the background image, screen height, screen width, and initializes the boss unit.
	 * </p>
	 *
	 * @param screenHeight the height of the screen.
	 * @param screenWidth the width of the screen.
	 */
	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(levelViewLevelBoss);
	}

	/**
	 * Initializes the friendly units for the level (i.e., the player).
	 * <p>
	 * This method ensures that the player unit is added to the game root if it is not already present.
	 * </p>
	 */
	@Override
	protected void initializeFriendlyUnits() {
		if (!getRoot().getChildren().contains(getUser())) {
			getRoot().getChildren().add(getUser());
		}
	}

	/**
	 * Checks if the game is over by evaluating whether the player or the boss is destroyed.
	 * <p>
	 * If the player is destroyed, the game is lost. If the boss is destroyed, the game is won.
	 * </p>
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	/**
	 * Spawns the enemy units for the level.
	 * <p>
	 * In this level, the only enemy unit is the boss. This method adds the boss to the game when no enemies are present.
	 * </p>
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Instantiates the level view for the game.
	 * <p>
	 * This method creates and returns a new instance of {@link LevelView} for this level.
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
	 * This method creates and returns a new instance of {@link LevelViewLevelBoss} for this level.
	 * </p>
	 *
	 * @return the instantiated boss level view.
	 */
	@Override
	protected LevelViewLevelBoss instantiateLevelViewLevelBoss() {
		levelViewLevelBoss = new LevelViewLevelBoss(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelViewLevelBoss;
	}

}
