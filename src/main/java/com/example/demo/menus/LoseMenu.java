package com.example.demo.menus;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class LoseMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/losemenu.png";
    private static  final String HOME_ICON = "/com/example/demo/images/Buttons/Home.png";

    public LoseMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
        initializeControls();
    }

    @Override
    protected void initializeControls() {
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.SPACE) {
                    goToNextLevel("LevelOne");
                }
            }
        });

        double posX = screenWidth - 745;
        double posY = 9;

        double homeButtonWidth = 110;
        double homeButtonHeight = 50;

        buttonImage(HOME_ICON, null, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToMenu("HomeMenu");
            }
        },posX, posY, homeButtonWidth, homeButtonHeight);
    }
}