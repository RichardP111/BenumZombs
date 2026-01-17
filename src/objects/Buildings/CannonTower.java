/**
 * CannonTower.java
 * The CannonTower class for BenumZombs, defining cannon tower properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;

public class CannonTower extends Building {

    /**
     * Constructor for CannonTower
     * Precondition: N/A
     * Postcondition: CannonTower object is created
     * @param x the x-coordinate of the CannonTower
     * @param y the y-coordinate of the CannonTower
     */
    public CannonTower(double x, double y) {
        super(x, y, 35, 35, "Cannon Tower", "cannonTower.png");
        this.cost = 10;
        this.description = "";
    }

    /**
     * Gets the description of the CannonTower
     * Precondition: N/A
     * Postcondition: returns the description of the CannonTower
     * @return the description of the CannonTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the CannonTower
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The CannonTower is drawn on the screen
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
     * Updates the CannonTower
     * Precondition: N/A
     * Postcondition: The CannonTower state is updated
     */
    @Override
    public void update() {}
}