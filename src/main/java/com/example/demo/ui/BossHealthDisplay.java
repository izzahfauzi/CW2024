package com.example.demo.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


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

    public BossHealthDisplay(double xPosition, double yPosition, double maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;

        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;

        initializeContainer();
        initializeBossHealth();

    }

    private void initializeContainer() {
        container = new StackPane();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);

    }

    private void initializeBossHealth(){
        healthBarBackground = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        healthBarBackground.setFill(BACKGROUND_COLOR);
        healthBarBackground.setStroke(Color.web("#2e464d"));
        healthBarBackground.setStrokeWidth(OUTLINE_STROKE_WIDTH);

        healthBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        healthBar.setFill(HEALTH_COLOR);

        container.getChildren().addAll(healthBarBackground, healthBar);

        StackPane.setAlignment(healthBar, javafx.geometry.Pos.CENTER_RIGHT);
    }

    public void updateHealth(double newHealth) {
        currentHealth = Math.max(newHealth, 0);
        double healthPercentage = currentHealth / maxHealth;
        healthBar.setWidth(BAR_WIDTH * healthPercentage);
    }

    public StackPane getContainer() {
        return container;
    }
}
