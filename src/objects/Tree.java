/**
 * Tree.java
 * The Tree class for BenumZombs, defining tree properties and behaviors
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-10
 */


package objects;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tree {
    private final int x, y;
    private static final int SIZE = 120; //size of tree
    private double shakeOffset = 0;
    private static final double RECOVERY_RATE = 0.9;
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
     * Plays the stone's shake animation
     * Precondition: None
     * Postcondition: The stone's shake offset is set to initiate the animation
     */
    public void playAnimation(){
        this.shakeOffset = 15;
    }

    /**
     * Updates the stone's animation state
     * Precondition: None
     * Postcondition: The stone's shake offset is updated
     */
    public void update(){
        if (Math.abs(shakeOffset) > 0.5) {
            shakeOffset *= RECOVERY_RATE;
        } else {
            shakeOffset = 0;
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
            g2d.drawImage(treeImage, x + (int)shakeOffset, y, SIZE, SIZE, null);
        }
    }

    /**
     * Gets the bounding rectangle of the tree for collision detection
     * Precondition: None
     * Postcondition: A Rectangle object representing the tree's bounds is returned
     * @return
     */
    public Rectangle getBounds() {
        int buffer = SIZE / 4; 
        return new Rectangle(x + buffer, y + buffer, SIZE - 2 * buffer, SIZE - 2 * buffer);
    }
}