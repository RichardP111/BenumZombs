/**
 * ArrowTower.java
 * The ArrowTower class for BenumZombs, defining arrow tower properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;

public class ArrowTower extends Building {

    /**
     * Constructor for ArrowTower
     * Precondition: N/A
     * Postcondition: ArrowTower object is created
     * @param x the x-coordinate of the ArrowTower
     * @param y the y-coordinate of the ArrowTower
     */
    public ArrowTower(double x, double y) {
        super(x, y, 35, 35, "Arrow Tower", "arrowTower.png");
        this.cost = 10;
        this.description = "Single target, fast firing tower";
    }

    /**
     * Gets the description of the ArrowTower
     * Precondition: N/A
     * Postcondition: returns the description of the ArrowTower
     * @return the description of the ArrowTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the ArrowTower
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The ArrowTower is drawn on the screen
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
     * Updates the ArrowTower
     * Precondition: N/A
     * Postcondition: The ArrowTower state is updated
     */
    @Override
    public void update() {}
}