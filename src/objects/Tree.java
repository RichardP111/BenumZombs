/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Tree Class which defines tree objects
 */

package objects;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tree {
    private final int x;
    private final int y;
    private final int size = 120;
    private static Image treeImage;

    /**
     * Constructor for Tree object
     * Precondition: x and y are valid coordinates within the game window
     * Postcondition: A Tree object is created at the specified coordinates
     * @param x
     * @param y
     */
    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
        
        if (treeImage == null) {
            try {
                treeImage = ImageIO.read(getClass().getResource("/assets/images/resources/tree.png"));
            } catch (IOException | IllegalArgumentException | NullPointerException e) {
                System.out.println("Could not find tree image, enjoy a game without trees: " + e.getMessage());
            }
        }
    }

    /**
     * Draws the tree on the provided Graphics2D context
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The tree image is drawn at its coordinates
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        if (treeImage != null) {
            g2d.drawImage(treeImage, x, y, size, size, null);
        }
    }

    /**
     * Gets the bounding rectangle of the tree for collision detection
     * Precondition: None
     * Postcondition: A Rectangle object representing the tree's bounds is returned
     * @return
     */
    public Rectangle getBounds() {
        int buffer = size / 4; 
        return new Rectangle(x + buffer, y + buffer, size - 2 * buffer, size - 2 * buffer);
    }
}