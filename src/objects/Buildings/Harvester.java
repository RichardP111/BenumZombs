/**
 * Harvester.java
 * The Harvester class for BenumZombs, defining harvester properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Graphics2D;
import systems.BuildingSystem;
import systems.ZombieSystem;
import systems.ResourceSystem;

public class Harvester extends Building {

    /**
     * Constructor for Harvester
     * Precondition: N/A
     * Postcondition: Harvester object is created
     * @param x the x-coordinate of the Harvester
     * @param y the y-coordinate of the Harvester
     */
    public Harvester(double x, double y) {
        super(x, y, 35, 35, "Harvester", "harvester.png");
        this.woodCost = 5;
        this.stoneCost = 5;
        this.description = "Harvests resources automatically, fuelled by gold. Hit with a pickaxe to collect.";
        this.isLocked = true;

        this.maxHealth = 150;
        this.health = 150;

        this.limits = 2;

        loadSprites("harvester", false, true, true, null);

        this.upgradeGoldCosts = new int[] {100, 200, 600, 1200, 2000, 8000, 10000};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 600};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 600};
    }

    /**
     * Creates a copy of the Harvester
     * Precondition: N/A
     * Postcondition: returns a new Harvester object at specified coordinates
     * @param x the x-coordinate of the new Harvester
     * @param y the y-coordinate of the new Harvester
     * @return a new Harvester object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new Harvester(x, y);
    }

    /**
     * Gets the description of the Harvester
     * Precondition: N/A
     * Postcondition: returns the description of the Harvester
     * @return the description of the Harvester
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the Harvester
     * Precondition: N/A
     * Postcondition: The Harvester state is updated
     */
    @Override
    public void update(ResourceSystem resourceSystem, ZombieSystem zombieSystem, BuildingSystem buildingSystem) {
        super.update(resourceSystem, zombieSystem, buildingSystem);
    }

    /**
     * Draws the Harvester
     * Precondition: N/A
     * Postcondition: The Harvester is drawn on the screen
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (baseSprites != null) {
            g2d.drawImage(baseSprites[level-1], (int)x, (int)y, width, height, null);
        }
    }

    @Override
    public void update() {}
}