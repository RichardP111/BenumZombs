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

public class RandomGeneration {
    private static final Random random = new Random();

    /**
     * Gets a random location within the game boundaries
     * Precondition: None
     * Postcondition: A random Point within the game boundaries is returned
     * @return
     */
    public static Point getRandomLocation() {
        int minX = BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS;
        int maxX = BenumZombsGame.PLAY_AREA - BenumZombsGame.BORDER_THICKNESS - 100;
        int minY = BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS;
        int maxY = BenumZombsGame.PLAY_AREA - BenumZombsGame.BORDER_THICKNESS - 100;

        int randomX = random.nextInt(maxX - minX + 1) + minX;
        int randomY = random.nextInt(maxY - minY + 1) + minY;

        return new Point(randomX, randomY);
    }
}
