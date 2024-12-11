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
//refactoring4
public class Controller implements Observer {

	private static final String HOME_MENU = "com.example.demo.menus.HomeMenu";
	private final Stage stage;
	private LevelParent currentLevel;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

		stage.show();
		goToMenu(HOME_MENU);
	}

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
