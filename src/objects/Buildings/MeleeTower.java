/**
 * MeleeTower.java
 * The MeleeTower class for BenumZombs, defining melee tower properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

public class MeleeTower extends Building {

    /**
     * Constructor for MeleeTower
     * Precondition: N/A
     * Postcondition: MeleeTower object is created
     * @param x the x-coordinate of the MeleeTower
     * @param y the y-coordinate of the MeleeTower
     */
    public MeleeTower(double x, double y) {
        super(x, y, 35, 35, "Melee Tower", "meleeTower.png");
        this.woodCost = 10;
        this.stoneCost = 10;
        this.description = "High damage, single target, close-range directional tower.";
        this.isLocked = true;

        this.maxHealth = 200;
        this.health = 200;

        this.limits = 6;

        this.upgradeGoldCosts = new int[]{100, 200, 600, 1200, 2000, 8000, 35000};
        this.upgradeWoodCosts = new int[]{25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[]{25, 30, 40, 50, 70, 300, 800};
    }

    /**
     * Creates a copy of the MeleeTower
     * Precondition: N/A
     * Postcondition: returns a new MeleeTower object at specified coordinates
     * @param x the x-coordinate of the new MeleeTower
     * @param y the y-coordinate of the new MeleeTower
     * @return a new MeleeTower object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new MeleeTower(x, y);
    }

    /**
     * Gets the description of the MeleeTower
     * Precondition: N/A
     * Postcondition: returns the description of the MeleeTower
     * @return the description of the MeleeTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the MeleeTower
     * Precondition: N/A
     * Postcondition: The MeleeTower state is updated
     */
    @Override
    public void update() {}
}