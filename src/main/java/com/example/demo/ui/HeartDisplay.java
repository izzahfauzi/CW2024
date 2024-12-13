package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The HeartDisplay class manages the display of heart images for the player.
 * It shows a series of heart images representing the player's current health.
 */
public class HeartDisplay {
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/hearts2.png";
	private static final int HEART_HEIGHT = 50;
	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int numberOfHeartsToDisplay;
	/**
	 * Constructs a HeartDisplay instance with the given position and initial number of hearts to display.
	 *
	 * @param xPosition The X position of the heart display
	 * @param yPosition The Y position of the heart display
	 * @param heartsToDisplay The number of hearts to initially display
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container that holds the heart images.
	 * Sets the position of the container on the screen.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the heart images by creating a number of heart ImageViews
	 * based on the specified number of hearts to display.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Updates the heart display by adding or removing hearts based on the number of hearts remaining.
	 * This method ensures that the display matches the player's current health.
	 *
	 * @param heartsRemaining The number of hearts to display based on the player's remaining health
	 */
	public void updateHeartDisplay(int heartsRemaining) {
		int currentNumberOfHearts = container.getChildren().size();
		for (int i = currentNumberOfHearts; i < heartsRemaining; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
		for (int i = currentNumberOfHearts - 1; i >= heartsRemaining; i--) {
			if (i >= 0 && i < container.getChildren().size()) {
				container.getChildren().remove(i);
			}
		}
	}

	/**
	 * Returns the container holding the heart images.
	 *
	 * @return The container containing the heart images
	 */
	public HBox getContainer() {
		return container;
	}
}
