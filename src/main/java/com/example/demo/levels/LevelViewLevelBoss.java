package com.example.demo.levels;

import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

public class LevelViewLevelBoss extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;
	
	public LevelViewLevelBoss(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
	}

	private void addImagesToRoot() {
		if (!root.getChildren().contains(shieldImage)) {
			root.getChildren().addAll(shieldImage);
		}
	}
	
	public void showShield() {
		shieldImage.showShield();
		addImagesToRoot();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

	public void updateShieldPosition(double xPosition, double yPosition){
		shieldImage.updatePosition(xPosition, yPosition);
	}

}
