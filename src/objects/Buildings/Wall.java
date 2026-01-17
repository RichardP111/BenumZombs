/**
 * Wall.java
 * The Wall class for BenumZombs, defining wall properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends Building {

    /**
     * Constructor for Wall
     * Precondition: N/A
     * Postcondition: Wall object is created
     * @param x the x-coordinate of the Wall
     * @param y the y-coordinate of the Wall
     */
    public Wall(double x, double y) {
        super(x, y, 35, 35, "Wall", "wall.png");
        this.cost = 10;
        this.description = "";
    }

    /**
     * Gets the description of the Wall
     * Precondition: N/A
     * Postcondition: returns the description of the Wall
     * @return the description of the Wall
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the Wall
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The Wall is drawn on the screen
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
     * Updates the Wall
     * Precondition: N/A
     * Postcondition: The Wall state is updated
     */
    @Override
    public void update() {}
}