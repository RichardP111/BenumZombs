# BenumZombs

**Author:** Richard Pu  
**Version:** 1.0  
**Created:** January 2026

## 🧟‍♂️ About the Game

**BenumZombs** is a 2D survival tower defense game developed in Java. Players must gather resources, build a fortress, and defend their "Gold Stash" against nightly waves of zombies. As the game progresses, waves become harder, requiring better defenses, strategic building placement, and upgraded tools.

## 🎮 Gameplay Features

* **Day/Night Cycle:** Build during the day and defend at night.
* **Building System:** Construct walls, towers (Arrow, Cannon, Bomb, Mage), and traps to protect your base.
* **Resource Management:** Harvest wood and stone to craft buildings and tools.
* **Shop System:** Purchase upgrades and new items.
* **Survival Mechanics:** Manage your health and keep your Gold Stash safe. If your stash is destroyed, it's game over!
* **Custom Graphics & Audio:** Includes custom assets for zombies, buildings, and sound effects.

## ⌨️ Controls

| Key / Action | Function |
| :--- | :--- |
| **W, A, S, D** (or Arrows) | Move Player |
| **Left Click** | Attack / Harvest / Place Building |
| **Right Click** | Cancel Building Placement |
| **Space** | Toggle Auto-Swing |
| **B** | Open Shop |
| **Q** | Cycle Active Tool |
| **F** | Quick Use Health Potion |
| **0 - 9** | Select Building from Hotbar |
| **Esc** | Cancel Placement |
| **F12** | Developer Mode (Add Resources) |

## 🛠️ Project Structure

The project follows a standard Java application structure:

* `src/game`: Contains the core game loop (`BenumZombsGame.java`), main entry point (`Main.java`), and UI screens (`ShopScreen`, `SettingsScreen`, `StartMenu`).
* `src/objects`: Contains entities like `Player`, `Zombie`, `Building`, and various `Tools`.
* `src/systems`: Manages game logic subsystems like `CollisionSystem`, `ResourceSystem`, `BuildingSystem`, and `ZombieSystem`.
* `src/helpers`: Utility classes for rendering, sound, and math helper functions.
* `src/assets`: Stores game resources including images (`.png`, `.jpg`), fonts (`.ttf`), and sounds (`.wav`).

## 🚀 How to Run

1.  **Prerequisites:** Ensure you have Java Development Kit (JDK) installed.
2.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/richardp111/benumzombs.git](https://github.com/richardp111/benumzombs.git)
    ```
3.  **Compile and Run:**
    Navigate to the `src` directory and compile the Java files.
    ```bash
    javac game/Main.java
    java game.Main
    ```
    *(Note: Using an IDE like IntelliJ IDEA, Eclipse, or VS Code is recommended for handling the classpath and assets automatically.)*

## 📜 Acknowledgements

* **Google Sans Flex Font:** Used for UI text rendering.
* **External Libraries:** Project utilizes standard Java Swing and AWT libraries.
* **Documentation:** Additional design docs can be found [here](https://docs.google.com/document/d/1JccsGbz9-viOxhGuASJULYrJF2vKoy3G_eF5oNd1vVI/edit?usp=sharing).

## 📄 License

This project is for educational purposes.
