/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Stone Class which defines stone objects
 */

package objects;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Stone {
    private final int x;
    private final int y;
    private final int size = 120;
    private static Image stoneImage;

    /**
     * Constructor for Stone object
     * Precondition: x and y are valid coordinates within the game window
     * Postcondition: A Stone object is created at the specified coordinates
     * @param x
     * @param y
     */
    public Stone(int x, int y) {
        this.x = x;
        this.y = y;
        
        if (stoneImage == null) {
            try {
                stoneImage = ImageIO.read(getClass().getResource("/assets/images/resources/stone.png"));
            } catch (IOException | IllegalArgumentException | NullPointerException e) {
                System.out.println("Could not find stone image, enjoy a game without stones: " + e.getMessage());
            }
        }
    }

    /**
     * Draws the stone on the provided Graphics2D context
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The stone image is drawn at its coordinates
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        if (stoneImage != null) {
            g2d.drawImage(stoneImage, x, y, size, size, null);
        }
    }

    /**
     * Gets the bounding rectangle of the stone for collision detection
     * Precondition: None
     * Postcondition: A Rectangle object representing the stone's bounds is returned
     * @return
     */
    public Rectangle getBounds() {
        int buffer = size / 4; 
        return new Rectangle(x + buffer, y + buffer, size - 2 * buffer, size - 2 * buffer);
    }
}

