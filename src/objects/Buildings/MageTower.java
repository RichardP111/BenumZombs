/**
 * GoldStash.java
 * The GoldStash class for BenumZombs, defining gold stash properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

public class MageTower extends Building {

    /**
     * Constructor for MageTower
     * Precondition: N/A
     * Postcondition: MageTower object is created
     * @param x the x-coordinate of the MageTower
     * @param y the y-coordinate of the MageTower
     */
    public MageTower(double x, double y) {
        super(x, y, 35, 35, "Mage Tower", "mageTower.png");
        this.woodCost = 15;
        this.stoneCost = 15;
        this.description = "Multiple projectile, short range, fast firing tower.";

        this.isLocked = true;

        this.limits = 6;
    }

    /**
     * Creates a copy of the MageTower
     * Precondition: N/A
     * Postcondition: returns a new MageTower object at specified coordinates
     * @param x the x-coordinate of the new MageTower
     * @param y the y-coordinate of the new MageTower
     * @return a new MageTower object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new MageTower(x, y);
    }

    /**
     * Gets the description of the MageTower
     * Precondition: N/A
     * Postcondition: returns the description of the MageTower
     * @return the description of the MageTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the MageTower
     * Precondition: N/A
     * Postcondition: The MageTower state is updated
     */
    @Override
    public void update() {}
}