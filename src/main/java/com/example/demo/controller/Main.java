package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class of the application, which launches the JavaFX game.
 * <p>
 * This class extends {@link Application} and overrides the {@link #start(Stage)} method to set up and
 * launch the game. It creates a {@link Controller} and calls {@link Controller#launchGame()} to start the game.
 * </p>
 */
public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "2024: Sky Battle";
	public Controller myController;

	/**
	 * Initializes the game window and launches the game.
	 * <p>
	 * This method is automatically called by the JavaFX runtime when the application is launched.
	 * It sets up the game window with a specified title, size, and calls the controller to launch the game.
	 * </p>
	 *
	 * @param stage the primary stage for the application.
	 * @throws ClassNotFoundException if a class required by the application cannot be found.
	 * @throws NoSuchMethodException if a method cannot be found.
	 * @throws SecurityException if a security violation occurs.
	 * @throws InstantiationException if a class cannot be instantiated.
	 * @throws IllegalAccessException if a method or constructor cannot be accessed.
	 * @throws IllegalArgumentException if an invalid argument is passed to a method or constructor.
	 * @throws InvocationTargetException if an exception is thrown by an invoked method or constructor.
	 */
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		myController = new Controller(stage);
		myController.launchGame();
	}

	/**
	 * The main entry point for the application.
	 * <p>
	 * This method calls {@link Application#launch(String...)} to start the JavaFX runtime and invoke the
	 * {@link #start(Stage)} method.
	 * </p>
	 *
	 * @param args command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		launch();
	}
}
