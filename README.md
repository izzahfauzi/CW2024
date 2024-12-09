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
   - **Level 3**: Introduces a new type of plane that moves horizontally to the left. It is slower compared to previous planes but has a higher health of 5, requiring more hits to be defeated.  

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

**Reasons for Not Implementing**: Limited time constraints prevented the addition of these features. 

---

## CHANGES IN CODE

### New Classes:

### Modified Classes:

---

## UNEXPECTED PROBLEMS
1. Encountered difficulties running the game in both IntelliJ IDEA and Eclipse during the initial setup:
   - In IntelliJ IDEA, Microsoft Defender blocked the project from running therefore causing delays.
   - In Eclipse, an unidentified issue prevented the project from building hence the project could not run properly.
As a result, progress on the project was delayed by approximately a week. The issue was ultimately resolved by deleting all project files and re-downloading IntelliJ IDEA.
