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

public class SlowTrap extends Building {

    /**
     * Constructor for SlowTrap
     * Precondition: N/A
     * Postcondition: SlowTrap object is created
     * @param x the x-coordinate of the SlowTrap
     * @param y the y-coordinate of the SlowTrap
     */
    public SlowTrap(double x, double y) {
        super(x, y, 35, 35, "Slow Trap", "slowTrap.png");
        this.cost = 10;
        this.description = "";
    }

    /**
     * Gets the description of the SlowTrap
     * Precondition: N/A
     * Postcondition: returns the description of the SlowTrap
     * @return the description of the SlowTrap
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the SlowTrap
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The SlowTrap is drawn on the screen
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
     * Updates the SlowTrap
     * Precondition: N/A
     * Postcondition: The SlowTrap state is updated
     */
    @Override
    public void update() {}
}