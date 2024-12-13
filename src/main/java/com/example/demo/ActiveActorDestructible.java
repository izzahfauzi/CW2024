package com.example.demo;

import com.example.demo.actors.ActiveActor;

/**
 * The ActiveActorDestructible class extends ActiveActor and implements the Destructible interface.
 * It represents an active actor in the game that can be destroyed, tracking its destruction state.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	/**
	 * Constructs an ActiveActorDestructible with the specified image, position, and height.
	 *
	 * @param imageName The name of the image representing the actor
	 * @param imageHeight The height of the actor's image
	 * @param initialXPos The initial X position of the actor
	 * @param initialYPos The initial Y position of the actor
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Abstract method to update the actor's position.
	 * Subclasses must implement this method to define how the position is updated.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Abstract method to update the actor.
	 * Subclasses must implement this method to define how the actor is updated (e.g., animation, logic).
	 */
	public abstract void updateActor();

	/**
	 * Abstract method to handle the actor taking damage.
	 * Subclasses must implement this method to define what happens when the actor takes damage.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed.
	 * This method is called to update the state of the actor to reflect its destruction.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed state of the actor.
	 *
	 * @param isDestroyed The new destruction state of the actor
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Returns the destruction state of the actor.
	 *
	 * @return true if the actor is destroyed, false otherwise
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
}
