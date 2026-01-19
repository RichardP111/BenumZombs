/**
 * GoldMine.java
 * The GoldMine class for BenumZombs, defining gold mine properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import systems.ResourceSystem;
import systems.ZombieSystem;
import systems.BuildingSystem;

public class GoldMine extends Building {
    private double spinAngle = 0.0;

    private final int[] goldProduction = {4, 6, 7, 10, 12, 15, 25, 53};

    private int frames = 0;
    private static final int GENERATION_INTERVAL = 60;

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

        this.maxHealth = 150;
        this.health = 150;

        this.limits = 8;

        loadSprites("goldMine", false, true, false, null);

        this.upgradeGoldCosts = new int[] {200, 300, 600, 800, 2000, 8000, 30000};
        this.upgradeWoodCosts = new int[] {15, 25, 35, 45, 55, 700, 1600};
        this.upgradeStoneCosts = new int[] {15, 25, 35, 45, 55, 700, 1600};
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
    public void update(ResourceSystem resourceSystem, ZombieSystem zombieSystem, BuildingSystem buildingSystem) {
        super.update(resourceSystem, zombieSystem, buildingSystem);
        spinAngle += 0.02; // Rotate the top part

        //************* Generate gold over time *************//
        frames++;
        if (frames >= GENERATION_INTERVAL) {
            frames = 0;
            
            int amount;
            if (level <= goldProduction.length) {
                amount = goldProduction[level - 1];
            } else {
                amount = goldProduction[goldProduction.length - 1]; 
            }
            
            if (resourceSystem != null) {
                resourceSystem.addGold(amount);
            }
        }
    }

    /**
     * Draws the GoldMine
     * Precondition: N/A
     * Postcondition: The GoldMine is drawn on the screen
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        //************* Draw base *************//
        if (baseSprites != null && level - 1 < baseSprites.length) {
            g2d.drawImage(baseSprites[level-1], (int)x, (int)y, width, height, null);
        }
        
        //************* Draw top *************//
        if (topSprites != null && level - 1 < topSprites.length) {
            AffineTransform old = g2d.getTransform(); // Save the current transform
            g2d.translate(x + width / 2, y + height / 2);
            g2d.rotate(spinAngle);
            g2d.drawImage(topSprites[level-1], -width / 2, -height / 2, width, height, null);
            g2d.setTransform(old); // Restore the original transform
        }
    }

    @Override
    public void update() {}
}