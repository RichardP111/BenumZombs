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

public class GoldStash extends Building {

    /**
     * Constructor for GoldStash
     * Precondition: N/A
     * Postcondition: GoldStash object is created
     * @param x the x-coordinate of the GoldStash
     * @param y the y-coordinate of the GoldStash
     */
    public GoldStash(double x, double y) {
        super(x, y, 35, 35, "Gold Stash", "goldStash.png");
        this.cost = 10;
        this.description = "";
    }

    /**
     * Gets the description of the GoldStash
     * Precondition: N/A
     * Postcondition: returns the description of the GoldStash
     * @return the description of the GoldStash
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the GoldStash
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The GoldStash is drawn on the screen
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
     * Updates the GoldStash
     * Precondition: N/A
     * Postcondition: The GoldStash state is updated
     */
    @Override
    public void update() {}
}