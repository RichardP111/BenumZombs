/**
 * Wall.java
 * The Wall class for BenumZombs, defining wall properties and behaviors
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

public class BombTower extends Building {
    private double pulse = 0.5;
    private static final double BASE_SCALE = 0.5;
    private static final double MAX_SCALE = 0.6;

    /**
     * Constructor for BombTower
     * Precondition: N/A
     * Postcondition: BombTower object is created
     * @param x the x-coordinate of the BombTower
     * @param y the y-coordinate of the BombTower
     */
    public BombTower(double x, double y) {
        super(x, y, 35, 35, "Bomb Tower", "bombTower.png");
        this.woodCost = 10;
        this.stoneCost = 10;
        this.description = "Large area of effect damage, very slow firing tower.";
        this.isLocked = true;

        this.maxHealth = 150 + (level * 100);
        this.health = maxHealth;

        this.limits = 6;

        this.attackCooldown = 2000;
        this.range = 200 + (level * 20);
        this.damage = 60 + (level * 15);

        loadSprites("bombTower", false, true, false, "bombTower_projectile.png");

        this.upgradeGoldCosts = new int[] {100, 200, 600, 1200, 2000, 8000, 35000};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
    }

    /**
     * Creates a copy of the BombTower
     * Precondition: N/A
     * Postcondition: returns a new BombTower object at specified coordinates
     * @param x the x-coordinate of the new BombTower
     * @param y the y-coordinate of the new BombTower
     * @return a new BombTower object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new BombTower(x, y);
    }

    /**
     * Gets the description of the BombTower
     * Precondition: N/A
     * Postcondition: returns the description of the BombTower
     * @return the description of the BombTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the BombTower
     * Precondition: N/A
     * Postcondition: The BombTower state is updated
     * @param resourceSystem the ResourceSystem object
     * @param zombieSystem the ZombieSystem object
     * @param buildingSystem the BuildingSystem object
     */
    @Override
    public void update(ResourceSystem resourceSystem, ZombieSystem zombieSystem, BuildingSystem buildingSystem) {
        super.update(resourceSystem, zombieSystem, buildingSystem);
        //************* Update pulse effect *************//
        if (pulse > BASE_SCALE) {
            pulse -= 0.01; 
            if (pulse < BASE_SCALE) {
                pulse = BASE_SCALE;
            }
        }

        Zombie target = findClosestZombie(zombieSystem);
        if (target != null) {
            //************* Attack if in range *************//
            long now = System.currentTimeMillis();
            if (now - lastAttackTime > attackCooldown) {
                double dx = (target.getX() + target.getWidth()/2) - (x + width/2);
                double dy = (target.getY() + target.getHeight()/2) - (y + height/2);
                double angle = Math.atan2(dy, dx);
                
                Projectile p = new Projectile(x + width/2, y + height/2, angle, 0.5, damage, 120, projectileSprite);
                buildingSystem.addProjectile(p);
                
                pulse = MAX_SCALE;
                lastAttackTime = now;
            }
        }
    }

    /**
     * Draws the BombTower
     * Precondition: N/A
     * Postcondition: The BombTower is drawn on the screen
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
            g2d.scale(pulse, pulse);
            g2d.drawImage(topSprites[level-1], -width/2, -height/2, width, height, null);
            g2d.setTransform(old); // Restore the original transform
        }
    }

    @Override
    public void update() {}
}