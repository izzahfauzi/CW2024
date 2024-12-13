# CW2024 - Developing Maintainable Software
### Repository Link: https://github.com/izzahfauzi/CW2024.git

## Compilation Instructions:
1. **Download and Extract the Game**:
   - Download the game's ZIP file by heading to the Github Repository.
   - Extract the contents.
2. **Set Up Your Development Enviroment**:
   - Open your preferred IDE (IntelliJ IDEA is recommended).
   - Ensure the required SDKs are properly configured.
   - Verify Maven is installed and dependencies are resolved.
3. **Open the Project**:
   - Navigate the folder where the project is located.
   - Open the project in your IDE.
4. **Build and Run the Game**:
   - Locate the **Main.java** class in the project directory: `src/main/java/com/example/demo/controller/Main.java`.
   - Build the project.
   - After a successful build, run **Main** class to launch the game.
---

## FEATURES

### Implemented and Working Properly:
1. **Menus**:
   - **Home Menu**: The first screen users see upon launching the game. From here, users can start the game, access the settings menu, view the info menu, or exit the game.  
   - **Info Menu**: Provides instructions on how to play the game.
   - **Pause Menu**: Lets users pause the game at any point. From here, they can resume the game or return to the home menu.  
   - **Game Over Menu**: Appears when the user loses, offering the option to restart the game from Level 1 or return to the home menu.
   - **Win Menu**: Displays a "Boss Defeated" message and an option to return to the home menu.  

2. **Transitions**:  
   - After completing a level, a transition menu appears displaying the message: “Level _ Complete.” Users are prompted to press **Enter** to proceed to the next level.

3. **Levels**:  
   - **Level 2**: Introduces a new type of plane that moves both vertically and horizontally to the left. This plane is faster and has a health of 3.
   - **Level 3**: Introduces a new type of plane that moves horizontally to the left. It is slower compared to previous planes but has a higher health of 7, requiring more hits to be defeated.  

4. **Boss Health Bar**:  
   - Displays the boss's health as a health bar, which decreases as the boss’s health decreases.

5. **Power-Up: Gain Heart**:  
   - The Gain Heart power-up spawns randomly during the level with a 1% chance.
   - When the user collides with the heart, their health is increased by 1, provided their health is not already at the maximum.  

6. **Music and Sound Effects**:  
    - **Background music** has been added to the menus
    - **Sound effects** are triggered during key gameplay moments, including:
      - When the user's plane shoots.
      - When the user's plane is hit.
      - When an enemy plane is hit.

7. **WASD Controls**:  
     - In addition to the arrow keys, users can move using the **W**, **A**, **S**, and **D** keys.

### Implemented and Not Working Properly:
1. **Volume Control in Settings Menu**:
   - Problem: The volume control functionality fails to adjust the volume when the user presses the button to decrease or increase the volume. When the audio clip is stopped, the associated volume control may either get cleared or become unavailable, preventing volume adjustments.
   - **Steps taken to address the issue**:
     - In the original implementation, when stopMusic() was called, the volume control was not preserved therefore leading to issues when trying to adjust the volume after the clip was stopped. I ensured the volume control isn't cleared when stopping the clip, allowing volume adjustments even when the clip is stopped.
     - The volume control needs to be reinitialized each time the clip is started or resumed. If the clip isn't running or isn't initialized properly, the volume control does not work. I added a debugging statement to ensure the initializeVolumeControl() method is called every time the clip starts.
     - Trying to adjust the volume would fail if the clip is not running or has been stoppe. This is because the control is tied to the clip's state. I added a debugging statement to ensure that the clip is running.
   - However, all the steps taken still could not solve the issue.
   - **Possible solution**:
     - A possible solution would be to store the volume setting independently. Therefore when the clip restarts, the previously stored volume setting can be reapplied and hence ensuring that the volume remains consistent even when the clip is stopped.

