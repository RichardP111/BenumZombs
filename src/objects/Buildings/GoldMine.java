/**
 * GoldMine.java
 * The GoldMine class for BenumZombs, defining gold mine properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

public class GoldMine extends Building {

    /**
     * Constructor for GoldMine
     * Precondition: N/A
     * Postcondition: GoldMine object is created
     * @param x the x-coordinate of the GoldMine
     * @param y the y-coordinate of the GoldMine
     */
    public GoldMine(double x, double y) {
        super(x, y, 35, 35, "Gold Mine", "goldMine.png");
        this.woodCost = 5;
        this.stoneCost = 5;
        this.description = "Generates gold every second for your party.";

        this.isLocked = true;

        this.limits = 8;
    }

    /**
     * Creates a copy of the GoldMine
     * Precondition: N/A
     * Postcondition: returns a new GoldMine object at specified coordinates
     * @param x the x-coordinate of the new GoldMine
     * @param y the y-coordinate of the new GoldMine
     * @return a new GoldMine object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new GoldMine(x, y);
    }

    /**
     * Gets the description of the GoldMine
     * Precondition: N/A
     * Postcondition: returns the description of the GoldMine
     * @return the description of the GoldMine
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the GoldMine
     * Precondition: N/A
     * Postcondition: The GoldMine state is updated
     */
    @Override
    public void update() {}
}