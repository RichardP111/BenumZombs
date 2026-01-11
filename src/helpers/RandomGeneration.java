/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Random generator class to find a random area to spwan a object
 */

package helpers;

import game.BenumZombsGame;
import java.awt.Point;
import java.util.Random;

public class RandomGeneration {
    private static final Random random = new Random();

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
