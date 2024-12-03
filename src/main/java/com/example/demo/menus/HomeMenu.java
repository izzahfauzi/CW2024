package com.example.demo.menus;

import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class HomeMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/homemenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/Copycat.wav";
    private static final String PLAY_BUTTON1 = "/com/example/demo/images/Buttons/play2.png";
    private static final String PLAY_BUTTON2 = "/com/example/demo/images/Buttons/play1.png";
    private static final String EXIT_BUTTON1 = "/com/example/demo/images/Buttons/exit1.png";
    private static final String EXIT_BUTTON2 = "/com/example/demo/images/Buttons/exit2.png";

    public HomeMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, BACKGROUND_MUSIC);

        double posX = (stage.getWidth() - 600) / 2;
        double playPosY = (stage.getHeight() - 175) / 2;

        double playButtonWidth = 549;
        double playButtonHeight = 48;

        buttonImage(PLAY_BUTTON1, PLAY_BUTTON2, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToNextLevel("LevelOne");
            }
        }, posX, playPosY, playButtonWidth, playButtonHeight);

        double exitButtonWidth = 549;
        double exitButtonHeight = 48;

        double exitPosY = (stage.getHeight() - 0) / 2;

        buttonImage(EXIT_BUTTON1, EXIT_BUTTON2, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitGame();
            }
        }, posX, exitPosY, exitButtonWidth, exitButtonHeight);

    }

    private void exitGame() {
        System.exit(0);
    }

}