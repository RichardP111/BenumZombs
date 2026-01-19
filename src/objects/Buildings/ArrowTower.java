/**
 * ArrowTower.java
 * The ArrowTower class for BenumZombs, defining arrow tower properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import systems.ResourceSystem;

public class ArrowTower extends Building {

    /**
     * Constructor for ArrowTower
     * Precondition: N/A
     * Postcondition: ArrowTower object is created
     * @param x the x-coordinate of the ArrowTower
     * @param y the y-coordinate of the ArrowTower
     */
    public ArrowTower(double x, double y) {
        super(x, y, 35, 35, "Arrow Tower", "arrowTower.png");
        this.woodCost = 5;
        this.stoneCost = 5;
        this.description = "Single target, fast firing tower.";
        this.isLocked = true;

        this.maxHealth = 150;
        this.health = 150;

        this.limits = 6;

        loadSprites("arrowTower", false, true, false, "arrowTower_projectile.png");

        this.upgradeGoldCosts = new int[] {100, 200, 600, 1200, 2000, 8000, 35000};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
    }

    /**
     * Creates a copy of the ArrowTower
     * Precondition: N/A
     * Postcondition: returns a new ArrowTower object at specified coordinates
     * @param x the x-coordinate of the new ArrowTower
     * @param y the y-coordinate of the new ArrowTower
     * @return a new ArrowTower object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new ArrowTower(x, y);
    }

    /**
     * Gets the description of the ArrowTower
     * Precondition: N/A
     * Postcondition: returns the description of the ArrowTower
     * @return the description of the ArrowTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the ArrowTower
     * Precondition: N/A
     * Postcondition: The ArrowTower state is updated
     */
    @Override
    public void update(ResourceSystem resourceSystem) {
        super.update(resourceSystem);
        this.rotation += 0.02; 
    }

    @Override
    public void update() {}
}