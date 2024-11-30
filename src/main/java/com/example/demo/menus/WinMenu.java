package com.example.demo.menus;

import javafx.stage.Stage;

public class WinMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/winmenu.png";

    public WinMenu(Stage stage, double screenHeight, double screenWidth, double zoomFactor) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, zoomFactor);
    }
}