### Not Implemented:
1. **More Power Ups**:
   - Speed Boost: A power-up that temporarily increases the user's movement speed. 
   - Shield: A power-up that grants temporary invincibility to protect the user plane from damage.
   - Double Damage Bullets: A power-up that increases the damage dealt to enemies for limited time
   - Applying Single Responsibility Principle (SRP) in more classes

**Reasons for Not Implementing**: Limited time constraints prevented the addition of these features. 

---

## CHANGES IN CODE

### New Classes:

### com.example.demo.actors

### `CollisionManager.java`: Manages collisions between multiple entities in the game, making sure proper interactions and effects when game objects collide.

**Features**

- Plane Collisions: Detects and processes collisions between the user’s plane and enemy units, applying damage to both.
- Projectile Handling: Manages interactions between user and enemy projectiles with their respective targets.
- Power-Up Collection: Handles user interactions with power-ups.
- Sound Integration: Plays sound effects for various collision scenarios (e.g., hits, power-ups).

**Core Methods**

1. **`handlePlaneCollisions(List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits)`**  
   Handles collisions between friendly units (e.g., the user’s plane) and enemy units. Both entities take damage upon collision.

2. **`handleUserProjectileCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyUnits)`**  
   Detects when user projectiles hit enemy units, applying damage, destroying the projectile, and incrementing the player’s kill count when an enemy is destroyed.

3. **`handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> friendlyUnits)`**  
   Processes collisions between enemy projectiles and friendly units, applying damage to both upon contact.

4. **`handlePowerUpCollisions()`**  
   Manages collisions between the user and power-ups, applying effects (e.g., health gain) and removing the collected power-ups from the game.

### com.example.demo.audio

### `SoundManager.java`: Manages background music and volume control for the game. It loops the audio and has methods that allow user's to control the volume

**Features**

- Singleton Pattern: Ensures only one instance of `SoundManager` is active throughout the game, reducing resource conflicts.
- Background Music Playback: Plays audio files in a loop.
- Volume Control: Provides methods to adjust the music volume up or down and set it to a specific level.
- Default Volume: Automatically sets the background music to a predefined volume level (`DEFAULT_VOLUME`) upon playback.
- Error Handling: Handles scenarios where the audio file is missing or cannot be played.

**Core Methods**

1. **`getInstance()`**  
   Retrieves the singleton instance of the `SoundManager` class.  

2. **`PlayMusic(String path)`**  
   Starts playing the background music from the specified file path. The music loops continuously until stopped.  

3. **`setVolume(float value)`**  
   Adjusts the music volume to a specific level.  

4. **`increaseVolume(float increment)`**  
   Increases the volume by a given increment, capped at the maximum allowed volume.  

5. **`decreaseVolume(float decrement)`**  
   Decreases the volume by a given decrement, down to the minimum allowed volume.  

6. **`getVolume()`**  
   Retrieves the current volume level.  

7. **`stopMusic()`**  
   Stops the background music playback.  

### `SoundEffectsManager.java`: Manages and plays sound effects in the game. 

**Features**

- Sound Effect Playback: Loads and plays sound effects from audio files using the Java `Clip` class.
- Resource Handling: Fetches audio files from specified paths and ensures proper playback handling.
- Error Handling: Provides feedback when audio files are missing or an error occurs during playback.

**Core Methods**

1. **`playSound(String path)`**  
   Plays a sound effect from the specified file path. If the audio file cannot be found or played, an error message is displayed in the console.

### com.example.demo.levels

### `LevelTwo.java`: The 'LevelTwo' class represents the game's second level, such as unique gameplay mechanisms such as opponent plane spawning, kill count-based progression, and checking for game-over circumstances.

**Features**

- Background and Theme: Sets a unique background for the level using `sky2.png`.
- Enemy Spawning: Spawns enemies based on probabilities, including special enemy types.
- Kill-Based Progression: Players need to achieve a higher kill count (15 kills) to advance.
- Health System: The player's health is initialised and managed for this level.
- Transition Mechanics: Displays a transition prompt when advancing to the next level.

