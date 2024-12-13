package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.levels.LevelParent;
import com.example.demo.menus.MenuParent;
import com.example.demo.transition.TransitionParent;

/**
 * Controller for managing the navigation between menus, transitions, and game levels.
 * <p>
 * The {@link Controller} class acts as the intermediary between the UI elements (menus, levels, transitions)
 * and the underlying game logic. It listens for updates from other components and handles the transitions
 * between different game states.
 * </p>
 */
public class Controller implements Observer {

	private static final String HOME_MENU = "com.example.demo.menus.HomeMenu";
	private final Stage stage;
	private LevelParent currentLevel;

	/**
	 * Constructs a new {@link Controller} with the given {@link Stage}.
	 *
	 * @param stage the primary stage for the game window.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game and shows the home menu.
	 * <p>
	 * This method displays the home menu when the game is launched.
	 * </p>
	 *
	 * @throws ClassNotFoundException if the class for the home menu cannot be found.
	 * @throws NoSuchMethodException if the constructor for the menu is missing.
	 * @throws SecurityException if a security manager denies access to the constructor.
	 * @throws InstantiationException if the menu class cannot be instantiated.
	 * @throws IllegalAccessException if the constructor cannot be accessed.
	 * @throws IllegalArgumentException if the constructor arguments are incorrect.
	 * @throws InvocationTargetException if the constructor throws an exception.
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.show();
		goToMenu(HOME_MENU);
	}

	/**
	 * Displays the transition screen based on the specified transition class.
	 * <p>
	 * The method dynamically loads the transition class and shows the corresponding scene.
	 * </p>
	 *
	 * @param transitionClassName the fully qualified class name of the transition.
	 * @throws ClassNotFoundException if the transition class cannot be found.
	 * @throws NoSuchMethodException if the constructor for the transition is missing.
	 * @throws InstantiationException if the transition cannot be instantiated.
	 * @throws IllegalAccessException if the constructor cannot be accessed.
	 * @throws InvocationTargetException if the constructor throws an exception.
	 */
	private void showTransitionPrompt(String transitionClassName) throws ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		System.out.println("Going to transition " + transitionClassName);

		Class<?> myClass = Class.forName(transitionClassName);
		Constructor<?> constructor = myClass.getConstructor(Stage.class, double.class, double.class);
		TransitionParent transition = (TransitionParent) constructor.newInstance(stage, stage.getHeight(), stage.getWidth());

		transition.addObserver(this);

		Scene scene = transition.initializeScene();
		stage.setScene(scene);
	}

	/**
	 * Navigates to the specified menu.
	 * <p>
	 * This method dynamically loads the menu class and shows the corresponding scene.
	 * </p>
	 *
	 * @param menuClassName the fully qualified class name of the menu.
	 * @throws ClassNotFoundException if the menu class cannot be found.
	 * @throws NoSuchMethodException if the constructor for the menu is missing.
	 * @throws SecurityException if a security manager denies access to the constructor.
	 * @throws InstantiationException if the menu cannot be instantiated.
	 * @throws IllegalAccessException if the constructor cannot be accessed.
	 * @throws IllegalArgumentException if the constructor arguments are incorrect.
	 * @throws InvocationTargetException if the constructor throws an exception.
	 */
	private void goToMenu(String menuClassName) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Going to menu " + menuClassName);

		Class<?> myClass = Class.forName(menuClassName);
		Constructor<?> constructor;
		MenuParent myMenu;

		if (menuClassName.equals("com.example.demo.menus.PauseMenu")) {
			constructor = myClass.getConstructor(Stage.class, double.class, double.class, LevelParent.class);
			myMenu = (MenuParent) constructor.newInstance(stage, stage.getWidth(), stage.getHeight(), currentLevel);
		} else {
			constructor = myClass.getConstructor(Stage.class, double.class, double.class);
			myMenu = (MenuParent) constructor.newInstance(stage, stage.getWidth(), stage.getHeight());
		}

		myMenu.addObserver(this);

		Scene scene = myMenu.initializeScene();
		stage.setScene(scene);
	}

	/**
	 * Navigates to the specified game level.
	 * <p>
	 * This method dynamically loads the level class and starts the game with the corresponding scene.
	 * </p>
	 *
	 * @param className the fully qualified class name of the level.
	 * @throws ClassNotFoundException if the level class cannot be found.
	 * @throws NoSuchMethodException if the constructor for the level is missing.
	 * @throws SecurityException if a security manager denies access to the constructor.
	 * @throws InstantiationException if the level cannot be instantiated.
	 * @throws IllegalAccessException if the constructor cannot be accessed.
	 * @throws IllegalArgumentException if the constructor arguments are incorrect.
	 * @throws InvocationTargetException if the constructor throws an exception.
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Going to level " + className);

		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

		currentLevel.addObserver(this);

		Scene scene = currentLevel.initializeScene();
		stage.setScene(scene);
		currentLevel.startGame();
	}

	/**
	 * Handles updates from the observed components and triggers the appropriate scene transitions.
	 * <p>
	 * Based on the updated name, this method decides whether to navigate to a menu, transition, or game level.
	 * </p>
	 *
	 * @param arg0 the observable object.
	 * @param arg1 the update object, expected to be a String indicating the next scene.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		String Name = (String) arg1;
		try {
			if (Name.contains("Menu")) {
				goToMenu("com.example.demo.menus." + Name);
			} else if (Name.contains("Transition")){
				showTransitionPrompt("com.example.demo.transition." + Name);
			} else
			{
				goToLevel("com.example.demo.levels." + Name);
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			e.printStackTrace();
			alert.show();
		}
	}
}
