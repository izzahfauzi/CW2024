package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.projectiles.EnemyProjectile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnemyPlane extends FighterPlane {

	private static final String ENEMY_IMAGE = "enemyplane1.png";
	private static final int IMAGE_HEIGHT = 60;
	private static final int INITIAL_HORIZONTAL_VELOCITY = -6;
	private static final int SPECIAL_HORIZONTAL_VELOCITY = -9;
	private static final int TANK_HORIZONTAL_VELOCITY = -4;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int Y_POSITION_UPPER_BOUND = 65;
	private static final int Y_POSITION_LOWER_BOUND = 650;
	private static final int INITIAL_HEALTH = 1;
	private static final int SPECIAL_INITIAL_HEALTH = 3;
	private static final int TANK_INITIAL_HEALTH = 7;
	private static final double FIRE_RATE = .01;
	private final double screenWidth = 1300;
	private boolean specialEnemy;
	private static final int VERTICAL_VELOCITY = 5;
	private List<Integer> movePattern;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;

	public EnemyPlane(double initialXPos, double initialYPos, boolean specialEnemy, boolean tankEnemy) {
		super(ENEMY_IMAGE, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		this.specialEnemy = specialEnemy;

		initializeMovePattern();

		if (tankEnemy) {
			setTankPlaneAttributes();
		} else if (specialEnemy) {
			setSpecialEnemyAttributes();
		} else {
			setNormalEnemyAttributes();
		}
	}

	private void setNormalEnemyAttributes() {
		this.health = INITIAL_HEALTH;
		this.horizontalVelocity = INITIAL_HORIZONTAL_VELOCITY;
	}

	private void setSpecialEnemyAttributes() {
		this.health = SPECIAL_INITIAL_HEALTH;
		this.horizontalVelocity = SPECIAL_HORIZONTAL_VELOCITY;
	}

	private void setTankPlaneAttributes() {
		this.health = TANK_INITIAL_HEALTH;
		this.horizontalVelocity = TANK_HORIZONTAL_VELOCITY;
	}

	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);

		if (specialEnemy) {
			moveVertically(getNextMove());
		}

		if (getLayoutX() + getTranslateX() < 0 || getLayoutX() + getTranslateX() > getScreenWidth()) {
			this.destroy();
		}
	}

	private void moveVertically(int verticalVelocity) {
		double initialTranslateY = getTranslateY();
		setTranslateY(getTranslateY() + verticalVelocity);

		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	private void initializeMovePattern() {
		movePattern = new ArrayList<>();
		movePattern.add(VERTICAL_VELOCITY);
		movePattern.add(-VERTICAL_VELOCITY);
		movePattern.add(0);
		Collections.shuffle(movePattern);
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;

		if (consecutiveMovesInSameDirection == 10) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}

		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}

		return currentMove;
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
