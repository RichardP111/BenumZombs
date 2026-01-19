/**
 * CannonTower.java
 * The CannonTower class for BenumZombs, defining cannon tower properties and behaviors
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

public class CannonTower extends Building {

    /**
     * Constructor for CannonTower
     * Precondition: N/A
     * Postcondition: CannonTower object is created
     * @param x the x-coordinate of the CannonTower
     * @param y the y-coordinate of the CannonTower
     */
    public CannonTower(double x, double y) {
        super(x, y, 35, 35, "Cannon Tower", "cannonTower.png");
        this.woodCost = 15;
        this.stoneCost = 15;
        this.description = "Area of effect damage, slow firing tower.";
        this.isLocked = true;
        
        this.maxHealth = 150 + (level * 100);
        this.health = maxHealth;

        this.limits = 6;

        this.attackCooldown = 1500;
        this.range = 300 + (level * 20);
        this.damage = 80 + (level * 15);

        loadSprites("cannonTower", false, true, false, "cannonTower_projectile.png");

        this.upgradeGoldCosts = new int[] {100, 200, 600, 1200, 2000, 8000, 35000};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
    }

    /**
     * Creates a copy of the CannonTower
     * Precondition: N/A
     * Postcondition: returns a new CannonTower object at specified coordinates
     * @param x the x-coordinate of the new CannonTower
     * @param y the y-coordinate of the new CannonTower
     * @return a new CannonTower object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new CannonTower(x, y);
    }

    /**
     * Gets the description of the CannonTower
     * Precondition: N/A
     * Postcondition: returns the description of the CannonTower
     * @return the description of the CannonTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the CannonTower
     * Precondition: N/A
     * Postcondition: The CannonTower state is updated
     */
    @Override
    public void update(ResourceSystem resourceSystem, ZombieSystem zombieSystem, BuildingSystem buildingSystem) {
        super.update(resourceSystem, zombieSystem, buildingSystem);
        Zombie target = findClosestZombie(zombieSystem);
        if (target != null) {
            //************* Aim at target *************//
            double dx = (target.getX() + target.getWidth()/2) - (x + width/2);
            double dy = (target.getY() + target.getHeight()/2) - (y + height/2);
            headRotation = Math.atan2(dy, dx);
            
            //************* Attack if in range *************//
            long now = System.currentTimeMillis();
            if (now - lastAttackTime > attackCooldown) {
                Projectile p = new Projectile(x + width/2, y + height/2, headRotation, 0.8, damage, 0, projectileSprite);
                buildingSystem.addProjectile(p);
                lastAttackTime = now;
            }
        }
    }

    /**
     * Draws the CannonTower
     * Precondition: N/A
     * Postcondition: The CannonTower is drawn on the screen
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