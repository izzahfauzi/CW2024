package com.example.demo.transition;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * The TransitionTwo class represents a specific transition screen in the game.
 * It inherits from the TransitionParent class and handles the transition to the "LevelThree"
 * when the user presses the Enter key.
 */
public class TransitionTwo extends TransitionParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/transitiontwo.png";

    /**
     * Constructs a new TransitionTwo screen with the specified stage, background image, and screen dimensions.
     * Initializes the scene and prepares the controls for transitioning to the next level.
     *
     * @param stage the main stage of the application where the transition screen will be displayed
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public TransitionTwo(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
        initializeControls();
    }

    /**
     * Initializes the controls for the transition screen.
     * Specifically, it listens for the Enter key press to transition to the "LevelThree".
     */
    @Override
    protected void initializeControls() {
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    goToNextLevel("LevelThree");
                }
            }
        });
    }
}
