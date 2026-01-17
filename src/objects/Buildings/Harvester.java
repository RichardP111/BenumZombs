/**
 * Harvester.java
 * The Harvester class for BenumZombs, defining harvester properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;

public class Harvester extends Building {

    /**
     * Constructor for Harvester
     * Precondition: N/A
     * Postcondition: Harvester object is created
     * @param x the x-coordinate of the Harvester
     * @param y the y-coordinate of the Harvester
     */
    public Harvester(double x, double y) {
        super(x, y, 35, 35, "Harvester", "harvester.png");
        this.cost = 10;
        this.description = "";
    }

    /**
     * Gets the description of the Harvester
     * Precondition: N/A
     * Postcondition: returns the description of the Harvester
     * @return the description of the Harvester
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the Harvester
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The Harvester is drawn on the screen
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
     * Updates the Harvester
     * Precondition: N/A
     * Postcondition: The Harvester state is updated
     */
    @Override
    public void update() {}
}