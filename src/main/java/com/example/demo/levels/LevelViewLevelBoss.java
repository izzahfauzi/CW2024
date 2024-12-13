package com.example.demo.levels;

import com.example.demo.ui.ShieldDisplay;
import com.example.demo.ui.BossHealthDisplay;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

/**
 * Represents the view for the level with a boss, including the display of the boss's health,
 * shield, and other elements specific to the boss fight.
 * This class extends the LevelView class and adds additional functionality to show and update
 * the boss's health and shield during the boss level.
 */
public class LevelViewLevelBoss extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private static final double HEALTH_BAR_X_POSITION = 975;
	private static final double HEALTH_BAR_Y_POSITION = 35;
	private final Group root;
	private final ShieldDisplay shieldDisplay;
	private final BossHealthDisplay bossHealthDisplay;
	private Text bossHealthText;

	/**
	 * Constructor for initializing the level boss view with the root group and the number of hearts to display.
	 * It also initializes the shield and boss health displays.
	 *
	 * @param root            the root group that contains all UI elements
	 * @param heartsToDisplay the initial number of hearts to display for the player's health
	 */
	public LevelViewLevelBoss(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldDisplay = new ShieldDisplay(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthDisplay = new BossHealthDisplay(HEALTH_BAR_X_POSITION, HEALTH_BAR_Y_POSITION, 100);

		initializeBossHealthText();
	}

	/**
	 * Initializes the text displaying the boss's health label.
	 * The text "BOSS HEALTH:" is styled and positioned on the screen.
	 */
	private void initializeBossHealthText() {
		bossHealthText = new Text("BOSS HEALTH:");
		bossHealthText.setFont(Font.font("Arial", FontWeight.BOLD,21));
		bossHealthText.setFill(Color.web("#2e464d"));
		bossHealthText.setX(805);
		bossHealthText.setY(53);
	}

	/**
	 * Displays the boss health bar and the associated text "BOSS HEALTH:" in the root group.
	 * Adds the health display to the root group if it's not already present.
	 */
	public void showBossHealthDisplay() {
		if (!root.getChildren().contains(bossHealthDisplay.getContainer())) {
			root.getChildren().add(bossHealthDisplay.getContainer());
		}

		if (!root.getChildren().contains(bossHealthText)) {
			root.getChildren().add(bossHealthText);
		}
	}

	/**
	 * Updates the boss's health displayed on the health bar.
	 *
	 * @param newHealth the new health value to display for the boss
	 */
	public void updateBossHealth(double newHealth) {
		bossHealthDisplay.updateHealth(newHealth);
	}

	/**
	 * Adds the shield display image to the root group if it is not already present.
	 * Ensures that the shield image is visible during the boss fight.
	 */
	private void addImagesToRoot() {
		if (!root.getChildren().contains(shieldDisplay)) {
			root.getChildren().addAll(shieldDisplay);
		}
	}

	/**
	 * Displays the shield on the screen.
	 * Ensures the shield is visible during the boss fight.
	 */
	public void showShield() {
		shieldDisplay.showShield();
		addImagesToRoot();
	}

	/**
	 * Hides the shield from the screen.
	 * This is used when the shield is no longer needed or is destroyed.
	 */
	public void hideShield() {
		shieldDisplay.hideShield();
	}

	/**
	 * Updates the position of the shield on the screen.
	 *
	 * @param xPosition the new x-coordinate for the shield
	 * @param yPosition the new y-coordinate for the shield
	 */
	public void updateShieldPosition(double xPosition, double yPosition){
		shieldDisplay.updatePosition(xPosition, yPosition);
	}
}
