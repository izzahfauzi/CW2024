package com.example.demo.menus;

import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

/**
 * Represents the home menu of the game, including buttons for starting the game,
 * navigating to other menus (Info and Settings), and exiting the game.
 * This class extends MenuParent and initializes various UI elements for the home menu.
 */
public class HomeMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/homemenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/Copycat.wav";
    private static final String PLAY_BUTTON1 = "/com/example/demo/images/Buttons/play2.png";
    private static final String PLAY_BUTTON2 = "/com/example/demo/images/Buttons/play1.png";
    private static final String EXIT_BUTTON1 = "/com/example/demo/images/Buttons/exit1.png";
    private static final String EXIT_BUTTON2 = "/com/example/demo/images/Buttons/exit2.png";
    private static final String INFO_ICON = "/com/example/demo/images/Buttons/Question-Mark.png";
    private static final String SETT_ICON = "/com/example/demo/images/Buttons/Gear.png";

    /**
     * Constructor for initializing the home menu with the given stage, screen dimensions,
     * and background music. This method also sets up the buttons for playing the game,
     * exiting the game, and navigating to the info and settings menus.
     *
     * @param stage        the primary stage for the application
     * @param screenHeight the height of the screen
     * @param screenWidth  the width of the screen
     */
    public HomeMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, BACKGROUND_MUSIC);

        double posX = (stage.getWidth() - 600) / 2;
        double playPosY = (stage.getHeight() - 175) / 2;

        double playButtonWidth = 549;
        double playButtonHeight = 48;

        // Set up the "Play" button with a hover effect and a click event handler
        buttonImage(PLAY_BUTTON1, PLAY_BUTTON2, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToNextLevel("LevelOne");
            }
        }, posX, playPosY, playButtonWidth, playButtonHeight);

        double exitButtonWidth = 549;
        double exitButtonHeight = 48;
        double exitPosY = (stage.getHeight() - 0) / 2;

        // Set up the "Exit" button with a click event handler
        buttonImage(EXIT_BUTTON1, EXIT_BUTTON2, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitGame();
            }
        }, posX, exitPosY, exitButtonWidth, exitButtonHeight);

        double INFOPosX = screenWidth - 665;
        double INFOPosY = 9;
        double infoButtonWidth = 110;
        double infoButtonHeight = 50;

        // Set up the "Info" button with a click event handler
        buttonImage(INFO_ICON, null, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToMenu("InfoMenu");
            }
        }, INFOPosX, INFOPosY, infoButtonWidth, infoButtonHeight);

        double SETTPosX = screenWidth - 740;
        double SETTPosY = 9;
        double settButtonWidth = 110;
        double settButtonHeight = 50;

        // Set up the "Settings" button with a click event handler
        buttonImage(SETT_ICON, null, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToMenu("SettingsMenu");
            }
        }, SETTPosX, SETTPosY, settButtonWidth, settButtonHeight);
    }

    /**
     * Exits the game by terminating the application.
     */
    private void exitGame() {
        System.exit(0);
    }

    /**
     * Retrieves the background image of the home menu.
     *
     * @return the background image of the home menu
     */
    public ImageView getBackground() {
        return this.background;
    }
}
