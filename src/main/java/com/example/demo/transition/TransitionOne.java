package com.example.demo.transition;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * The TransitionOne class represents the first transition screen in the game.
 * It extends the TransitionParent class and manages the controls and actions
 * for this transition, particularly responding to the Enter key to move to the next level.
 */
public class TransitionOne extends TransitionParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/transitionone.png";

    /**
     * Constructs a new TransitionOne screen with the specified stage and screen dimensions.
     * This constructor calls the superclass (TransitionParent) constructor to set up
     * the transition screen with the background image and screen size.
     *
     * @param stage the main stage of the application where the transition screen will be displayed
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public TransitionOne(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
        initializeControls();
    }

    /**
     * Initializes the controls for the transition screen.
     * Specifically, it sets up a key listener to listen for the Enter key,
     * which triggers the transition to the next level (LevelTwo).
     */
    @Override
    protected void initializeControls() {
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    goToNextLevel("LevelTwo");
                }
            }
        });
    }
}