**Core Methods**

1. **`checkIfGameOver()`**
   - Ends the game if the user's plane is destroyed.
   - Initiates a transition to the next level when the required kill count is reached.

2. **`initializeFriendlyUnits()`**
   - Ensures the user's plane is added to the game scene.

3. **`spawnEnemyUnits()`**
   - Spawns enemies based on the user’s current kill count and predefined probabilities.

4. **`instantiateLevelView()`**
   - Creates and returns the level view for Level Two, including a health display.

5. **`instantiateLevelViewLevelBoss()`**
   - Returns `null` as this level does not feature a boss.

6. **`userHasReachedKillTarget()`**
   - Checks if the player has achieved the required number of kills to progress.

### `LevelThree.java`: The 'LevelThree' class reflects the game's third level, which utilises previous levels' principles while introducing new challenges such as tank enemy planes. This level focusses on kill count-based progression and checks for game-ending circumstances.

**Features**

- Background and Theme: Utilises `sky2.png` as the background for a consistent aesthetic.
- Enemy Spawning: Spawns enemies based on probabilities, including special enemy and tank enemy.
- Kill-Based Progression: Players need to achieve a higher kill count (20 kills) to advance.
- Health System: The player's health is initialised and managed for this level.
- Transition Mechanics: Displays a transition prompt when advancing to the next level.

**Core Methods**

1. **`checkIfGameOver()`**
   - Ends the game if the user's plane is destroyed.
   - Initiates a transition to the next level when the required kill count is reached.

2. **`initializeFriendlyUnits()`**
   - Ensures the user's plane is added to the game scene

3. **`spawnEnemyUnits()`**
   - Spawns enemies based on the user’s current kill count and predefined probabilities.

4. **`instantiateLevelView()`**
   - Creates and returns the level view for Level Two, including a health display.

5. **`instantiateLevelViewLevelBoss()`**
   - Returns `null` as this level does not feature a boss.

6. **`userHasReachedKillTarget()`**
   - Checks if the player has achieved the required number of kills to progress.
  
### com.example.demo.menus

### `MenuParent.java`: The 'MenuParent' class acts as a base for all menu screens in the game. It handles common tasks including background configuration, button generation, audio control, and navigating between menus and stages.

**Features**

- Background Management: Handles setting and displaying background images for menus.
- Button Creation: Simplifies adding buttons with optional hover effects and click event handling.
- Background Music: Plays music during menu screens, with control for stopping and resuming.
- Navigation: Provides methods for transitioning between menus and levels.
- Pause/Resume: Controls game state with pause and resume functionality.

**Core Methods**

1. **`Scene initializeScene()`**
   - Returns the scene object for the menu.

2. **`protected void initializeControls()`**
   - Placeholder for adding custom controls in subclasses.

3. **`protected Button buttonImage(String buttonImagePath, String hoverImagePath, EventHandler eventHandler, double posX, double posY, double buttonWidth, double buttonHeight)`**
   - Creates a button with an image, hover effect (optional), and click action.

4. **`void resumeGame()`**
   - Resumes the game by playing the timeline and resetting the pause flag.

5. **`void goToNextLevel(String levelName)`**
   - Transitions to the specified level and stops background music.

6. **`void goToMenu(String menuName)`**
   - Navigates to the specified menu and stops background music.

7. **`protected void stopBackgroundMusic()`**
   - Stops the background music if active.

### `HomeMenu.java`: The class 'HomeMenu' shows the game's home screen. It extends the 'MenuParent' class to offer the main menu functionality, which includes buttons for beginning the game, navigating to other menus (Info and Settings), and ending the game.

**Features**

- Play Button: Navigates to the first game level.
- Exit Button: Exits the application.
- Info Button: Navigates to the information/how to play menu.
- Settings Button: Navigates to the settings menu.
- Custom Background: Displays a background image specific to the home menu.
- Background Music: Plays music when the home menu is active.

**Core Methods**

