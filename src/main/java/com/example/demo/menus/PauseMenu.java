package com.example.demo.menus;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import com.example.demo.levels.LevelParent;

/**
 * The PauseMenu class represents the pause menu that appears during gameplay.
 * It allows users to resume the game or navigate to the home menu.
 * It is responsible for handling user input, including keyboard and button actions.
 */
public class PauseMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/pausemenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/Copycat.wav";
    private static  final String HOME_ICON = "/com/example/demo/images/Buttons/Home.png";
    private LevelParent currentLevel;

    /**
     * Constructs a PauseMenu object. Initializes the background image,
     * background music, and sets up the level for transitioning back to the gameplay.
     *
     * @param stage the main stage of the application
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param currentLevel the current level where the game is paused
     */
    public PauseMenu(Stage stage, double screenHeight, double screenWidth, LevelParent currentLevel) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, BACKGROUND_MUSIC);
        this.currentLevel = currentLevel;
        initializeControls();
    }

    /**
     * Initializes the controls for the pause menu.
     * Sets up key press events and button interactions.
     */
    @Override
    protected void initializeControls() {
        background.setFocusTraversable(true);
        background.requestFocus();
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    System.out.println("Space");
                    resumeGame();
                }
            }
        });

        // Set up the home button
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

    /**
     * Resumes the game, transitions to the current level, and stops the background music.
     */
    @Override
    public void resumeGame() {
        super.resumeGame();
        transitionToLevel();
        stopBackgroundMusic();
    }

    /**
     * Transitions to the current level by setting the scene to the level's scene
     * and starting the game.
     */
    private void transitionToLevel() {
        if (currentLevel != null) {
            Scene levelScene = currentLevel.initializeScene();
            stage.setScene(levelScene);
            currentLevel.startGame();
        }
    }
}
