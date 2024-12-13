package com.example.demo.menus;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * The WinMenu class represents the menu displayed when the player wins the game.
 * It provides a button to navigate back to the home menu.
 */
public class WinMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/winmenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/In the Garden.wav";
    private static final String HOME_ICON = "/com/example/demo/images/Buttons/Home.png";

    /**
     * Constructs a WinMenu object. Initializes the background image and music,
     * and sets up the controls for the win menu.
     *
     * @param stage the main stage of the application
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public WinMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, BACKGROUND_MUSIC);
        initializeControls();
    }

    /**
     * Initializes the controls for the win menu.
     * Sets up the home button to navigate back to the home menu.
     */
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