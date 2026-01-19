/**
 * Door.java
 * The Door class for BenumZombs, defining door properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

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
        this.woodCost = 5;
        this.stoneCost = 5;
        this.description = "Allows party members to enter your base.";
        this.isLocked = true;

        this.maxHealth = 500;
        this.health = 500;

        this.limits = 40;

        loadSprites("door", false, false, false, null);

        this.upgradeGoldCosts = new int[] {0, 10, 50, 70, 150, 200, 400, 800};
        this.upgradeWoodCosts = new int[] {5, 5, 0, 0, 0, 0, 0};
        this.upgradeStoneCosts = new int[] {5, 5, 0, 0, 0, 0, 0};
    }

    /**
     * Creates a copy of the Door
     * Precondition: N/A
     * Postcondition: returns a new Door object at specified coordinates
     * @param x the x-coordinate of the new Door
     * @param y the y-coordinate of the new Door
     * @return a new Door object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new Door(x, y);
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
     * Precondition: N/A
     * Postcondition: The Door is drawn on the screen
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        //************* Draw base *************//
        if (baseSprites != null) {
            g2d.drawImage(baseSprites[level-1], (int)x, (int)y, width, height, null);
        }
    }

    /**
     * Updates the Door
     * Precondition: N/A
     * Postcondition: The Door state is updated
     */
    @Override
    public void update() {}
}