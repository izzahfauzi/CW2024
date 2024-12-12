package com.example.demo.menus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuParentTest {

    private Stage stage;
    private MenuParent testMenu;

    @BeforeEach
    void setUp() {
        Platform.startup(() -> {});
        stage = new Stage();

        testMenu = new MenuParent(stage, "/background.png", 600, 800, "/music.mp3") {
            @Override
            protected void initializeControls() {
            }
        };
    }

    @Test
    void testInitializeScene() {
        Scene scene = testMenu.initializeScene();
        assertNotNull(scene, "Scene should not be null");
        assertEquals(800, scene.getWidth(), "Scene width should match initialisation");
        assertEquals(600, scene.getHeight(), "Scene height should match initialisation");
    }

    @Test
    void testButtonImageCreation() {
        Button button = testMenu.buttonImage(
                "/button.png",
                "/button_hover.png",
                event -> System.out.println("Button clicked"),
                100, 150, 200, 50
        );

        assertNotNull(button, "Button should not be null");
        assertEquals(100, button.getLayoutX(), "Button X position should be set correctly");
        assertEquals(150, button.getLayoutY(), "Button Y position should be set correctly");
    }

    @Test
    void testGoToMenu() {
        String menuName = "PauseMenu";

        testMenu.goToMenu(menuName);
        assertTrue(testMenu.hasChanged(), "MenuParent should register a change");
    }
}
