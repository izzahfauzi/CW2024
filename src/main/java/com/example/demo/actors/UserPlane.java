package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.projectiles.UserProjectile;

/**
 * Represents the user-controlled plane in the game, extending {@link FighterPlane}.
 * <p>
 * The {@link UserPlane} class is responsible for managing the movement, projectile firing,
 * and health of the player's plane. It includes methods for controlling the plane's
 * vertical and horizontal movement, firing projectiles, and tracking the player's kills.
 * </p>
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane1.png";
	private static final double Y_UPPER_BOUND = 65.0;
	private static final double Y_LOWER_BOUND = 650.0;
	private static final double X_LEFT_BOUND = 0.0;
	private static final double X_RIGHT_BOUND = 1100.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 50;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;

	/**
	 * Constructs a new {@link UserPlane} with the specified initial health.
	 *
	 * @param initialHealth the initial health of the user plane
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Updates the position of the user plane based on its movement direction.
	 * The plane can move vertically and horizontally, with bounds checked
	 * to ensure the plane stays within the screen.
	 */
	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * verticalVelocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}

		if (isMovingHorizontally()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			double newPositionX = getLayoutX() + getTranslateX();
			if (newPositionX < X_LEFT_BOUND || newPositionX > X_RIGHT_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	/**
	 * Updates the state of the user plane by calling {@link #updatePosition()}.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane.
	 *
	 * @return a new {@link UserProjectile} representing the fired projectile
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		double n = 18;
		double m = -5;
		double projectileX = this.getBoundsInParent().getMaxX() + m;
		double projectileY = this.getBoundsInParent().getMinY() + n;
		return new UserProjectile(projectileX, projectileY);
	}

	/**
	 * Checks if the user plane is moving vertically.
	 *
	 * @return true if the plane is moving vertically, otherwise false
	 */
	private boolean isMovingVertically() {
		return verticalVelocityMultiplier != 0;
	}

	/**
	 * Checks if the user plane is moving horizontally.
	 *
	 * @return true if the plane is moving horizontally, otherwise false
	 */
	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	/**
	 * Moves the user plane upwards.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Moves the user plane downwards.
	 */
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	/**
	 * Stops the vertical movement of the user plane.
	 */
	public void stopMoveVertical() {
		verticalVelocityMultiplier = 0;
	}

	/**
	 * Moves the user plane to the left.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Moves the user plane to the right.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops the horizontal movement of the user plane.
	 */
	public void stopMoveHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Gets the current number of kills made by the user.
	 *
	 * @return the number of kills
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
		System.out.println("Kill Count: " + numberOfKills);
	}

	/**
	 * Increases the health of the user plane by a specified amount.
	 * The health cannot exceed a maximum value of 5.
	 *
	 * @param amount the amount to increase health by
	 */
	public void gainHealth(int amount) {
		if (health < 5) {
			health += amount;
			if (health > 5) {
				health = 5;
			}
		}
	}
}
