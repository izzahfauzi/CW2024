package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.levels.LevelViewLevelBoss;
import com.example.demo.projectiles.BossProjectile;

import java.util.*;

/**
 * Represents the Boss fighter plane in the game, extending the {@link FighterPlane} class.
 * <p>
 * The Boss class handles the behaviour of the Boss plane, including its movement pattern, firing projectiles,
 * health management, and shield activation/deactivation. It has a predefined set of movement cycles and a chance
 * to activate a shield at random intervals.
 * </p>
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane1.png";
	private static final double INITIAL_X_POSITION = 1100.0;
	private static final double INITIAL_Y_POSITION = 400.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.002;
	private static final int IMAGE_HEIGHT = 90;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 50;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = 65;
	private static final int Y_POSITION_LOWER_BOUND = 650;
	private static final int MAX_FRAMES_WITH_SHIELD = 500;
	private final List<Integer> movePattern;
	private final LevelViewLevelBoss levelView;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private static final double SHIELD_X_OFFSET = -30;
	private static final double SHIELD_Y_OFFSET = 22;

	/**
	 * Constructs a new Boss with the specified {@link LevelViewLevelBoss} and initializes its movement pattern.
	 * The Boss plane starts at a predefined position with a given image and health.
	 *
	 * @param levelView the level view for the boss, used for shield management and health updates
	 */
	public Boss(LevelViewLevelBoss levelView) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.levelView = levelView;
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	/**
	 * Updates the Boss's position based on its movement pattern.
	 * The Boss moves vertically within certain bounds and updates the shield's position.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();

		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}

		levelView.updateShieldPosition(
				getLayoutX() + SHIELD_X_OFFSET,
				getLayoutY() + getTranslateY() + SHIELD_Y_OFFSET
		);
	}

	/**
	 * Updates the Boss's state, including its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile from the Boss if the firing condition is met.
	 *
	 * @return a new {@link BossProjectile} if the Boss fires, or {@code null} if it does not
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * The Boss takes damage if it is not shielded.
	 *
	 * @see FighterPlane#takeDamage()
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
			levelView.updateBossHealth(getHealth());
		}
	}

	/**
	 * Initializes the movement pattern for the Boss.
	 * The pattern contains vertical movements (up and down) and is shuffled for randomness.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the Boss.
	 * The shield is activated randomly based on probability and deactivated after a certain duration.
	 */
	private void updateShield() {
		if (isShielded) framesWithShieldActivated++;
		else if (shieldShouldBeActivated()) activateShield();
		if (shieldExhausted()) deactivateShield();
	}

	/**
	 * Retrieves the next move from the Boss's movement pattern.
	 * The movement direction is determined by the shuffled pattern and frequency of same-direction moves.
	 *
	 * @return the vertical velocity for the next move (up, down, or zero)
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the Boss should fire a projectile in the current frame based on its fire rate.
	 *
	 * @return {@code true} if the Boss fires, {@code false} otherwise
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Retrieves the Y-position for the initial projectile spawn, accounting for the Boss's position.
	 *
	 * @return the Y-position for the projectile
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines if the Boss should activate its shield, based on a random probability.
	 *
	 * @return {@code true} if the shield should be activated, {@code false} otherwise
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines if the Boss's shield has been activated for the maximum duration.
	 *
	 * @return {@code true} if the shield should be deactivated, {@code false} otherwise
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the Boss's shield, displaying it in the level view.
	 */
	private void activateShield() {
		System.out.println("Shield activated");
		isShielded = true;
		levelView.showShield();
	}

	/**
	 * Deactivates the Boss's shield, hiding it in the level view and resetting its status.
	 */
	private void deactivateShield() {
		System.out.println("Shield deactivated");
		isShielded = false;
		framesWithShieldActivated = 0;
		levelView.hideShield();
	}

}