1. **`private void exitGame()`**
   - Exits the application by terminating the program.

2. **`public ImageView getBackground()`**
   - Returns the background image of the home menu.
  
### `InfoMenu.java`: The class 'InfoMenu' shows the game's information screen. It extends the 'MenuParent' class and adds functionality for showing game information, including a background image, background music, and a button to return to the home menu.

**Features**

- Home Button: Navigates back to the home menu.
- Custom Background: Displays a background image specific to the information menu.
- Background Music: Plays music when the information menu is active.

**Core Methods**

1. **`protected void initializeControls()`**
   - Initializes the controls for the info menu, such as the home button.
   - The home button is placed at the corner of the screen and triggers navigation back to the home menu.

### `SettingsMenu.java`: The 'SettingsMenu' class shows the screen where users can control the game's audio settings, such as increasing or decreasing volume. It also allows you to navigate back to the home menu.

**Features**

- Volume Controls: Buttons to increase or decrease the game’s volume.
- Navigation: A button to navigate back to the home menu.
- Sound Management: Uses the `SoundManager` to control the volume levels.

**Core Methods**

1. **`public SettingsMenu(Stage stage, double screenHeight, double screenWidth)`**
   - Initializes the settings menu with the given stage and screen dimensions.
   - Sets up the `SoundManager` instance for volume control and calls `initializeControls()` to set up the buttons.

2. **`protected void initializeControls()`**
   - Sets up the buttons for adjusting the volume and the home button for navigation.
   - The increase and decrease volume buttons adjust the volume by `VOLUME_INCREMENT`.

3. **`private void printVolumeLevel()`**
   - Prints the current volume level to the console after adjusting it using the volume buttons.
  
### `PauseMenu.java`: The 'PauseMenu' class shows the game's pause menu, which appears during gameplay. It allows users to resume the game or return to the main menu. The class handles user input, such as keyboard and button presses, and manages the transition from the pause menu to gameplay.

**Features**

- Resume Game: Allows users to resume gameplay by pressing the Spacebar or clicking the "Resume" button.
- Navigation: A button to navigate back to the home menu.
- Key Press Handling: Listens for the Spacebar key press to resume the game.
- Level Transition: Manages the transition back to the current level when resuming the game.

**Core Methods**

1. **`public PauseMenu(Stage stage, double screenHeight, double screenWidth, LevelParent currentLevel)`**
     - Initializes the pause menu with the given stage, screen dimensions, and current level.
     - Calls `initializeControls()` to set up user interface elements, including key press handling.

2. **`protected void initializeControls()`**
     - Sets up the key press event listener for resuming the game (Spacebar) and the home button for navigation.
     - The home button leads back to the home menu.

3. **`public void resumeGame()`**
     - Resumes the game by transitioning to the current level and stopping the background music.

4. **`private void transitionToLevel()`**
     - Transitions to the current level by setting the scene to the level's scene and starting the game.
     - If the current level is `null`, no transition occurs.

### `WinMenu.java`: The 'WinMenu' class shows the menu that appears after the player wins the game. It includes a button that allows players to return to the home menu after finishing the game.

**Features**

- Win Screen: Displays the win screen after the player wins the game.
- Navigation: A button to navigate back to the home menu after winning.

**Core Methods**

1. **`public WinMenu(Stage stage, double screenHeight, double screenWidth)`**
   - Initializes the win menu with the specified stage, screen dimensions, and background music.
   - Calls `initializeControls()` to set up the UI elements, including the home button.

2. **`protected void initializeControls()`**
   - Sets up the home button to allow navigation back to the home menu.
   - The button is placed at the top right of the screen, and clicking it takes the player to the home menu.

### `LoseMenu.java`: The 'LoseMenu' class shows the game over screen that appears when the player loses the game. It allows the player to navigate to the home menu or the next level by pressing the space key.

**Features**

- Game Over Screen: Displays a "Game Over" screen after the player loses the game.
- Keyboard Navigation: The space key allows the player to restart to level 1.
- Home Button: A button that navigates the player back to the home menu.

