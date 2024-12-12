package com.example.demo.controller;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller;
    private Stage mockStage;

    @BeforeEach
    void setUp() {
        mockStage = new Stage();
        controller = new Controller(mockStage);
    }

    @Test
    void testLaunchGame() {
        assertDoesNotThrow(() -> controller.launchGame(), "Launching the game should not throw an exception");
    }

    @Test
    void testUpdate() {
        assertDoesNotThrow(() -> controller.update(null, "MenuName"), "Updating the controller should not throw an exception");
    }
}
