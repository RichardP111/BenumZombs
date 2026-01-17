/**
 * MeleeTower.java
 * The MeleeTower class for BenumZombs, defining melee tower properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;

public class MeleeTower extends Building {

    /**
     * Constructor for MeleeTower
     * Precondition: N/A
     * Postcondition: MeleeTower object is created
     * @param x the x-coordinate of the MeleeTower
     * @param y the y-coordinate of the MeleeTower
     */
    public MeleeTower(double x, double y) {
        super(x, y, 35, 35, "Melee Tower", "meleeTower.png");
        this.cost = 10;
        this.description = "";
    }

    /**
     * Gets the description of the MeleeTower
     * Precondition: N/A
     * Postcondition: returns the description of the MeleeTower
     * @return the description of the MeleeTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the MeleeTower
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The MeleeTower is drawn on the screen
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
     * Updates the MeleeTower
     * Precondition: N/A
     * Postcondition: The MeleeTower state is updated
     */
    @Override
    public void update() {}
}