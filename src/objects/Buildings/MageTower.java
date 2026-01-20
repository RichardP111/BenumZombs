/**
 * GoldStash.java
 * The GoldStash class for BenumZombs, defining gold stash properties and behaviors
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

public class MageTower extends Building {
    private double pulse = 0.5;
    private static final double BASE_SCALE = 0.5;
    private static final double MAX_SCALE = 0.6;

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
        
        this.maxHealth = 150 + (level * 100);
        this.health = maxHealth;

        this.limits = 6;

        this.attackCooldown = 1200;
        this.range = 450 + (level * 20);
        this.damage = 35 + (level * 15);

        loadSprites("mageTower", false, true, false, "mageTower_projectile.png");

        this.upgradeGoldCosts = new int[] {100, 200, 600, 1200, 2000, 8000, 35000};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
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

        //************* Attack Logic *************//
        Zombie target = findClosestZombie(zombieSystem);
        if (target != null) {
            long now = System.currentTimeMillis();
            if (now - lastAttackTime > attackCooldown) {
                //************* Fire projectiles *************//
                double dx = (target.getX() + target.getWidth()/2) - (x + width/2);
                double dy = (target.getY() + target.getHeight()/2) - (y + height/2);
                double angle = Math.atan2(dy, dx);
                
                buildingSystem.addProjectile(new Projectile(x + width/2, y + height/2, angle, 11, damage, 20, projectileSprite));
                buildingSystem.addProjectile(new Projectile(x + width/2, y + height/2, angle - 0.2, 11, damage, 20, projectileSprite));
                buildingSystem.addProjectile(new Projectile(x + width/2, y + height/2, angle + 0.2, 11, damage, 20, projectileSprite));
                
                pulse = MAX_SCALE;
                lastAttackTime = now;
            }
        }
    }

    /**
     * Draws the MageTower
     * Precondition: N/A
     * Postcondition: The MageTower is drawn on the screen
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (baseSprites != null) {
            //************* Draw base *************//
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