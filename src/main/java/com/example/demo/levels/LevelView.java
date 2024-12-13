package com.example.demo.levels;

import com.example.demo.ui.HeartDisplay;
import javafx.scene.Group;

/**
 * Represents the view of a level in the game, including the heart display for the player's health.
 * This class handles the display of health hearts and updates to the health status during gameplay.
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private final Group root;
	private final HeartDisplay heartDisplay;

	/**
	 * Constructor for initializing the level view with the root group and the number of hearts to display.
	 *
	 * @param root            the root group that contains all UI elements
	 * @param heartsToDisplay the initial number of hearts to display for the player's health
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
	}

	/**
	 * Displays the heart display container in the root group if it is not already added.
	 * This ensures that the health hearts are visible on the screen.
	 */
	public void showHeartDisplay() {
		if (!root.getChildren().contains(heartDisplay.getContainer())) {
			root.getChildren().add(heartDisplay.getContainer());
		}
	}

	/**
	 * Updates the heart display with the current number of remaining hearts.
	 *
	 * @param heartsRemaining the number of hearts remaining to be displayed
	 */
	public void updateHearts(int heartsRemaining) {
		heartDisplay.updateHeartDisplay(heartsRemaining);
	}
}
