package com.example.demo.levels;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Observable;

public class LevelTransitionPrompt extends Observable {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/transitionprompt.png";
    private final double screenHeight;
    private final double screenWidth;
    private final Group root;
    private final Scene scene;
    private final Timeline timeline;

    public LevelTransitionPrompt(double screenHeight, double screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.timeline = new Timeline();
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
    }

    public Scene createPrompt(String levelName) {
        ImageView background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);

        root.getChildren().add(background);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                goToNextLevel(levelName);
            }
        });

        return scene;
    }

    public void goToNextLevel(String levelName) {
        setChanged();
        notifyObservers(levelName);

        timeline.stop();
    }

}
