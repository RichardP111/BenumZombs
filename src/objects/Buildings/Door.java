/**
 * Door.java
 * The Door class for BenumZombs, defining door properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;

public class Door extends Building {

    /**
     * Constructor for Door
     * Precondition: N/A
     * Postcondition: Door object is created
     * @param x the x-coordinate of the Door
     * @param y the y-coordinate of the Door
     */
    public Door(double x, double y) {
        super(x, y, 35, 35, "Door", "door.png");
        this.cost = 10;
        this.description = "";
    }

    /**
     * Gets the description of the Door
     * Precondition: N/A
     * Postcondition: returns the description of the Door
     * @return the description of the Door
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the Door
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The Door is drawn on the screen
     * @param g2d the Graphics2D object used for drawing
     */
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(100, 65, 23)); 

        g2d.fillRect((int)x, (int)y, width, height);
        
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)x, (int)y, width, height);
    }

    /**
     * Updates the Door
     * Precondition: N/A
     * Postcondition: The Door state is updated
     */
    @Override
    public void update() {}
}