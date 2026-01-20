/**
 * Harvester.java
 * The Harvester class for BenumZombs, defining harvester properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 * The following building is currently under development and may not be fully functional.
 */

package objects.Buildings;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import systems.BuildingSystem;
import systems.CollisionSystem;
import systems.ResourceSystem;
import systems.ZombieSystem;

public class Harvester extends Building {
    private long lastHarvestTime = 0;
    private long harvestCooldown = 2500;
    private int harvestAmount = 1;
    private Rectangle harvestRangeBox;

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
        this.description = "Harvests resources automatically over time.";
        this.isLocked = true;

        this.maxHealth = 150 + (level * 100);
        this.health = maxHealth;

        this.limits = 2;

        this.harvestCooldown = 2500 - (level * 100);
        this.harvestAmount = 1 + level;
        
        loadSprites("harvester", false, true, false, null);

        this.upgradeGoldCosts = new int[] {100, 200, 600, 1200, 2000, 8000, 10000};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 600};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 600};
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
     * @param resourceSystem the ResourceSystem object
     * @param zombieSystem the ZombieSystem object
     * @param buildingSystem the BuildingSystem object
     */
    @Override
    public void update(ResourceSystem resourceSystem, ZombieSystem zombieSystem, BuildingSystem buildingSystem) {
        super.update(resourceSystem, zombieSystem, buildingSystem);
        if (harvestRangeBox == null) {
            int padding = 60;
            harvestRangeBox = new Rectangle((int)x - padding, (int)y - padding, width + (padding*2), height + (padding*2));
        }
        
        //************* Harvest resources *************//
        long now = System.currentTimeMillis();
        if (now - lastHarvestTime > harvestCooldown) {
            boolean gathered = false;
            
            //************* Check trees in range *************//
            if (CollisionSystem.checkResourceHitCollision(harvestRangeBox, resourceSystem).equals("tree")) {
                resourceSystem.addWood(harvestAmount);
                gathered = true;
            }
               
            //************* Check stones in range *************//
            if (CollisionSystem.checkResourceHitCollision(harvestRangeBox, resourceSystem).equals("stone")) {
                resourceSystem.addStone(harvestAmount);
                gathered = true;
            }
            
            if (gathered) { // Only update time if something was gathered
                lastHarvestTime = now;
            }
        }
    }

    /**
     * Draws the Harvester
     * Precondition: N/A
     * Postcondition: The Harvester is drawn on the screen
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        //************* Draw base *************//
        if (baseSprites != null) {
            g2d.drawImage(baseSprites[level-1], (int)x, (int)y, width, height, null);
        }

        //************* Draw top*************//
        if (topSprites != null && level - 1 < topSprites.length) {
            g2d.drawImage(topSprites[level-1], (int)x, (int) (y + (height - (height * 0.75)) / 2), width, (int) (height * 0.75), null);
        }
    }

    @Override
    public void update() {}
}