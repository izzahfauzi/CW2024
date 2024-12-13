package com.example.demo.menus;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * The InfoMenu class represents the information menu in the game.
 * It extends the MenuParent class and provides functionality for the
 * information screen, including background image, background music,
 * and controls such as a button to navigate back to the home menu.
 */
public class InfoMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/infomenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/Copycat.wav";
    private static final String HOME_ICON = "/com/example/demo/images/Buttons/Home.png";

    /**
     * Constructs an InfoMenu object, initializing the menu with the specified stage,
     * screen dimensions, and background music.
     *
     * @param stage the main stage of the application
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public InfoMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, BACKGROUND_MUSIC);
        initializeControls();
    }

    /**
     * Initializes the controls for the info menu, such as the home button.
     * The home button is placed at the top right of the screen and triggers
     * navigation back to the home menu when clicked.
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