package com.example.demo.menus;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class WinMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/winmenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/In the Garden.wav";
    private static  final String HOME_ICON = "/com/example/demo/images/Buttons/Home.png";

    public WinMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, BACKGROUND_MUSIC);
        initializeControls();
    }

    @Override
    protected void initializeControls() {
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