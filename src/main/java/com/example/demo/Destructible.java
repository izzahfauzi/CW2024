package com.example.demo;

/**
 * The Destructible interface represents an entity that can take damage and be destroyed.
 * Classes that implement this interface should define how the entity takes damage and how it is destroyed.
 */
public interface Destructible {

	/**
	 * Method to handle the entity taking damage.
	 * This method should be implemented to define what happens when the entity takes damage.
	 */
	void takeDamage();

	/**
	 * Method to destroy the entity.
	 * This method should be implemented to define what happens when the entity is destroyed.
	 */
	void destroy();
}