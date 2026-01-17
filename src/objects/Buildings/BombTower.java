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

public class BombTower extends Building {

    /**
     * Constructor for BombTower
     * Precondition: N/A
     * Postcondition: BombTower object is created
     * @param x the x-coordinate of the BombTower
     * @param y the y-coordinate of the BombTower
     */
    public BombTower(double x, double y) {
        super(x, y, 35, 35, "Bomb Tower", "bombTower.png");
        this.cost = 10;
        this.description = "";
    }

    /**
     * Gets the description of the BombTower
     * Precondition: N/A
     * Postcondition: returns the description of the BombTower
     * @return the description of the BombTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Draws the BombTower
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The BombTower is drawn on the screen
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
     * Updates the BombTower
     * Precondition: N/A
     * Postcondition: The BombTower state is updated
     */
    @Override
    public void update() {}
}