package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The ShieldDisplay class represents a visual shield that can be displayed and positioned on the screen.
 * It provides methods to show or hide the shield and update its position.
 */
public class ShieldDisplay extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield1.png";
	private static final int SHIELD_SIZE = 75;

	/**
	 * Constructs a ShieldDisplay instance at a specified position with an initial shield image.
	 *
	 * @param xPosition The X position of the shield on the screen
	 * @param yPosition The Y position of the shield on the screen
	 */
	public ShieldDisplay(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Makes the shield visible on the screen.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the shield from the screen.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

	/**
	 * Updates the position of the shield on the screen.
	 *
	 * @param xPosition The new X position of the shield
	 * @param yPosition The new Y position of the shield
	 */
	public void updatePosition(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
}
