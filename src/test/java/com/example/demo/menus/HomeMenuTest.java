package com.example.demo.menus;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HomeMenuTest {

    static class TestHomeMenu extends HomeMenu {
        public TestHomeMenu(Stage stage, double screenHeight, double screenWidth) {
            super(stage, screenHeight, screenWidth);
        }

    }

    @Test
    void testSceneInitialization() {
        Stage mockStage = new Stage();

        TestHomeMenu homeMenu = new TestHomeMenu(mockStage, 600, 800);

        Scene scene = homeMenu.initializeScene();

        assertNotNull(scene, "Scene should not be null");
        assertEquals(800, scene.getWidth(), "Scene width should match initialisation");
        assertEquals(600, scene.getHeight(), "Scene height should match initialisation");
    }

    @Test
    void testButtonCreation() {
        Stage mockStage = new Stage();

        TestHomeMenu homeMenu = new TestHomeMenu(mockStage, 600, 800);

        Button button = homeMenu.buttonImage(
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
    void testBackgroundImageInitialization() {
        Stage mockStage = new Stage();

        TestHomeMenu homeMenu = new TestHomeMenu(mockStage, 600, 800);

        ImageView background = homeMenu.getBackground(); // Ensure you have a getter in HomeMenu
        assertNotNull(background, "Background image should not be null");
        assertEquals(600, background.getFitHeight(), "Background height should match initialisation");
        assertEquals(800, background.getFitWidth(), "Background width should match initialisation");
    }

    @Test
    void testGoToMenu() {
        Stage mockStage = new Stage();

        TestHomeMenu homeMenu = new TestHomeMenu(mockStage, 600, 800);

        final boolean[] menuChanged = {false};

        homeMenu.addObserver((observable, arg) -> {
            if (arg.equals("PauseMenu")) {
                menuChanged[0] = true;
            }
        });

        homeMenu.goToMenu("PauseMenu");

        assertTrue(menuChanged[0], "Menu should change to PauseMenu");
    }
}
