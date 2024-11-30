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

public abstract class MenuParent extends Observable {

    private final Stage stage;
    private final Group root;
    private final Timeline timeline;
    private final Scene scene;
    protected final ImageView background;
    private final double screenWidth;
    private final double screenHeight;

    public MenuParent(Stage stage, String backgroundImageName, double screenHeight, double screenWidth) {
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

    protected Button buttonImage(String buttonImagePath, String hoverImagePath, EventHandler eventHandler, double posX, double posY) {

        Image image = new Image(getClass().getResource(buttonImagePath).toExternalForm());
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();

        double adjustedButtonWidth = Math.min(imageWidth, screenWidth * 0.8);
        double adjustedButtonHeight = imageHeight * (adjustedButtonWidth / imageWidth);

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

        root.getChildren().add(button);

        return button;
    }

    public void goToNextLevel(String levelName) {
        setChanged();
        notifyObservers(levelName);

        timeline.stop();
    }


}
