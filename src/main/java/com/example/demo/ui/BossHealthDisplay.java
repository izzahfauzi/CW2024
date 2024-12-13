package com.example.demo.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The BossHealthDisplay class represents a health bar for the boss in the game.
 * It displays the current health of the boss and updates the health bar as the boss takes damage.
 */
public class BossHealthDisplay {

    private static final double BAR_WIDTH = 300;
    private static final double BAR_HEIGHT = 20;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color HEALTH_COLOR = Color.web("#ffaa5f");
    private static final double OUTLINE_STROKE_WIDTH = 2;

    private StackPane container;
    private double containerXPosition;
    private double containerYPosition;
    private double maxHealth;
    private double currentHealth;

    private Rectangle healthBarBackground;
    private Rectangle healthBar;

    /**
     * Constructor to initialize the boss's health display.
     *
     * @param xPosition The X-position of the health bar on the screen
     * @param yPosition The Y-position of the health bar on the screen
     * @param maxHealth The maximum health of the boss
     */
    public BossHealthDisplay(double xPosition, double yPosition, double maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;

        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;

        initializeContainer();
        initializeBossHealth();
    }

    /**
     * Initializes the container that will hold the health bar and its background.
     */
    private void initializeContainer() {
        container = new StackPane();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);
    }

    /**
     * Initializes the health bar and its background, and sets them in the container.
     */
    private void initializeBossHealth() {
        healthBarBackground = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        healthBarBackground.setFill(BACKGROUND_COLOR);
        healthBarBackground.setStroke(Color.web("#2e464d"));
        healthBarBackground.setStrokeWidth(OUTLINE_STROKE_WIDTH);
        healthBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        healthBar.setFill(HEALTH_COLOR);
        container.getChildren().addAll(healthBarBackground, healthBar);
        StackPane.setAlignment(healthBar, javafx.geometry.Pos.CENTER_RIGHT);
    }

    /**
     * Updates the health bar width based on the new health value.
     *
     * @param newHealth The new health value for the boss
     */
    public void updateHealth(double newHealth) {
        currentHealth = Math.max(newHealth, 0);
        double healthPercentage = currentHealth / maxHealth;
        healthBar.setWidth(BAR_WIDTH * healthPercentage);
    }

    /**
     * Returns the container holding the health bar and its background.
     *
     * @return The container with the health bar
     */
    public StackPane getContainer() {
        return container;
    }
}
