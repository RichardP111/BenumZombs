/**
 * Stone.java
 * The Stone class for BenumZombs, defining stone properties and behaviors
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-10
 */

package objects;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Stone {
    private final int x, y;
    private static final int SIZE = 120; //size of rock
    private double shakeOffset = 0;
    private static final double RECOVERY_RATE = 0.9;
    private static Image stoneImage;

    /**
     * Constructor for Stone object
     * Precondition: x and y are valid coordinates within the game window
     * Postcondition: A Stone object is created at the specified coordinates
     * @param x the x-coordinate of the stone
     * @param y the y-coordinate of the stone
     */
    public Stone(int x, int y) {
        this.x = x;
        this.y = y;
        
        if (stoneImage == null) {
            try {
                stoneImage = ImageIO.read(getClass().getResource("/assets/images/resources/stone.png"));
            } catch (IOException | IllegalArgumentException | NullPointerException e) {
                System.out.println("Stone.java - Could not find stone image, enjoy a game without stones: " + e.getMessage());
            }
        }
    }

    /**
     * Plays the stone's shake animation
     * Precondition: N/A
     * Postcondition: The stone's shake offset is set to initiate the animation
     */
    public void playAnimation() {
        this.shakeOffset = 15;
    }

    /**
     * Updates the stone's animation state
     * Precondition: N/A
     * Postcondition: The stone's shake offset is updated
     */
    public void update() {
        if (Math.abs(shakeOffset) > 0.5) {
            shakeOffset *= RECOVERY_RATE;
        } else {
            shakeOffset = 0;
        }
    }

    /**
     * Draws the stone on the provided Graphics2D context
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The stone image is drawn at its coordinates
     * @param g2d the Graphics2D object to draw on
     */
    public void draw(Graphics2D g2d) {
        if (stoneImage != null) {
            g2d.drawImage(stoneImage, x + (int)shakeOffset, y, SIZE, SIZE, null);
        }
    }

    /**
     * Gets the bounding rectangle of the stone for collision detection
     * Precondition: N/A
     * Postcondition: A Rectangle object representing the stone's bounds is returned
     * @return a Rectangle representing the stone's bounding box
     */
    public Rectangle getBounds() {
        int buffer = SIZE / 4; 
        return new Rectangle(x + buffer, y + buffer, SIZE - 2 * buffer, SIZE - 2 * buffer);
    }
}

