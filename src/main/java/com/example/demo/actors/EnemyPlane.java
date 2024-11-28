package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.projectiles.EnemyProjectile;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane1.png";
	private static final int IMAGE_HEIGHT = 60;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;
	private final double screenWidth = 1300;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		if (getLayoutX() + getTranslateX() < 0 || getLayoutX() + getTranslateX() > getScreenWidth()) {
			this.destroy();
		}
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