**Core Methods**

1. **`public LoseMenu(Stage stage, double screenHeight, double screenWidth)`**
   - Initializes the lose menu with the specified stage, screen dimensions, and background music.
   - Calls `initializeControls()` to set up the UI elements, including the home button and keyboard input.

2. **`protected void initializeControls()`**
   - Sets up keyboard controls to allow the player to transition to the level 1 by pressing the space key.
   - Sets up the home button to allow the player to return to the home menu.

### com.example.demo.powerup

### `PowerUpManager`: The `PowerUpManager` class is responsible for managing power-ups in the game, such as spawning, updating, and handling collisions with the player's plane. It ensures that power-ups like hearts are created, displayed, and collected when the player collides with them.

**Features**

- Spawn Power-Ups: Generates power-ups at random locations within the defined screen bounds.
- Collision Detection: Detects collisions between the player's plane and power-ups. If a collision is detected, the corresponding power-up is activated.
- Power-Up Update: Updates the list of active power-ups, removing destroyed ones from the scene.

**Core Methods**

1. **`public PowerUpManager(Group root, UserPlane user)`**
     - Initializes the PowerUpManager with the root group and the player's plane.
     - Creates an empty list for tracking active power-ups.

2. **`public void spawnPowerUp()`**
     - Spawns a new power-up at a random location within the defined bounds with a given spawn chance.
     - The power-up is added to the scene and the active power-ups list.

3. **`public void updatePowerUps()`**
     - Updates the active power-ups list by removing any power-ups that have been destroyed. The destroyed power-ups are also removed from the scene.

4. **`public void checkForCollisions()`**
     - Checks if any active power-ups have collided with the player's plane. If a collision occurs, the power-up is activated (e.g., increases health for a `GainHeart`), and the power-up is removed from both the list and the scene.

5. **`public List<ActiveActorDestructible> getActivePowerUps()`**
     - Returns a list of the currently active power-ups.
     - A list of `ActiveActorDestructible` objects representing the active power-ups.

### `GainHeart`: The 'GainHeart' class indicates a health power-up in the game that, when gained, increases the user's health. When the player collides with the power-up, their health increases and the power-up is destroyed.

**Features**

- Health Boost: When collected, the heart power-up increases the user's health by a fixed amount.
- Heart Icon: The power-up is visually represented by a heart image.
- Destruction: After being collected, the power-up is destroyed, and its icon is removed from the screen.

**Core Methods**

1. **`public GainHeart(double x, double y, UserPlane user)`**
     - Constructs the `GainHeart` power-up at the specified position and associates it with the `UserPlane` instance.

2. **`public void plusHealth()`**
     - Increases the health of the `UserPlane` by the amount specified in `HEART_GAIN` (1).
     - After collection, the power-up is destroyed.

3. **`public void destroy()`**
     - Destroys the power-up and removes it from the scene.
     - Prints a log message indicating the destruction of the power-up.

4. **`public void updateActor()`**
     - This method is not implemented for `GainHeart` since the power-up does not have dynamic updates after creation (it’s destroyed upon collection).

5. **`public void updatePosition()`**
     - This method is not implemented for `GainHeart` as the power-up’s position does not change after its creation.

6. **`public void takeDamage()`**
     - This method is not implemented for `GainHeart`, as the power-up cannot take damage.

### com.example.demo.transition

### `TransitionParent.java`: The 'TransitionParent' class is the base class for all transition screens in the game, including level transitions and scene changes. It is in charge of configuring the transition scene, including the background picture. 

**Features**

- Background Image: The class handles setting up and displaying a background image for the transition screen.
- Observable: The class extends `Observable` to notify observers when transitioning to the next level or scene.

**Core Methods**

1. **`public TransitionParent(Stage stage, String backgroundImageName, double screenHeight, double screenWidth)`**
     - Initializes the transition screen with the specified parameters, including the background image and the scene setup.

