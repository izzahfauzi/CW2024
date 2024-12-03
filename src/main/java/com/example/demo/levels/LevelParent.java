package com.example.demo.levels;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.actors.UserPlane;
import com.example.demo.actors.FighterPlane;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import com.example.demo.actors.Boss;

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
		initializeTimeline();
		friendlyUnits.add(user);

	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();
	protected abstract LevelViewLevelBoss instantiateLevelViewLevelBoss();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		if (levelViewLevelBoss != null){
			levelViewLevelBoss.showBossHealthDisplay();
		}
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);

		timeline.stop();
	}
	public void goToMenu(String menuName) {
		setChanged();
		notifyObservers(menuName);

		timeline.stop();
	}

	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);

		if (!root.getChildren().contains(background)){
			root.getChildren().add(background);
		}

		initializeControls();
	}

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

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void togglePause(){
		if (isPaused){
			resumeGame();
		} else {
			pauseGame();
		}
	}

	private void pauseGame() {
		isPaused = true;
		timeline.stop();
		goToMenu("PauseMenu");
	}

	private void resumeGame() {
		isPaused = false;
		timeline.play();
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed()).collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		for (ActiveActorDestructible friendly : friendlyUnits) {
			for (ActiveActorDestructible enemy : enemyUnits) {
				if (friendly.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
					if (enemy instanceof Boss) {
						user.takeDamage();
						if (userIsDestroyed()) {
							loseGame();
							return;
						}
					} else {
						enemy.takeDamage();
						friendly.takeDamage();
					}
				}
			}
		}
	}

	private void handleUserProjectileCollisions() {
			for (ActiveActorDestructible projectile : userProjectiles){
				for (ActiveActorDestructible enemy : enemyUnits){
					if (projectile.getBoundsInParent().intersects(enemy.getBoundsInParent())){
						enemy.takeDamage();
						projectile.destroy();
						if (enemy.isDestroyed()){
							user.incrementKillCount();
						}
					}
				}
		}
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemy.getLayoutX() + enemy.getTranslateX() > screenWidth) {
				user.takeDamage();
				enemy.destroy();
				updateNumberOfEnemies();
			}
		}
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	protected void winGame() {
		timeline.stop();
		goToMenu("WinMenu");
	}

	protected void loseGame() {
		timeline.stop();
		goToMenu("LoseMenu");
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

}
