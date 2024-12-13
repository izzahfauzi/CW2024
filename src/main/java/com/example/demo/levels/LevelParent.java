package com.example.demo.levels;

import java.util.*;
import java.util.stream.Collectors;

import javafx.util.Duration;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.actors.UserPlane;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.CollisionsManager;
import com.example.demo.audio.SoundEffectsManager;
import com.example.demo.powerup.PowerUpManager;
import javafx.animation.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * Abstract class representing a level in the game.
 * This class manages the game's main functionalities such as spawning enemies,
 * handling user input, managing collisions, and updating the game state.
 * Derived classes must implement specific level functionalities.
 */
public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private int currentNumberOfEnemies;

	private final LevelView levelView;
	private final LevelViewLevelBoss levelViewLevelBoss;

	private boolean isPaused = false;

	private SoundEffectsManager soundEffectsManager;
	private final PowerUpManager powerUpManager;
	private final CollisionsManager collisionsManager;

	private static final String SHOOT = "/com/example/demo/audios/sound effects/Gun.wav";
	private static final String WIN = "/com/example/demo/audios/sound effects/Win.wav";
	/**
	 * Abstract method to initialize friendly units (e.g., user plane, allies).
	 */
	protected abstract void initializeFriendlyUnits();
	/**
	 * Abstract method to check if the game is over.
	 */
	protected abstract void checkIfGameOver();
	/**
	 * Abstract method to spawn enemy units for the level.
	 */
	protected abstract void spawnEnemyUnits();
	/**
	 * Abstract method to instantiate and return the LevelView.
	 *
	 * @return the LevelView object for the current level
	 */
	protected abstract LevelView instantiateLevelView();
	/**
	 * Abstract method to instantiate and return the LevelViewLevelBoss.
	 *
	 * @return the LevelViewLevelBoss object for the current level's boss
	 */
	protected abstract LevelViewLevelBoss instantiateLevelViewLevelBoss();
	/**
	 * Constructor for initializing the level.
	 *
	 * @param backgroundImageName  the name of the background image for the level
	 * @param screenHeight         the height of the screen
	 * @param screenWidth          the width of the screen
	 * @param playerInitialHealth  the initial health of the user plane
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.levelViewLevelBoss = instantiateLevelViewLevelBoss();
		this.currentNumberOfEnemies = 0;
		this.powerUpManager = new PowerUpManager(root, user);
		this.soundEffectsManager = new SoundEffectsManager();
		this.collisionsManager = new CollisionsManager(root, user, soundEffectsManager, powerUpManager);

		initializeTimeline();
		friendlyUnits.add(user);

	}

	/**
	 * Initializes and returns the game scene.
	 *
	 * @return the scene object for the level
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		if (levelViewLevelBoss != null){
			levelViewLevelBoss.showBossHealthDisplay();
		}
		return scene;
	}

	/**
	 * Starts the game by playing the timeline animation.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Shows a transition prompt with a given name (used when transitioning between levels).
	 *
	 * @param transitionName the name of the transition to be shown
	 */
	public void showTransitionPrompt(String transitionName) {
		setChanged();
		notifyObservers(transitionName);

		timeline.stop();
		soundEffectsManager.playSound(WIN);
	}

	/**
	 * Navigates to the specified menu.
	 *
	 * @param menuName the name of the menu to navigate to
	 */
	public void goToMenu(String menuName) {
		setChanged();
		notifyObservers(menuName);

		timeline.stop();
	}

	/**
	 * Updates the game state, including spawning enemies, handling collisions, and updating the display.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();

		collisionsManager.handleUserProjectileCollisions(userProjectiles, enemyUnits);
		collisionsManager.handleEnemyProjectileCollisions(enemyProjectiles, friendlyUnits);
		collisionsManager.handlePlaneCollisions(friendlyUnits, enemyUnits);
		collisionsManager.handlePowerUpCollisions();
		collisionsManager.handleEnemyPenetration(enemyUnits, screenWidth);

		powerUpManager.spawnPowerUp();
		powerUpManager.updatePowerUps();
		powerUpManager.checkForCollisions();
	}

	/**
	 * Initializes the timeline for the game loop.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background for the level.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);

		if (!root.getChildren().contains(background)){
			root.getChildren().add(background);
		}
		initializeControls();
	}

	/**
	 * Initializes the controls for user input (movement and shooting).
	 */
	private void initializeControls(){
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.W) user.moveUp();
				if (kc == KeyCode.DOWN || kc == KeyCode.S) user.moveDown();

				if (kc == KeyCode.LEFT || kc == KeyCode.A) user.moveLeft();
				if (kc == KeyCode.RIGHT || kc == KeyCode.D) user.moveRight();

				if (kc == KeyCode.SPACE) fireProjectile();

				if (kc == KeyCode.P) togglePause();
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN || kc == KeyCode.S || kc == KeyCode.W) user.stopMoveVertical();
				if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT || kc == KeyCode.A || kc == KeyCode.D) user.stopMoveHorizontal();
			}
		});
	}

	/**
	 * Fires a projectile from the user plane.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);

		soundEffectsManager.playSound(SHOOT);
	}

	/**
	 * Pauses or resumes the game.
	 */
	private void togglePause(){
		if (isPaused){
			resumeGame();
		} else {
			pauseGame();
		}
	}

	/**
	 * Pauses the game and navigates to the pause menu.
	 */
	private void pauseGame() {
		isPaused = true;
		timeline.stop();
		goToMenu("PauseMenu");
	}

	/**
	 * Resumes the game from the paused state.
	 */
	private void resumeGame() {
		isPaused = false;
		timeline.play();
	}

	/**
	 * Generates enemy fire (projectiles).
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns an enemy projectile and adds it to the game.
	 *
	 * @param projectile the projectile to be added
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates the state of all actors (user, enemies, projectiles).
	 */
	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * Removes all destroyed actors from the game.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes destroyed actors from the specified list and the game scene.
	 *
	 * @param actors the list of actors to be checked and removed
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed()).collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Updates the number of enemies currently in the game.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * Updates the level view to reflect the current game state.
	 */
	private void updateLevelView() {
		levelView.updateHearts(user.getHealth());
	}

	/**
	 * Handles the logic when the player wins the game. Stops the game timeline
	 * and navigates to the win menu.
	 */
	protected void winGame() {
		timeline.stop();
		goToMenu("WinMenu");
	}

	/**
	 * Handles the logic when the player loses the game. Stops the game timeline
	 * and navigates to the lose menu.
	 */
	protected void loseGame() {
		timeline.stop();
		goToMenu("LoseMenu");
	}

	/**
	 * Returns the user plane (player's plane).
	 *
	 * @return the UserPlane object representing the player's plane
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Returns the root group containing all the game's visual elements.
	 *
	 * @return the root Group containing all the game elements
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Returns the current number of enemy units in the game.
	 *
	 * @return the number of enemy units
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds an enemy unit to the game and updates the root group to display it.
	 *
	 * @param enemy the enemy unit to be added
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Returns the maximum Y position for enemies, used to limit their vertical position.
	 *
	 * @return the maximum Y position for enemy units
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Returns the width of the game screen.
	 *
	 * @return the screen width
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks if the user (player's plane) is destroyed.
	 *
	 * @return true if the user plane is destroyed, false otherwise
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}
}
