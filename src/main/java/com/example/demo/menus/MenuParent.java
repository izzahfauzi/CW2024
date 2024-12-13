package com.example.demo.menus;

import java.util.Observable;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import com.example.demo.audio.SoundManager;

/**
 * The MenuParent class serves as the base class for all menus in the game.
 * It manages the stage, scene, background image, and controls common to all menus.
 * It also handles the background music and provides functionality for navigating
 * between menus and levels, as well as pausing and resuming the game.
 */
public abstract class MenuParent extends Observable {
    public final Stage stage;
    private final Group root;
    private final Timeline timeline;
    private final Scene scene;
    protected final ImageView background;
    protected final double screenWidth;
    protected final double screenHeight;
    private boolean isPaused = false;
    private SoundManager soundManager;

    /**
     * Constructs a MenuParent object and initializes the menu with the specified stage,
     * background image, screen dimensions, and background music.
     *
     * @param stage the main stage of the application
     * @param backgroundImageName the path to the background image for the menu
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param backgroundMusicPath the path to the background music for the menu
     */
    public MenuParent(Stage stage, String backgroundImageName, double screenHeight, double screenWidth, String backgroundMusicPath) {
        this.stage = stage;
        this.timeline = new Timeline();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;

        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);

        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));

        initializeBackground();

        soundManager = new SoundManager();
        soundManager.PlayMusic(backgroundMusicPath);
    }

    /**
     * Initializes the scene for the menu and returns it.
     *
     * @return the Scene object for the menu
     */
    public Scene initializeScene() {
        return scene;
    }

    /**
     * Initializes the controls for the menu. This method can be overridden in subclasses
     * to define custom controls.
     */
    protected void initializeControls() {
    }

    /**
     * Initializes the background image for the menu and adds it to the root group.
     */
    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setPreserveRatio(true);

        root.getChildren().add(background);
    }

    /**
     * Creates and returns a button with an image. The button can also have a hover effect
     * and an event handler for click actions.
     *
     * @param buttonImagePath the path to the image for the button
     * @param hoverImagePath the path to the hover image for the button (can be null)
     * @param eventHandler the event handler for the button click
     * @param posX the X position of the button
     * @param posY the Y position of the button
     * @param buttonWidth the width of the button
     * @param buttonHeight the height of the button
     * @return the created button
     */
    protected Button buttonImage(String buttonImagePath, String hoverImagePath, EventHandler eventHandler, double posX, double posY, double buttonWidth, double buttonHeight) {

        Image image = new Image(getClass().getResource(buttonImagePath).toExternalForm());

        double adjustedButtonWidth = buttonWidth;
        double adjustedButtonHeight = buttonHeight;

        ImageView buttonImageView = new ImageView(image);
        buttonImageView.setFitWidth(adjustedButtonWidth);
        buttonImageView.setFitHeight(adjustedButtonHeight);
        buttonImageView.setPreserveRatio(true);

        Button button = new Button();
        button.setGraphic(buttonImageView);
        button.setStyle("-fx-background-color: transparent;");

        button.setLayoutX(posX);
        button.setLayoutY(posY);

        button.setOnAction(eventHandler);

        if (hoverImagePath != null) {
            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Image hoverImage = new Image(getClass().getResource(hoverImagePath).toExternalForm());
                    ImageView hoverImageView = new ImageView(hoverImage);
                    hoverImageView.setFitWidth(adjustedButtonWidth);
                    hoverImageView.setFitHeight(adjustedButtonHeight);
                    hoverImageView.setPreserveRatio(true);
                    button.setGraphic(hoverImageView);
                }
            });

            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setGraphic(buttonImageView);
                }
            });
        }
        root.getChildren().add(button);

        return button;
    }

    /**
     * Resumes the game by unpausing the timeline and setting the pause flag to false.
     */
    public void resumeGame() {
        isPaused = false;
        timeline.play();
    }

    /**
     * Navigates to the next level by notifying observers and stopping the timeline and background music.
     *
     * @param levelName the name of the next level to navigate to
     */
    public void goToNextLevel(String levelName) {
        setChanged();
        notifyObservers(levelName);

        timeline.stop();
        stopBackgroundMusic();
    }

    /**
     * Navigates to a specified menu by notifying observers and stopping the timeline and background music.
     *
     * @param menuName the name of the menu to navigate to
     */
    public void goToMenu(String menuName) {
        setChanged();
        notifyObservers(menuName);

        timeline.stop();
        stopBackgroundMusic();
    }

    /**
     * Stops the background music if the SoundManager is not null.
     */
    protected void stopBackgroundMusic() {
        if (soundManager != null) {
            soundManager.stopMusic();
        }
    }
}
