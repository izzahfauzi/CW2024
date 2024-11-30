package com.example.demo.menus;

import javafx.stage.Stage;

public class LoseMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/losemenu.png";

    public LoseMenu(Stage stage, double screenHeight, double screenWidth, double zoomFactor) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, zoomFactor);
    }
}