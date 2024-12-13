package com.example.demo.transition;

import java.util.Observable;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The TransitionParent class serves as the base class for all transition screens in the game.
 * It manages the setup of the transition scene, including the background image and animation timeline.
 * This class is extended by specific transition screens, such as TransitionOne.
 */
public abstract class TransitionParent extends Observable {
    public final Stage stage;
    private final Group root;
    private final Timeline timeline;
    private final Scene scene;
    protected final ImageView background;
    protected final double screenWidth;
    protected final double screenHeight;

    /**
     * Constructs a new TransitionParent screen with the specified stage, background image, and screen dimensions.
     * Initializes the scene, background image, and prepares the stage for the transition screen.
     *
     * @param stage the main stage of the application where the transition screen will be displayed
     * @param backgroundImageName the path to the background image for the transition screen
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public TransitionParent(Stage stage, String backgroundImageName, double screenHeight, double screenWidth) {
        this.stage = stage;
        this.timeline = new Timeline();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));

        initializeBackground();
    }

    /**
     * Initializes and returns the scene for the transition screen.
     *
     * @return the scene to be displayed on the stage
     */
    public Scene initializeScene() {
        return scene;
    }

    /**
     * Initializes the controls for the transition screen.
     * This method is intended to be overridden by subclasses to implement specific controls.
     */
    protected void initializeControls() {
    }

    /**
     * Initializes the background image for the transition screen.
     * This method configures the background image to be focus traversable and preserves its aspect ratio.
     */
    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setPreserveRatio(true);

        root.getChildren().add(background);
    }

    /**
     * Triggers the transition to the next level by notifying observers with the specified level name.
     * Stops the current animation timeline.
     *
     * @param levelName the name of the level to transition to
     */
    public void goToNextLevel(String levelName) {
        setChanged();
        notifyObservers(levelName);

        timeline.stop();
    }
}
