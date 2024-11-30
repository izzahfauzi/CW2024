package com.example.demo.menus;

import java.util.Observable;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public abstract class MenuParent extends Observable {

    private final Stage stage;
    private final Group root;
    private final Timeline timeline;
    private final Scene scene;
    private final ImageView background;
    private final double screenWidth;
    private final double screenHeight;
    private final double zoomFactor;
    private static final String LEVEL_ONE = "com.example.demo.levels.LevelOne";

    public MenuParent(Stage stage, String backgroundImageName, double screenHeight, double screenWidth, double zoomFactor) {
        this.stage = stage;
        this.timeline = new Timeline();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.zoomFactor = zoomFactor;

        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);

        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));

        initializeBackground();
        initializeControls();

    }

    public Scene initializeScene() {
        return scene;
    }

    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setPreserveRatio(true);

        double imageWidth = background.getImage().getWidth();
        double imageHeight = background.getImage().getHeight();

        double scaleWidth = screenWidth / imageWidth;
        double scaleHeight = screenHeight / imageHeight;

        double scaleFactor = Math.min(scaleWidth, scaleHeight) * zoomFactor;;

        background.setFitWidth(imageWidth * scaleFactor);
        background.setFitHeight(imageHeight * scaleFactor);

        root.getChildren().add(background);
    }

    private void initializeControls(){
        timeline.stop();
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.SPACE) {
                    System.out.println("Space");
                    goToNextLevel(LEVEL_ONE);
                }
            }
        });
    }

    public void goToNextLevel(String levelName) {
        setChanged();
        notifyObservers(levelName);

        timeline.stop();
    }




}
