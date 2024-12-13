package com.example.demo.actors;

import javafx.scene.image.*;

/**
 * An abstract class representing an active actor in the game, extending the {@link javafx.scene.image.ImageView} class.
 * <p>
 * This class provides common functionality for actors that have an image, such as setting their initial position,
 * size, and providing movement methods. It is intended to be extended by specific types of actors in the game
 * </p>
 */
public abstract class ActiveActor extends ImageView {

	/**
	 * The location where images for the actors are stored.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs a new {@code ActiveActor} with the specified image, initial position, and size.
	 * <p>
	 * This constructor sets the image of the actor, its initial horizontal and vertical positions,
	 * and its height. It also ensures that the image maintains its aspect ratio.
	 * </p>
	 *
	 * @param imageName    the name of the image to be used for the actor (should be located in the
	 *                     {@code /com/example/demo/images/} directory)
	 * @param imageHeight  the height of the image to be displayed for the actor
	 * @param initialXPos  the initial horizontal position of the actor
	 * @param initialYPos  the initial vertical position of the actor
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Abstract method to update the position of the actor. This method should be implemented by any subclass
	 * of {@code ActiveActor} to provide the actor's movement logic.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by a specified amount.
	 * <p>
	 * This method adjusts the actor's current position along the X-axis by the given value.
	 * </p>
	 *
	 * @param horizontalMove the distance to move the actor horizontally
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified amount.
	 * <p>
	 * This method adjusts the actor's current position along the Y-axis by the given value.
	 * </p>
	 *
	 * @param verticalMove the distance to move the actor vertically
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
