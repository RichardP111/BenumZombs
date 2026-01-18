/**
 * CannonTower.java
 * The CannonTower class for BenumZombs, defining cannon tower properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import systems.ResourceSystem;

public class CannonTower extends Building {

    /**
     * Constructor for CannonTower
     * Precondition: N/A
     * Postcondition: CannonTower object is created
     * @param x the x-coordinate of the CannonTower
     * @param y the y-coordinate of the CannonTower
     */
    public CannonTower(double x, double y) {
        super(x, y, 35, 35, "Cannon Tower", "cannonTower.png");
        this.woodCost = 15;
        this.stoneCost = 15;
        this.description = "Area of effect damage, slow firing tower.";
        this.isLocked = true;

        this.maxHealth = 150;
        this.health = 150;

        this.limits = 6;

        loadSprites("cannonTower", false, true, false, "cannonTower_projectile.png");

        this.upgradeGoldCosts = new int[]{100, 200, 600, 1200, 2000, 8000, 35000};
        this.upgradeWoodCosts = new int[]{25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[]{25, 30, 40, 50, 70, 300, 800};
    }

    /**
     * Creates a copy of the CannonTower
     * Precondition: N/A
     * Postcondition: returns a new CannonTower object at specified coordinates
     * @param x the x-coordinate of the new CannonTower
     * @param y the y-coordinate of the new CannonTower
     * @return a new CannonTower object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new CannonTower(x, y);
    }

    /**
     * Gets the description of the CannonTower
     * Precondition: N/A
     * Postcondition: returns the description of the CannonTower
     * @return the description of the CannonTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the CannonTower
     * Precondition: N/A
     * Postcondition: The CannonTower state is updated
     */
    @Override
    public void update(ResourceSystem resourceSystem) {
        super.update(resourceSystem);
        this.rotation += 0.02; 
    }

    @Override
    public void update() {}
}