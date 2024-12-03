package com.example.demo.transition;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TransitionOne extends TransitionParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/transitionone.png";

    public TransitionOne(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
        initializeControls();
    }

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
