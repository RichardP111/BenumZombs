/**
 * SlowTrap.java
 * The SlowTrap class for BenumZombs, defining slow trap properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

public class SlowTrap extends Building {

    /**
     * Constructor for SlowTrap
     * Precondition: N/A
     * Postcondition: SlowTrap object is created
     * @param x the x-coordinate of the SlowTrap
     * @param y the y-coordinate of the SlowTrap
     */
    public SlowTrap(double x, double y) {
        super(x, y, 35, 35, "Slow Trap", "slowTrap.png");
        this.woodCost = 5;
        this.stoneCost = 5;
        this.description = "Slows enemies from entering your base.";
        this.isLocked = true;

        this.maxHealth = 150;
        this.health = 150;

        this.limits = 12;

        loadSprites("slowTrap", false, false, false, null);

        this.upgradeGoldCosts = new int[] {100, 200, 400, 600, 800, 1000, 1500};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
    }

    /**
     * Creates a copy of the SlowTrap
     * Precondition: N/A
     * Postcondition: returns a new SlowTrap object at specified coordinates
     * @param x the x-coordinate of the new SlowTrap
     * @param y the y-coordinate of the new SlowTrap
     * @return a new SlowTrap object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new SlowTrap(x, y);
    }

    /**
     * Gets the description of the SlowTrap
     * Precondition: N/A
     * Postcondition: returns the description of the SlowTrap
     * @return the description of the SlowTrap
     */
    @Override
    public String getDescription() {
        return description;
    }


    /**
     * Updates the SlowTrap
     * Precondition: N/A
     * Postcondition: The SlowTrap state is updated
     */
    @Override
    public void update() {}
}