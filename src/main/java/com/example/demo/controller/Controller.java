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
import com.example.demo.menus.HomeMenu;

public class Controller implements Observer {

	private static final String HOME_MENU = "com.example.demo.menus.HomeMenu";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

		stage.show();
		goToMenu(HOME_MENU);
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Going to level " + className);

		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

		myLevel.addObserver(this);

		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	private void goToMenu(String menuClassName) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Going to menu " + menuClassName);

		Class<?> myClass = Class.forName(menuClassName);
		Constructor<?> constructor = myClass.getConstructor(Stage.class, double.class, double.class);
		MenuParent myMenu = (MenuParent) constructor.newInstance(stage, stage.getWidth(), stage.getHeight());

		myMenu.addObserver(this);

		Scene scene = myMenu.initializeScene();
		stage.setScene(scene);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String Name = (String) arg1;
		try {
			if (Name.contains("Menu")) {
				goToMenu("com.example.demo.menus." + Name);
			} else {
				goToLevel("com.example.demo.levels." + Name);
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}

}
