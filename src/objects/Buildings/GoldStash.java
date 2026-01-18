/**
 * GoldStash.java
 * The GoldStash class for BenumZombs, defining gold stash properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

public class GoldStash extends Building {

    /**
     * Constructor for GoldStash
     * Precondition: N/A
     * Postcondition: GoldStash object is created
     * @param x the x-coordinate of the GoldStash
     * @param y the y-coordinate of the GoldStash
     */
    public GoldStash(double x, double y) {
        super(x, y, 35, 35, "Gold Stash", "goldStash.png");
        this.woodCost = 0;
        this.stoneCost = 0;
        this.description = "Establishes your base and holds your gold. Protect this!";
        this.isLocked = false;

        this.maxHealth = 1500;
        this.health = 1500;

        this.limits = 1;

        loadSprites("goldStash", false, false, false, null);

        this.upgradeGoldCosts = new int[]{5000, 10000, 16000, 20000, 32000, 10000, 40000};
        this.upgradeWoodCosts = new int[]{0, 0, 0, 0, 0, 0, 0};
        this.upgradeStoneCosts = new int[]{0, 0, 0, 0, 0, 0, 0};
    }

    /**
     * Creates a copy of the GoldStash
     * Precondition: N/A
     * Postcondition: returns a new GoldStash object at specified coordinates
     * @param x the x-coordinate of the new GoldStash
     * @param y the y-coordinate of the new GoldStash
     * @return a new GoldStash object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new GoldStash(x, y);
    }

    /**
     * Indicates that GoldStash is an building that unlocks others
     * Precondition: N/A
     * Postcondition: returns true
     * @return true, indicating GoldStash unlocks others
     */
    @Override
    public boolean isUnlocker() {
        return true;
    }

    /**
     * Indicates that GoldStash cannot be sold
     * Precondition: N/A
     * Postcondition: returns false
     * @return false, indicating GoldStash cannot be sold
     */
    @Override
    public boolean canBeSold() {
        return false; 
    }

    /**
     * Gets the description of the GoldStash
     * Precondition: N/A
     * Postcondition: returns the description of the GoldStash
     * @return the description of the GoldStash
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the GoldStash
     * Precondition: N/A
     * Postcondition: The GoldStash state is updated
     */
    @Override
    public void update() {}
}