/**
 * RandomGeneration.java
 * Helper class for generating random locations within the game boundaries in BenumZombs
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-10
 */

package helpers;

import game.BenumZombsGame;
import java.awt.Point;
import java.util.Random;
import objects.Buildings.Building;
import systems.BuildingSystem;

public class RandomGeneration {

    private static final Random random = new Random();
    private static final double SPAWN_RADIUS = BenumZombsGame.GRID_SIZE * 30;

    //************* Game Boundaries *************//
    private static final int MIN_X = BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS;
    private static final int MAX_X = BenumZombsGame.PLAY_AREA - BenumZombsGame.BORDER_THICKNESS - 100;
    private static final int MIN_Y = BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS;
    private static final int MAX_Y = BenumZombsGame.PLAY_AREA - BenumZombsGame.BORDER_THICKNESS - 100;

    /**
     * Gets a random location within the game boundaries
     * Precondition: N/A
     * Postcondition: A random Point within the game boundaries is returned
     * @return Point representing a random location
     */
    public static Point getRandomLocation() {

        int randomX = random.nextInt(MAX_X - MIN_X + 1) + MIN_X;
        int randomY = random.nextInt(MAX_Y - MIN_Y + 1) + MIN_Y;

        return new Point(randomX, randomY);
    }

    /**
     * Gets a random location around the player's base within a certain radius
     * Precondition: buildingSystem is valid and contains the player's base
     * Postcondition: A random Point around the base within the spawn radius is returned
     * @param buildingSystem the BuildingSystem containing the player's buildings
     * @return Point representing a random location around the base
     */
    public static Point getRandomBaseLocation(BuildingSystem buildingSystem) {
        Building stash = buildingSystem.getActiveStash();
        int centerX, centerY;
        
        //************* Determine Base Location *************//
        if (stash != null) {
            centerX = (int) (stash.getX() + stash.getWidth() / 2);
            centerY = (int) (stash.getY() + stash.getHeight() / 2);
        } else {
            centerX = (MIN_X + MAX_X) / 2;
            centerY = (MIN_Y + MAX_Y) / 2;
        }

        //************* Generate Random Point Around Base *************//
        double angle = Math.random() * Math.PI * 2;
        double radius = SPAWN_RADIUS + (Math.random() * 100 - 100);

        int randomX = (int) (centerX + radius * Math.cos(angle));
        int randomY = (int) (centerY + radius * Math.sin(angle));

        //************* Stay Within Game Boundaries *************//
        if (randomX < MIN_X) {
            randomX = MIN_X + 50;
        }
        if (randomX > MAX_X) {
            randomX = MAX_X - 50;
        }
        if (randomY < MIN_Y) {
            randomY = MIN_Y + 50;
        }
        if (randomY > MAX_Y) {
            randomY = MAX_Y - 50;
        }
        
        return new Point(randomX, randomY);
    }
}
