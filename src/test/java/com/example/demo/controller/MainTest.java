package com.example.demo.controller;

import javafx.stage.Stage;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testLaunchGame() {
        Main mainApp = new Main();

        Platform.runLater(() -> {
            try {
                Stage mockStage = new Stage();
                mainApp.start(mockStage);
                assertNotNull(mainApp.myController, "Controller should be initialized");
            } catch (Exception e) {
                fail("Exception occurred during start(): " + e.getMessage());
            }
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            fail("Test interrupted: " + e.getMessage());
        }
    }
}