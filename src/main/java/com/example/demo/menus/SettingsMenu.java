package com.example.demo.menus;

import com.example.demo.audio.SoundManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class SettingsMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/settingsmenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audios/Copycat.wav";
    private static final String INCREASE_VOLUME_BUTTON = "/com/example/demo/images/buttons/Chevron-Arrow-Up.png";
    private static final String DECREASE_VOLUME_BUTTON = "/com/example/demo/images/buttons/Chevron-Arrow-Down.png";
    private static  final String HOME_ICON = "/com/example/demo/images/Buttons/Home.png";
    private static final float VOLUME_INCREMENT = 2.0f;
    private final SoundManager soundManager;

    public SettingsMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, BACKGROUND_MUSIC);

        this.soundManager = SoundManager.getInstance();
        initializeControls();
    }

    @Override
    protected void initializeControls() {

        double homeButtonX = screenWidth - 745;
        double homeButtonY = 9;

        double homeButtonWidth = 110;
        double homeButtonHeight = 50;

        buttonImage(HOME_ICON, null, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToMenu("HomeMenu");
            }
        }, homeButtonX, homeButtonY, homeButtonWidth, homeButtonHeight);

        double ButtonWidth = 110;
        double ButtonHeight = 50;

        double increaseButtonX = 700;
        double increaseButtonY = 350;
        buttonImage(INCREASE_VOLUME_BUTTON, null, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Volume Increase");
                soundManager.increaseVolume(VOLUME_INCREMENT);
                printVolumeLevel();
            }
        }, increaseButtonX, increaseButtonY, ButtonWidth, ButtonHeight);

        double decreaseButtonX = 500;
        double decreaseButtonY = 350;
        buttonImage(DECREASE_VOLUME_BUTTON, null, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Volume Decrease");
                soundManager.decreaseVolume(VOLUME_INCREMENT);
                printVolumeLevel();
            }
        }, decreaseButtonX, decreaseButtonY, ButtonWidth, ButtonHeight);
    }

    private void printVolumeLevel() {
        float currentVolume = soundManager.getVolume();
        System.out.println("Current Volume Level: " + currentVolume);
    }
}