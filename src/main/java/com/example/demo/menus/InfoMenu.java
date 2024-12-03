package com.example.demo.menus;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class InfoMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/infomenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/Copycat.wav";
    private static  final String HOME_ICON = "/com/example/demo/images/Buttons/Home.png";

    public InfoMenu(Stage stage, double screenHeight, double screenWidth) {
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