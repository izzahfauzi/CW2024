package com.example.demo.projectiles;

/**
 * The UserProjectile class represents a projectile fired by the player's plane in the game.
 * It extends the Projectile class and implements the specific behaviour for the user's projectile,
 * including its movement logic.
 */
public class UserProjectile extends Projectile {
	private static final String IMAGE_NAME = "torpedo.png";
	private static final int IMAGE_HEIGHT = 25;
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a new UserProjectile with the specified initial position.
	 * This constructor passes the image, height, and initial position to the superclass (Projectile).
	 *
	 * @param initialXPos the initial X position of the user's projectile
	 * @param initialYPos the initial Y position of the user's projectile
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the user's projectile. The projectile moves horizontally
	 * at a constant velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	/**
	 * Updates the actor, which in this case is the user's projectile.
	 * This method calls the updatePosition() method to move the projectile.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
