package com.example.demo.menus;

import javafx.stage.Stage;

public class HomeMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/homemenu.png";

    public HomeMenu(Stage stage, double screenHeight, double screenWidth, double zoomFactor) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, zoomFactor);
    }
}