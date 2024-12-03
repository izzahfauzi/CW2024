package com.example.demo.levels;

import com.example.demo.actors.Boss;

public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/sky2.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelBoss levelViewLevelBoss;

	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(levelViewLevelBoss);
	}

	@Override
	protected void initializeFriendlyUnits() {
		if (!getRoot().getChildren().contains(getUser())) {
			getRoot().getChildren().add(getUser());
		}
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected LevelViewLevelBoss instantiateLevelViewLevelBoss() {
		levelViewLevelBoss = new LevelViewLevelBoss(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelViewLevelBoss;
	}

}
