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

	//private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";
	private static final String HOME_MENU = "com.example.demo.menus.HomeMenu";
	private final Stage stage;
	private static final double ZOOM_FACTOR = 1.75;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			//goToLevel(LEVEL_ONE_CLASS_NAME);
			goToMenu(HOME_MENU, ZOOM_FACTOR);
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

	private void goToMenu(String menuClassName, double zoomFactor) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Going to menu " + menuClassName);

		Class<?> myClass = Class.forName(menuClassName);

		Constructor<?> constructor = myClass.getConstructor(Stage.class, double.class, double.class, double.class);
		MenuParent myMenu = (MenuParent) constructor.newInstance(stage, stage.getWidth(), stage.getHeight(), zoomFactor);

		myMenu.addObserver(this);

		Scene scene = myMenu.initializeScene();
		stage.setScene(scene);
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		String Name = (String) arg1;
		System.out.println("Transitioning to " + Name);
		try {
			if (Name.equals("WinMenu")) {
				goToMenu("com.example.demo.menus.WinMenu", ZOOM_FACTOR);
			} else if (Name.equals("LoseMenu")) {
				goToMenu("com.example.demo.menus.LoseMenu", ZOOM_FACTOR);
			}
			else {
				goToLevel(Name);
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}

}