2. **`public Scene initializeScene()`**
     - Initializes and returns the scene that will be displayed on the stage during the transition.

3. **`protected void initializeControls()`**
     - This is a placeholder method intended to be overridden by subclasses. It allows for adding specific controls for each transition screen.

4. **`private void initializeBackground()`**
     - Initializes the background image. Ensures that the image preserves its aspect ratio and is focus traversable.

5. **`public void goToNextLevel(String levelName)`**
     - Triggers the transition to the next level by notifying observers.

### `TransitionOne.java`:  The 'TransitionOne' class shows the game's first transition screen. It extends the 'TransitionParent' class and manages the controls and actions for this specific transition, including responding to user input (the Enter key) to proceed to the next level.

**Features**
- Background Image: Displays a specific background image for the transition screen.
- Key Event Handling: Listens for the Enter key to trigger a transition to the next level (LevelTwo).

**Core Methods**

1. **`public TransitionOne(Stage stage, double screenHeight, double screenWidth)`**
     - Constructs the `TransitionOne` screen and initializes it with the specified stage and screen dimensions, as well as the background image.

2. **`protected void initializeControls()`**
     - Initializes the controls for the transition screen. Specifically, it sets up a key listener that listens for the Enter key. When the Enter key is pressed, the game transitions to the next level (LevelTwo).

### `TransitionTwo.java`:  The 'TransitionTwo' class shows the game's second transition screen. It extends the 'TransitionParent' class and manages the controls and actions for this specific transition, including responding to user input (the Enter key) to proceed to the next level.

**Features**
- Background Image: Displays a specific background image for the transition screen.
- Key Event Handling: Listens for the Enter key to trigger a transition to the next level (LevelThree).

**Core Methods**

1. **`public TransitionTwo(Stage stage, double screenHeight, double screenWidth)`**
     - Constructs the `TransitionTwo` screen and initializes it with the specified stage and screen dimensions, as well as the background image.

2. **`protected void initializeControls()`**
     - Initializes the controls for the transition screen. Specifically, it sets up a key listener that listens for the Enter key. When the Enter key is pressed, the game transitions to the next level (LevelThree).

### `TransitionThree.java`:  The 'TransitionThree' class shows the game's third transition screen. It extends the 'TransitionParent' class and manages the controls and actions for this specific transition, including responding to user input (the Enter key) to proceed to the next level.

**Features**
- Background Image: Displays a specific background image for the transition screen.
- Key Event Handling: Listens for the Enter key to trigger a transition to the next level (LevelBoss).

**Core Methods**

1. **`public TransitionThree(Stage stage, double screenHeight, double screenWidth)`**
     - Constructs the `TransitionThree` screen and initializes it with the specified stage and screen dimensions, as well as the background image.

2. **`protected void initializeControls()`**
     - Initializes the controls for the transition screen. Specifically, it sets up a key listener that listens for the Enter key. When the Enter key is pressed, the game transitions to the next level (LevelBoss).

### com.example.demo.ui

### `BossHealthDisplay.java`: The 'BossHealthDisplay' class represents the boss's health meter in the game. It displays the boss's current health and dynamically changes the health bar whenever it gets hit, receiving damage.

**Features**

- Health Bar: A visual representation of the boss's health.
- Dynamic Update: The health bar's width is updated based on the current health of the boss.
- Customizable Position: You can set the position of the health bar on the screen.

**Core Methods**

1. **`public BossHealthDisplay(double xPosition, double yPosition, double maxHealth)`**
     - Initializes the boss health display, setting its maximum health and initial health.

2. **`private void initializeContainer()`**
     - Initializes the container for the health bar and its background.

3. **`private void initializeBossHealth()`**
     - Initializes the health bar and its background, and adds them to the container.

4. **`public void updateHealth(double newHealth)`**
     - Updates the health bar's width based on the current health. The health bar shrinks as the boss takes damage.

