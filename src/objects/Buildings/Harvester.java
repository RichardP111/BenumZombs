/**
 * Harvester.java
 * The Harvester class for BenumZombs, defining harvester properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

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

        this.limits = 2;
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
    public void update() {}
}