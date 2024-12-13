package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;

/**
 * Represents a generic fighter plane, extending {@link ActiveActorDestructible}.
 * <p>
 * The {@link FighterPlane} class serves as a base class for all fighter plane types (e.g., enemy planes, user-controlled planes).
 * It includes common properties like health, horizontal velocity, and methods for taking damage and firing projectiles.
 * </p>
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	protected int health;
	protected int horizontalVelocity;

	/**
	 * Constructs a fighter plane with the specified attributes.
	 *
	 * @param imageName the image file name representing the fighter plane
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial horizontal position of the fighter plane
	 * @param initialYPos the initial vertical position of the fighter plane
	 * @param health the initial health of the fighter plane
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the fighter plane.
	 * This method must be implemented by subclasses to define how the fighter plane fires projectiles.
	 *
	 * @return a new {@link ActiveActorDestructible} representing the fired projectile
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the health of the fighter plane by one.
	 * If the health reaches zero or below, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero() || health < 0) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X position of a projectile relative to the fighter plane.
	 *
	 * @param xPositionOffset the horizontal offset to apply to the X position
	 * @return the calculated X position of the projectile
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y position of a projectile relative to the fighter plane.
	 *
	 * @param yPositionOffset the vertical offset to apply to the Y position
	 * @return the calculated Y position of the projectile
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks whether the fighter plane's health is zero.
	 *
	 * @return true if health is zero, otherwise false
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the fighter plane.
	 *
	 * @return the current health of the fighter plane
	 */
	public int getHealth() {
		return health;
	}
}
