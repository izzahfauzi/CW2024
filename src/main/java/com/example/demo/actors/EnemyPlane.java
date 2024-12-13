package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.projectiles.EnemyProjectile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an enemy plane in the game, extending the {@link FighterPlane} class.
 * <p>
 * The {@link EnemyPlane} class is responsible for the movement, firing, and behaviour of the enemy planes in the game.
 * It includes different types of enemy planes (normal, special, and tank) with varying health, velocity, and movement patterns.
 * </p>
 */
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

	/**
	 * Constructs an enemy plane with the specified initial position and type (normal, special, or tank).
	 *
	 * @param initialXPos the initial horizontal position of the enemy plane
	 * @param initialYPos the initial vertical position of the enemy plane
	 * @param specialEnemy whether the enemy is a special type
	 * @param tankEnemy whether the enemy is a tank type
	 */
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

	/**
	 * Sets the attributes for a normal enemy plane, including its health and horizontal velocity.
	 */
	private void setNormalEnemyAttributes() {
		this.health = INITIAL_HEALTH;
		this.horizontalVelocity = INITIAL_HORIZONTAL_VELOCITY;
	}

	/**
	 * Sets the attributes for a special enemy plane, including its health and horizontal velocity.
	 */
	private void setSpecialEnemyAttributes() {
		this.health = SPECIAL_INITIAL_HEALTH;
		this.horizontalVelocity = SPECIAL_HORIZONTAL_VELOCITY;
	}

	/**
	 * Sets the attributes for a tank enemy plane, including its health and horizontal velocity.
	 */
	private void setTankPlaneAttributes() {
		this.health = TANK_INITIAL_HEALTH;
		this.horizontalVelocity = TANK_HORIZONTAL_VELOCITY;
	}

	/**
	 * Updates the position of the enemy plane, moving it horizontally and vertically (if it's a special enemy).
	 * If the enemy plane goes out of bounds, it is destroyed.
	 */
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

	/**
	 * Moves the enemy plane vertically within the screen bounds.
	 * If the plane goes out of the allowed vertical range, it resets its vertical position.
	 *
	 * @param verticalVelocity the velocity used for vertical movement
	 */
	private void moveVertically(int verticalVelocity) {
		double initialTranslateY = getTranslateY();
		setTranslateY(getTranslateY() + verticalVelocity);

		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Initializes the move pattern for a special enemy plane, which can move up, down, or stay still.
	 */
	private void initializeMovePattern() {
		movePattern = new ArrayList<>();
		movePattern.add(VERTICAL_VELOCITY);
		movePattern.add(-VERTICAL_VELOCITY);
		movePattern.add(0);
		Collections.shuffle(movePattern);
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
	}

	/**
	 * Determines the next vertical movement based on the current move pattern.
	 * The move pattern is shuffled after a set number of consecutive moves in the same direction.
	 *
	 * @return the next vertical velocity in the move pattern
	 */
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

	/**
	 * Returns the screen width used to check if the enemy plane is out of bounds.
	 *
	 * @return the screen width
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Fires a projectile from the enemy plane.
	 * If the random chance is below the fire rate, a new {@link EnemyProjectile} is created and returned.
	 *
	 * @return a new enemy projectile or null if the fire rate condition is not met
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the enemy plane by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
