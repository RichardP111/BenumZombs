/**
 * Wall.java
 * The Wall class for BenumZombs, defining wall properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

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
        this.woodCost = 2;
        this.stoneCost = 0;
        this.description = "Blocks enemies from reaching your towers.";
        this.isLocked = true;

        this.maxHealth = 150 + (level * 100);
        this.health = maxHealth;

        this.limits = 250;

        loadSprites("wall", false, false, false, null);

        this.upgradeGoldCosts = new int[] {0, 5, 30, 60, 80, 100, 250, 800};
        this.upgradeWoodCosts = new int[] {2, 0, 0, 0, 0, 0, 0};
        this.upgradeStoneCosts = new int[] {0, 2, 0, 0, 0, 0, 0};
    }

    /**
     * Creates a copy of the Wall
     * Precondition: N/A
     * Postcondition: returns a new Wall object at specified coordinates
     * @param x the x-coordinate of the new Wall
     * @param y the y-coordinate of the new Wall
     * @return a new Wall object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new Wall(x, y);
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
     * Precondition: N/A
     * Postcondition: The Wall is drawn on the screen
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
     * Updates the Wall
     * Precondition: N/A
     * Postcondition: The Wall state is updated
     */
    @Override
    public void update() {}
}