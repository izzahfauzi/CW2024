package com.example.demo.projectiles;

/**
 * The BossProjectile class represents a projectile fired by the boss in the game.
 * It extends the Projectile class and defines its specific behaviour, such as
 * movement speed and starting position.
 */
public class BossProjectile extends Projectile {
	private static final String IMAGE_NAME = "fireboss.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a BossProjectile with the specified initial Y position.
	 *
	 * @param initialYPos the initial Y position of the projectile when it is created
	 */
	public BossProjectile(double initialYPos) {
		// Call the constructor of the superclass (Projectile) with the image, height, initial X and Y positions
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the boss's projectile, moving it horizontally
	 * at the specified velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	/**
	 * Updates the state of the projectile, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
