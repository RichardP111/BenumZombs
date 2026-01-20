/**
 * ArrowTower.java
 * The ArrowTower class for BenumZombs, defining arrow tower properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import objects.Projectile;
import objects.Zombie;
import systems.BuildingSystem;
import systems.ResourceSystem;
import systems.ZombieSystem;

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

        this.maxHealth = 150 + (level * 100);
        this.health = maxHealth;

        this.limits = 6;

        this.attackCooldown = 800;
        this.range = 400 + (level * 20);
        this.damage = 30 + (level * 15);

        loadSprites("arrowTower", false, true, false, "arrowTower_projectile.png");

        this.upgradeGoldCosts = new int[] {100, 200, 600, 1200, 2000, 8000, 35000};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
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
     * Updates the ArrowTower state
     * Precondition: N/A
     * Postcondition: The ArrowTower state is updated
     * @param resourceSystem the ResourceSystem object
     * @param zombieSystem the ZombieSystem object
     * @param buildingSystem the BuildingSystem object
     */
    @Override
    public void update(ResourceSystem resourceSystem, ZombieSystem zombieSystem, BuildingSystem buildingSystem) {
        super.update(resourceSystem, zombieSystem, buildingSystem);
        Zombie target = findClosestZombie(zombieSystem);
        if (target != null) {
            //************* Calculate angle to face the target *************//
            double dx = (target.getX() + target.getWidth()/2) - (x + width/2);
            double dy = (target.getY() + target.getHeight()/2) - (y + height/2);
            headRotation = Math.atan2(dy, dx);
            
            //****************** Attack if in range ****************//
            long now = System.currentTimeMillis();
            if (now - lastAttackTime > attackCooldown) {
                Projectile p = new Projectile(x + width/2, y + height/2, headRotation, 10, damage, 0, projectileSprite);
                buildingSystem.addProjectile(p);
                lastAttackTime = now;
            }
        }
    }

    /**
     * Draws the ArrowTower
     * Precondition: N/A
     * Postcondition: The ArrowTower is drawn on the screen
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        //************* Draw base *************//
        if (baseSprites != null) {
            g2d.drawImage(baseSprites[level-1], (int)x, (int)y, width, height, null);
        }
        
        //************* Draw top *************//
        if (topSprites != null) {
            AffineTransform old = g2d.getTransform(); // Save the current transform
            g2d.translate(x + width/2, y + height/2);
            g2d.rotate(headRotation);
            g2d.drawImage(topSprites[level-1], -width/2, -height/2, width, height, null);
            g2d.setTransform(old); // Restore the original transform
        }
    }

    @Override
    public void update() {}
}