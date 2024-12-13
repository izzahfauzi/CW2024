package com.example.demo.menus;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * The LoseMenu class represents the lose menu in the game.
 * It extends the MenuParent class and provides functionality for the
 * game over screen, including background image, background music,
 * and controls such as a button to navigate to the home menu or
 * proceed to the next level upon pressing the space key.
 */
public class LoseMenu extends MenuParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/losemenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/Down Under.wav";
    private static final String HOME_ICON = "/com/example/demo/images/Buttons/Home.png";

    /**
     * Constructs a LoseMenu object, initializing the menu with the specified stage,
     * screen dimensions, and background music.
     *
     * @param stage the main stage of the application
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public LoseMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, BACKGROUND_MUSIC);
        initializeControls();
    }

    /**
     * Initializes the controls for the lose menu, such as keyboard input and the home button.
     * - Pressing the space key triggers transitioning to the next level.
     * - The home button is placed at the top right of the screen and navigates to the home menu.
     */
    @Override
    protected void initializeControls() {
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.SPACE) {
                    goToNextLevel("LevelOne");
                    stopBackgroundMusic();
                }
            }
        });
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