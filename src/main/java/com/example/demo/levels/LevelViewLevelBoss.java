package com.example.demo.levels;

import com.example.demo.ui.ShieldDisplay;
import com.example.demo.ui.BossHealthDisplay;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

public class LevelViewLevelBoss extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private static final double HEALTH_BAR_X_POSITION = 975;
	private static final double HEALTH_BAR_Y_POSITION = 35;
	private final Group root;
	private final ShieldDisplay shieldDisplay;
	private final BossHealthDisplay bossHealthDisplay;
	private Text bossHealthText;

	public LevelViewLevelBoss(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldDisplay = new ShieldDisplay(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthDisplay = new BossHealthDisplay(HEALTH_BAR_X_POSITION, HEALTH_BAR_Y_POSITION, 100);

		initializeBossHealthText();
	}

	private void initializeBossHealthText() {
		bossHealthText = new Text("BOSS HEALTH:");
		bossHealthText.setFont(Font.font("Arial", FontWeight.BOLD,21));
		bossHealthText.setFill(Color.web("#2e464d"));
		bossHealthText.setX(805);
		bossHealthText.setY(53);
	}

	public void showBossHealthDisplay() {
		if (!root.getChildren().contains(bossHealthDisplay.getContainer())) {
			root.getChildren().add(bossHealthDisplay.getContainer());
		}

		if (!root.getChildren().contains(bossHealthText)) {
			root.getChildren().add(bossHealthText);
		}
	}

	public void updateBossHealth(double newHealth) {
		bossHealthDisplay.updateHealth(newHealth);
	}

	private void addImagesToRoot() {
		if (!root.getChildren().contains(shieldDisplay)) {
			root.getChildren().addAll(shieldDisplay);
		}
	}
	
	public void showShield() {
		shieldDisplay.showShield();
		addImagesToRoot();
	}

	public void hideShield() {
		shieldDisplay.hideShield();
	}

	public void updateShieldPosition(double xPosition, double yPosition){
		shieldDisplay.updatePosition(xPosition, yPosition);
	}

}
