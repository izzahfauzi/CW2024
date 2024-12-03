package com.example.demo.menus;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import com.example.demo.levels.LevelParent;

public class PauseMenu extends MenuParent{

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/pausemenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/Copycat.wav";
    private static  final String HOME_ICON = "/com/example/demo/images/Buttons/Home.png";
    private LevelParent currentLevel;

    public PauseMenu(Stage stage, double screenHeight, double screenWidth, LevelParent currentLevel) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, BACKGROUND_MUSIC);
        this.currentLevel = currentLevel;
        initializeControls();
    }

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

    @Override
    public void resumeGame() {
        super.resumeGame();
        transitionToLevel();
        stopBackgroundMusic();
    }
    private void transitionToLevel() {
        if (currentLevel != null) {
            Scene levelScene = currentLevel.initializeScene();
            stage.setScene(levelScene);
            currentLevel.startGame();
        }
    }

}