5. **`public StackPane getContainer()`**
     - Returns the container holding the health bar and its background. This container can be added to a scene to display the health bar.


### Modified Classes:

## 1. Controller.java

### 1.1 Transition from a Single Level to Multiple Menus and Levels
#### Old Code:
- The controller was specifically designed to handle only the initial game level (`LEVEL_ONE_CLASS_NAME`).
- The game launched directly into the first level, with no menu or transition screens.

#### New Code:
- The controller was updated to handle multiple game states (home menu, pause menu, level transitions, etc.).
- The `launchGame` method was updated to show the home menu instead of directly jumping to a level (`goToMenu(HOME_MENU)`).
- New methods were added to:
  - Navigate to menus (`goToMenu`),
  - Handle transitions (`showTransitionPrompt`),
  - Load game levels dynamically based on the class name provided.
- This allows for flexible transitions between the game’s various menus, levels, and transition screens.

### 1.2 Handling of the Current Level
#### Old Code:
- The old code did not track or manage the current level explicitly.
- It simply loaded the first level and did not store a reference to the current level.

#### New Code:
- A `currentLevel` field was introduced to track the currently active game level.
- This ensures that the controller can pass the current level to the menu screens, enabling features like pause functionality.

### 1.3 Error Handling
#### Old Code:
- The error handling in the old code was basic. 
- If an error occurred during class loading or instantiation, the error message was displayed in an alert without much detail.

#### New Code:
- The error handling was improved slightly.
- In case of exceptions, error details are logged to the console, and an alert is shown with the exception stack trace.
- This helps developers diagnose issues better during the development and debugging process.

### 1.4 Additional Methods for Menu and Transition Management
#### Old Code:
- The old code only had the `goToLevel` method for navigating to the first level.

#### New Code:
- Added `goToMenu`, `showTransitionPrompt`, and updated `update` to manage transitions between different game states dynamically.
- The `update` method now checks whether the name passed corresponds to a menu, transition, or level and acts accordingly by calling the appropriate methods.


## 2. Boss.java

### 2.1 Projectile Firing
#### Old Code:
- The Boss fired a projectile randomly based on a defined fire rate.

#### New Code:
- The projectile firing logic remains similar, but the `fireProjectile` method now returns a `BossProjectile` with an updated spawn position.
- The Boss fires projectiles based on its fire rate, determined randomly each frame.

### 2.2 Health Management
#### Old Code:
- Health was managed by a simple method, and the Boss could take damage when not shielded.

#### New Code:
- The health is updated visually with the `levelView.updateBossHealth` method when the Boss takes damage.
- Damage is only taken if the shield is not active.


## 3. EnemyPlane.java

### 3.1 Overview of the Class
#### Old Code:
- The `EnemyPlane` class represented a simple enemy with basic horizontal movement and projectile firing.
- It only supported one type of enemy with fixed attributes such as health and velocity.
- The movement was strictly horizontal with no variation.

#### New Code:
- The `EnemyPlane` class now supports multiple types of enemy planes: **normal**, **special**, and **tank**.
- Each type has distinct attributes such as health and horizontal velocity:
  - **Normal**: Basic enemy plane with standard health and velocity.
  - **Special**: Includes a vertical movement pattern that shuffles up and down
  - **Tank**: A stronger enemy with higher health and slower horizontal velocity.
- The plane is destroyed if it moves outside the screen bounds.

### 3.2 Handling Multiple Types of Enemy Planes
#### Old Code:
- There was no distinction between types of enemy planes, and all enemies had the same attributes (health, velocity).
- The class did not account for specialized movement or firing patterns.

#### New Code:
- The `EnemyPlane` class now has logic to differentiate between normal, special, and tank enemies.
- Each type of enemy is configured with its specific attributes:
  - **Normal** enemies have a velocity of `-6` and 1 health point.
  - **Special** enemies have a velocity of `-9` and 3 health points, with a vertical movement pattern.
  - **Tank** enemies have a velocity of `-4` and 7 health points.
