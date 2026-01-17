/**
 * GoldStash.java
 * The GoldStash class for BenumZombs, defining gold stash properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;

public class MageTower extends Building {

    /**
     * Constructor for MageTower
     * Precondition: N/A
     * Postcondition: MageTower object is created
     * @param x the x-coordinate of the MageTower
     * @param y the y-coordinate of the MageTower
     */
    public MageTower(double x, double y) {
        super(x, y, 35, 35, "Mage Tower", "mageTower.png");
        this.cost = 10;
        this.description = "";
    }

    /**
     * Gets the description of the MageTower
     * Precondition: N/A
     * Postcondition: returns the description of the MageTower
     * @return the description of the MageTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the MageTower
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The MageTower is drawn on the screen
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
     * Updates the MageTower
     * Precondition: N/A
     * Postcondition: The MageTower state is updated
     */
    @Override
    public void update() {}
}