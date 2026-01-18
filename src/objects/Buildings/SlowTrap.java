/**
 * ArrowTower.java
 * The ArrowTower class for BenumZombs, defining arrow tower properties and behaviors
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

        this.limits = 12;
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