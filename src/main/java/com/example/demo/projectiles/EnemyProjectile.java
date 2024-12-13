package com.example.demo.projectiles;

/**
 * The EnemyProjectile class represents a projectile fired by an enemy in the game.
 * It extends the Projectile class and defines its specific behaviour, such as
 * movement speed and starting position.
 */
public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = "fireenemy.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs an EnemyProjectile with the specified initial X and Y positions.
	 *
	 * @param initialXPos the initial X position of the projectile when it is created
	 * @param initialYPos the initial Y position of the projectile when it is created
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the enemy's projectile, moving it horizontally
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