- These attributes are set using methods: `setNormalEnemyAttributes()`, `setSpecialEnemyAttributes()`, and `setTankPlaneAttributes()`.

### 3.3 Handling Screen Boundaries and Destruction
#### Old Code:
- The enemy plane was not destroyed when it went out of bounds, and there was no handling for boundaries.

#### New Code:
- The `updatePosition()` method checks if the enemy plane is out of bounds and calls `destroy()` to remove the enemy plane if it is out of bounds.This is done so that the number of enemies on the screen can change dynamically. The game logic ensures that new enemies will spawn as long as there are not more than 5 enemies currently on screen. When an enemy goes off-screen and is destroyed, it frees up space for new enemies to appear, maintaining the flow of the game.

### 3.6 Updating Actor State
#### Old Code:
- The `updateActor()` method simply updated the plane’s position horizontally.

#### New Code:
- The `updateActor()` method now calls `updatePosition()` to handle both horizontal and vertical movements (for special enemies), ensuring the enemy plane’s position is updated correctly.


## 4. GameOverImage.java WinImage.java

#### Old Code:
- The game over and win conditions were displayed using images (`gameOverImage.png` and `winImage.png`).
- These images were shown directly when the game ended, either after the player lost or won.

#### New Code:
- The static images for game over and win conditions were replaced with dedicated menus (`winMenu` and `loseMenu`).
- When the player wins or loses, the game now transitions to the appropriate menu rather than displaying an image.
- This change allows for a more interactive and dynamic transition after the game ends, where the player can choose to restart or exit the game from the win or lose menu.


## 5. LevelParent.java

### 5.1 Improved Scene Initialisation
#### Old code: 
- The background and controls were initialised directly within initializeBackground without checks for duplication.
- Control events were added inline, leading to potential redundancy.

#### New code: 
- Background initialisation now checks if it has already been added before proceeding.
- Controls are set up in a separate initializeControls method, ensuring better organisation and clarity.

### 5.2 Centralised Collision Handling (Attempting SRP)
#### Old code:
- Collision logic for actors and projectiles was handled directly in LevelParent.

#### New code:
- Delegated collision handling to a dedicated CollisionsManager. This class manages all collision-related updates and actions. Therefore improves code modularity by encapsulating collision logic in a single class.


## 6. UserPlane.java

### 6.1 Movement Boundaries Adjustment
#### Old code:
- The vertical boundaries were the only ones set.

#### New code:
- Horizontal boundaries were added to ensure `UserPlane` stays within the visible area

### 6.3 Horizontal Movements
#### Old code:
- Vertical movements were the only form of movement allowed for `UserPlane`.
- Horizontal movement was not implemented or considered, limiting the plane's movement to the vertical axis only.

#### New code:
- Horizontal movement has been added to allow the user to move the `UserPlane` left or right.
- This change provides more control and flexibility to the user by allowing movement in both vertical and horizontal directions.


## 7. Renaming and Reorganising into Packages
- Previously, many classes were not organised into packages, resulting in a flat file structure where all the classes were placed in the root directory.
- Some class names did not clearly describe their purpose or functionality, making it harder to understand their role in the game.

- Classes have been reorganised into appropriate packages to improve project structure and maintainability. For example:
  - Classes related to projectiles are moved to a `com.example.demo.projectiles` package. 
- Several classes have been renamed to better reflect their purpose. For example:
  - `ShieldImage` was renamed to `ShieldDisplay` to more accurately describe the class's function of displaying the shield.


---

## UNEXPECTED PROBLEMS
1. Encountered difficulties running the game in both IntelliJ IDEA and Eclipse during the initial setup:
   - In IntelliJ IDEA, Microsoft Defender blocked the project from running therefore causing delays.
   - In Eclipse, an unidentified issue prevented the project from building
- Hence the project could not run properly. As a result, progress on the project was delayed by approximately a week.
- Solution: The issue was ultimately resolved by deleting all project files and re-downloading IntelliJ IDEA.
