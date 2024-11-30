package com.example.demo.menus;

import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;

public class HomeMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/homemenu.png";
    private static final String PLAY_BUTTON1 = "/com/example/demo/images/Buttons/play2.png";
    private static final String PLAY_BUTTON2 = "/com/example/demo/images/Buttons/play1.png";

    public HomeMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);

        double posX = (stage.getWidth() - 600) / 2;
        double posY = (stage.getHeight() - 100) / 2;

        double playButtonWidth = 549;
        double playButtonHeight = 48;

        buttonImage(PLAY_BUTTON1, PLAY_BUTTON2, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToNextLevel("LevelOne");
            }
        }, posX, posY, playButtonWidth, playButtonHeight);
    }


}