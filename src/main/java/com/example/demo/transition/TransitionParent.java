package com.example.demo.transition;

import java.util.Observable;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public abstract class TransitionParent extends Observable {

    public final Stage stage;
    private final Group root;
    private final Timeline timeline;
    private final Scene scene;
    protected final ImageView background;
    protected final double screenWidth;
    protected final double screenHeight;

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

    public Scene initializeScene() {
        return scene;
    }

    protected void initializeControls() {
    }

    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setPreserveRatio(true);

        root.getChildren().add(background);
    }

    public void goToNextLevel(String levelName) {
        setChanged();
        notifyObservers(levelName);

        timeline.stop();
    }

}
