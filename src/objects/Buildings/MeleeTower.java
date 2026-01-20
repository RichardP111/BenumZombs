/**
 * MeleeTower.java
 * The MeleeTower class for BenumZombs, defining melee tower properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import objects.Zombie;
import systems.BuildingSystem;
import systems.ResourceSystem;
import systems.ZombieSystem;

public class MeleeTower extends Building {
    private double animationTimer = 0;
    private boolean isAttacking = false;

    /**
     * Constructor for MeleeTower
     * Precondition: N/A
     * Postcondition: MeleeTower object is created
     * @param x the x-coordinate of the MeleeTower
     * @param y the y-coordinate of the MeleeTower
     */
    public MeleeTower(double x, double y) {
        super(x, y, 35, 35, "Melee Tower", "meleeTower.png");
        this.woodCost = 10;
        this.stoneCost = 10;
        this.description = "High damage, single target, close-range directional tower.";
        this.isLocked = true;

        this.maxHealth = 200 + (level * 100);
        this.health = maxHealth;

        this.limits = 6;

        this.attackCooldown = 500;
        this.range = 100 + (level * 20);
        this.damage = 25 + (level * 15);

        loadSprites("meleeTower", true, true, false, null);

        this.upgradeGoldCosts = new int[] {100, 200, 600, 1200, 2000, 8000, 35000};
        this.upgradeWoodCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
        this.upgradeStoneCosts = new int[] {25, 30, 40, 50, 70, 300, 800};
    }

    /**
     * Creates a copy of the MeleeTower
     * Precondition: N/A
     * Postcondition: returns a new MeleeTower object at specified coordinates
     * @param x the x-coordinate of the new MeleeTower
     * @param y the y-coordinate of the new MeleeTower
     * @return a new MeleeTower object
     */
    @Override
    public Building createCopy(double x, double y) {
        return new MeleeTower(x, y);
    }

    /**
     * Gets the description of the MeleeTower
     * Precondition: N/A
     * Postcondition: returns the description of the MeleeTower
     * @return the description of the MeleeTower
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the MeleeTower
     * Precondition: N/A
     * Postcondition: The MeleeTower state is updated
     * @param resourceSystem the ResourceSystem object
     * @param zombieSystem the ZombieSystem object
     * @param buildingSystem the BuildingSystem object
     */
    @Override
    public void update(ResourceSystem resourceSystem, ZombieSystem zombieSystem, BuildingSystem buildingSystem) {
        super.update(resourceSystem, zombieSystem, buildingSystem);

        Zombie zombie = findClosestZombie(zombieSystem);
        isAttacking = false;

        if (zombie != null) {
            //*************** Calculate angle to face the zombie *************//
            double dx = (zombie.getX() + zombie.getWidth()/2) - (x + width/2);
            double dy = (zombie.getY() + zombie.getHeight()/2) - (y + height/2);
            headRotation = Math.atan2(dy, dx);
            
            double dist = Math.hypot(dx, dy);
            
            //**************** Attack if in range ****************//
            if (dist <= range) {
                isAttacking = true;
                long now = System.currentTimeMillis();
                if (now - lastAttackTime > attackCooldown) {
                    zombie.takeDamage(damage, false);
                    lastAttackTime = now;
                }
            }
        }
        
        //**************** Update animation timer ****************//
        if (isAttacking) {
            animationTimer += 0.1;
        } else {
            animationTimer = 0;
        }
    }

    /**
     * Draws the MeleeTower
     * Precondition: N/A
     * Postcondition: The MeleeTower is drawn on the screen
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        //************* Draw base *************//
        if (baseSprites != null && level - 1 < baseSprites.length) {
            g2d.drawImage(baseSprites[level-1], (int)x, (int)y, width, height, null);
        }

        AffineTransform old = g2d.getTransform(); // Save the current transform
        g2d.translate(x + width / 2, y + height / 2);
        g2d.rotate(headRotation); 
        g2d.scale(0.7, 0.7);

        //************* Draw middle with attack animation *************//
        if (middleSprites != null && level - 1 < middleSprites.length) {
            double punchAmount = 0;
            if (isAttacking) {
                punchAmount = 20 * Math.abs(Math.sin(animationTimer * 5));
            }
            
            int punchX = (int)(-width / 4 + punchAmount);
            int punchY = -height / 2;
            
            g2d.drawImage(middleSprites[level-1], punchX, punchY, width, height, null);
        }
        
        //************* Draw top *************//
        if (topSprites != null && level - 1 < topSprites.length) {
            g2d.drawImage(topSprites[level-1], -width / 2, -height / 2, width, height, null);
        }

        g2d.setTransform(old); // Restore the original transform
    }

    @Override
    public void update() {}
}