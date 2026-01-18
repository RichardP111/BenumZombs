/**
 * ArrowTower.java
 * The ArrowTower class for BenumZombs, defining arrow tower properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

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

        this.limits = 6;
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
    public void update() {}
}