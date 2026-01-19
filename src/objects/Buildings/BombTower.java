/**
 * Wall.java
 * The Wall class for BenumZombs, defining wall properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import systems.ResourceSystem;

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
        this.woodCost = 10;
        this.stoneCost = 10;
        this.description = "Large area of effect damage, very slow firing tower.";
        this.isLocked = true;

        this.maxHealth = 150;
        this.health = 150;

        this.limits = 6;

        loadSprites("bombTower", false, true, false, "bombTower_projectile.png");

        this.upgradeGoldCosts = new int[] {100, 200, 600, 1200, 2000, 8000, 35000};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
    }

    /**
     * Creates a copy of the BombTower
     * Precondition: N/A
     * Postcondition: returns a new BombTower object at specified coordinates
     * @param x the x-coordinate of the new BombTower
     * @param y the y-coordinate of the new BombTower
     * @return a new BombTower object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new BombTower(x, y);
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
     * Updates the BombTower
     * Precondition: N/A
     * Postcondition: The BombTower state is updated
     */
    @Override
    public void update(ResourceSystem resourceSystem) {
        super.update(resourceSystem);
    }

    @Override
    public void update() {}
}