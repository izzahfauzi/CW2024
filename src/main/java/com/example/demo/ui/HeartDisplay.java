package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeartDisplay {
	
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/hearts2.png";
	private static final int HEART_HEIGHT = 50;
	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int numberOfHeartsToDisplay;
	
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}
	
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}
	
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	public void updateHeartDisplay(int heartsRemaining) {
		int currentNumberOfHearts = container.getChildren().size();

		for (int i = currentNumberOfHearts; i < heartsRemaining; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}

		while (container.getChildren().size() > heartsRemaining) {
			container.getChildren().remove(container.getChildren().size() - 1);
		}
	}


	public HBox getContainer() {
		return container;
	}

}
