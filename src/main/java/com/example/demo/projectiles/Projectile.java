package com.example.demo.projectiles;

import com.example.demo.ActiveActorDestructible;

/**
 * The Projectile class is an abstract representation of a projectile in the game.
 * It extends the ActiveActorDestructible class and defines common behaviour for all projectiles,
 * including taking damage and moving. Specific projectiles, such as enemy or boss projectiles,
 * will extend this class and implement their own movement logic.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a new Projectile with the specified image name, image height,
	 * and initial position.
	 *
	 * @param imageName the name of the image representing the projectile
	 * @param imageHeight the height of the projectile image
	 * @param initialXPos the initial X position of the projectile
	 * @param initialYPos the initial Y position of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the damage taken by the projectile. In this case, the projectile is destroyed
	 * when it takes damage.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Abstract method to update the position of the projectile. This method must be
	 * implemented by subclasses to define how the projectile moves in the game.
	 */
	@Override
	public abstract void updatePosition();

}
