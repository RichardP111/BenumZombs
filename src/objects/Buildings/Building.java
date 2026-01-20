/**
 * Building.java
 * The Building class for BenumZombs, defining building properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import helpers.HealthManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import objects.GameObject;
import objects.Zombie;
import systems.BuildingSystem;
import systems.ResourceSystem;
import systems.ZombieSystem;

public abstract class Building extends GameObject {
    protected String name;
    protected String type;
    protected String description;
    protected int level = 1;
    protected int maxLevel = 8;

    //************* Start Settings *************//
    protected int woodCost;
    protected int stoneCost;
    protected boolean isLocked = true;
    protected int limits; // Limit for number of buildings of a type

    //************* Upgrade Costs *************//
    protected int[] upgradeWoodCosts;
    protected int[] upgradeStoneCosts;
    protected int[] upgradeGoldCosts;

    //************* Tower Health *************//
    protected int maxHealth;
    protected int health;

    protected long lastDamageTime = 0;
    protected static final long REGEN_DELAY = 3000;
    protected int regenCounter = 0;

    //************* Attack Properties *************//
    protected long lastAttackTime = 0;
    protected long attackCooldown = 1000;
    protected int damage = 20;
    protected double range = 300;
    protected double headRotation = 0.0;

    //************* Tower Sprites *************//
    protected BufferedImage[] baseSprites;
    protected BufferedImage[] middleSprites;
    protected BufferedImage[] topSprites;
    protected BufferedImage[] otherSprites;
    protected BufferedImage projectileSprite;

    protected Image icon;
    protected float animation = 0.0f;

    /**
     * Constructor for Building
     * Precondition: width and height are positive integers
     * Postcondition: Building is created
     * @param x the x-coordinate of the building
     * @param y the y-coordinate of the building
     * @param width the width of the building
     * @param height the height of the building
     * @param name the name of the building
     * @param iconName the filename of the building's icon image
     */
    public Building(double x, double y, int width, int height, String name, String iconName) {
        super(x, y, width, height, null, null); 
        this.name = name;
        this.health = 100;
        this.maxHealth = 100;

        upgradeWoodCosts = new int[maxLevel];
        upgradeStoneCosts = new int[maxLevel];
        upgradeGoldCosts = new int[maxLevel];

        baseSprites = new BufferedImage[maxLevel];
        middleSprites = new BufferedImage[maxLevel];
        topSprites = new BufferedImage[maxLevel];
        otherSprites = new BufferedImage[maxLevel];
        
        //************* Load Building Icon *************//
        try {
            if (iconName != null) {
                this.icon = ImageIO.read(getClass().getResource("/assets/images/buildings/toolbar/" + iconName));
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Building.java - Error loading toolbar icon: " + iconName);
        }
    }

    /**
     * Loads the sprites for the Building
     * Precondition: N/A
     * Postcondition: sprites are loaded for the Building at all levels
     * @param name the base name of the building sprites
     * @param hasMiddle whether the building has middle sprites
     * @param hasHead whether the building has head/top sprites
     * @param hasClaw whether the building has claw/other sprites
     * @param projectileName the filename of the projectile sprite, or null if building doesn't shoot
     */
    protected void loadSprites(String name, boolean hasMiddle, boolean hasHead, boolean hasClaw, String projectileName) {
        try {
            for (int i = 0; i < maxLevel; i++) {
                //************* Load Base Sprite *************//
                int lvl = i + 1;
                String basePath = "/assets/images/buildings/" + name + "/base_" + lvl + ".png";
                baseSprites[i] = ImageIO.read(getClass().getResource(basePath));

                //************* Load Middle Sprites *************//
                if (hasMiddle) {
                    String middlePath = "/assets/images/buildings/" + name + "/middle_" + lvl + ".png";
                    middleSprites[i] = ImageIO.read(getClass().getResource(middlePath));
                }

                //************* Load Top Sprites *************//
                if (hasHead) {
                    String topPath = "/assets/images/buildings/" + name + "/head_" + lvl + ".png";
                    topSprites[i] = ImageIO.read(getClass().getResource(topPath));
                }
                
                //************* Load Other Sprites *************//
                if (hasClaw) {
                    String clawPath = "/assets/images/buildings/" + name + "/claw_" + lvl + ".png";
                    otherSprites[i] = ImageIO.read(getClass().getResource(clawPath));
                }

                if (projectileName != null) {
                    String projectilePath = "/assets/images/buildings/" + name + "/" + projectileName;
                    projectileSprite = ImageIO.read(getClass().getResource(projectilePath));
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Building.java - Error loading sprites for " + name + ": " + e.getMessage());
        }
    }

    /**
     * Gets the icon of the Building
     * Precondition: N/A
     * Postcondition: returns the icon of the Building
     * @return the icon of the Building
     */
    public Image getIcon() { 
        return icon; 
    }

    /**
     * Gets the projectile image of the Building
     * Precondition: N/A
     * Postcondition: returns the projectile image of the Building
     * @return the projectile image of the Building
     */
    public BufferedImage getProjectileImage() {
        return projectileSprite;
    }

    /**
     * Gets the name of the Building
     * Precondition: N/A
     * Postcondition: returns the name of the Building
     * @return the name of the Building
     */
    public String getName() { 
        return name; 
    }

    /**
     * Gets the level of the Building
     * Precondition: N/A
     * Postcondition: returns the level of the Building
     * @return the level of the Building
     */
    public int getLevel() { 
        return level; 
    }

    /**
     * Gets the maximum level of the Building
     * Precondition: N/A
     * Postcondition: returns the maximum level of the Building
     * @return the maximum level of the Building
     */
    public int getMaxLevel() { 
        return maxLevel; 
    }

    /**
     * Gets the wood cost of the Building
     * Precondition: N/A
     * Postcondition: returns the wood cost of the Building
     * @return the wood cost of the Building
     */
    public int getWoodCost() { 
        return woodCost; 
    }

    /**
     * Gets the stone cost of the Building
     * Precondition: N/A
     * Postcondition: returns the stone cost of the Building
     * @return the stone cost of the Building
     */
    public int getStoneCost() { 
        return stoneCost; 
    }

    /**
     * Checks if the Building is locked
     * Precondition: N/A
     * Postcondition: returns true if the Building is locked, false otherwise
     * @return true if the Building is locked, false otherwise
     */
    public boolean isLocked() {
        return isLocked; 
    }

    /**
     * Checks if the Building is the Gold Stash
     * Precondition: N/A
     * Postcondition: returns true if the Building is an Gold Stash, false otherwise
     * @return true if the Building is an Gold Stash, false otherwise
     */
    public boolean isUnlocker() {
        return false;
    }

    /**
     * Sets the locked status of the Building
     * Precondition: N/A
     * Postcondition: sets the locked status of the Building
     * @param locked the locked status to set
     */
    public void setLocked(boolean locked) { 
        this.isLocked = locked; 
    }

    /**
     * Gets the limit of the Building type
     * Precondition: N/A
     * Postcondition: returns the limit of the Building type
     * @return the limit of the Building type
     */
    public int getLimit() {
        return limits;
    }

    /**
     * Sets the width of the Building
     * Precondition: w is a positive integer
     * Postcondition: sets the width of the Building
     * @param w the width to set
     */
    @Override
    public void setWidth(int w) {
        this.width = w;
    }
    
    /**
     * Sets the height of the Building
     * Precondition: h is a positive integer
     * Postcondition: sets the height of the Building
     * @param h the height to set
     */
    @Override
    public void setHeight(int h) {
        this.height = h;
    }

    /**
     * Gets the current health of the Building
     * Precondition: N/A
     * Postcondition: returns the current health of the Building
     * @return the current health of the Building
     */
    public int getHealth() { 
        return health; 
    }

    /**
     * Damages the Building by a specified amount
     * Precondition: amount is a non-negative integer
     * Postcondition: reduces the health of the Building by the specified amount
     * @param amount the amount of damage to inflict
     */
    public int getMaxHealth() { 
        return maxHealth; 
    }

    /**
     * Damages the Building by a specified amount
     * Precondition: amount is a non-negative integer
     * Postcondition: reduces the health of the Building by the specified amount
     * @param amount the amount of damage to inflict
     */
    public void takeDamage(int amount) {
        this.health -= amount;
        this.lastDamageTime = System.currentTimeMillis();
    }

    /**
     * Checks if the Building is destroyed
     * Precondition: N/A
     * Postcondition: returns true if the Building is destroyed, false otherwise
     * @return true if the Building is destroyed, false otherwise
     */
    public boolean isDestroyed() {
        return this.health <= 0;
    }

    /**
     * Upgrades the Building if not at max level
     * Precondition: N/A
     * Postcondition: upgrades the Building level and heals building
     */
    public void upgrade() {
        if (level < maxLevel) {
            level++;
            this.maxHealth = (int)(this.maxHealth * 1.2);
            this.health = this.maxHealth; 
        }
    }

    /**
     * Checks if the Building can be upgraded based on active stash level
     * Precondition: activeStashLevel is a positive integer
     * Postcondition: returns true if the Building can be upgraded, false otherwise
     * @param activeStashLevel the level of the active Gold Stash
     * @return true if the Building can be upgraded, false otherwise
     */
    public boolean canUpgrade(int activeStashLevel) {
        if (level >= maxLevel) {
            return false;
        }
        
        if (this.name.equals("Gold Stash")) {
            return true;
        }
        
        return level < activeStashLevel;
    }

    /**
     * Gets the wood cost to upgrade the Building
     * Precondition: N/A
     * Postcondition: returns the wood cost to upgrade the Building
     * @return the wood cost to upgrade the Building
     */
    public int getUpgradeWoodCost() {
        if (level >= maxLevel) {
            return 0;
        }
        int index = level - 1;
        if (index < upgradeWoodCosts.length) {
            return upgradeWoodCosts[index];
        }
        return woodCost * (level + 1);
    }

    /**
     * Gets the stone cost to upgrade the Building
     * Precondition: N/A
     * Postcondition: returns the stone cost to upgrade the Building
     * @return the stone cost to upgrade the Building
     */
    public int getUpgradeStoneCost() {
        if (level >= maxLevel) {
            return 0;
        }
        int index = level - 1;
        if (index < upgradeStoneCosts.length) {
            return upgradeStoneCosts[index];
        }
        return stoneCost * (level + 1);
    }

    /**
     * Gets the gold cost to upgrade the Building
     * Precondition: N/A
     * Postcondition: returns the gold cost to upgrade the Building
     * @return the gold cost to upgrade the Building
     */
    public int getUpgradeGoldCost() {
        if (level >= maxLevel) {
            return 0;
        }
        int index = level - 1;
        if (index < upgradeGoldCosts.length) {
            return upgradeGoldCosts[index];
        }
        return 0; 
    }

    /**
     * Checks if the Building can be sold
     * Precondition: N/A
     * Postcondition: returns true if the Building can be sold, false otherwise
     * @return true if the Building can be sold, false otherwise
     */
    public boolean canBeSold() {
        return true; 
    }

    /**
     * Gets the wood sell value of the Building (half of total cost)
     * Precondition: N/A
     * Postcondition: returns the sell value of the Building
     * @return the sell value of the Building
     */
    public int getWoodSellValue() {
        if (getUpgradeWoodCost() == 0) {
            return upgradeWoodCosts[6] / 2;
        } else if (getLevel() == 1) {
            return woodCost / 2;
        } else {
            return getUpgradeWoodCost() / 2;
        }
    }

    /**
     * Gets the stone sell value of the Building (half of total cost)
     * Precondition: N/A
     * Postcondition: returns the sell value of the Building
     * @return the sell value of the Building
     */
    public int getStoneSellValue() {
        if (getUpgradeStoneCost() == 0) {
            return upgradeStoneCosts[6] / 2;
        } else if (getLevel() == 1) {
            return stoneCost / 2;
        } else {
            return getUpgradeStoneCost() / 2;
        }
    }

    /**
     * Draws the health bar of the Building if damaged
     * Precondition: g2d is not null
     * Postcondition: draws the health bar of the Building
     * @param g2d the Graphics2D object to draw on
     */
    public void drawHealthBar(Graphics2D g2d) {
        if (health < maxHealth) {
            HealthManager.drawStatusBar(g2d, health, maxHealth, (int)x + 5, (int)y + height - 5, width - 10, 5, new Color(99, 183, 32), false);
        }
    }

    /**
     * Finds the closest zombie within range
     * Precondition: zombieSystem is not null
     * Postcondition: returns the closest zombie within range, or null if none found
     * @param zombieSystem the ZombieSystem to search for zombies
     * @return the closest zombie within range, or null if none found
     */
    protected Zombie findClosestZombie(ZombieSystem zombieSystem) {
        Zombie closest = null;
        double minDist = range;
        
        for (int i = 0; i < zombieSystem.getZombies().size(); i++) {
            Zombie zombie = zombieSystem.getZombies().get(i);
            double dist = Math.hypot(zombie.getX() - x, zombie.getY() - y);
            if (dist < minDist) {
                minDist = dist;
                closest = zombie;
            }
        }
        return closest;
    }

    /**
     * Updates the Building state
     * Precondition: N/A
     * Postcondition: the Building state is updated
     */
    public void update(ResourceSystem resourceSystem, ZombieSystem zombieSystem, BuildingSystem buildingSystem) {
        //************* Update Animation *************//
        animation += 0.01f;
        if (animation > 2) {
            animation = 0;
        }

        //************* Regenerate Health *************//
        if (health < maxHealth && health > 0) {
            long now = System.currentTimeMillis();
            if (now - lastDamageTime > REGEN_DELAY) {
                regenCounter++;
                if (regenCounter > 5) {
                    health += 2;
                    if (health > maxHealth) {
                        health = maxHealth;
                    }
                    regenCounter = 0;
                }
            }
        }
    }

    /**
     * Gets the hitbox of the Building
     * Precondition: N/A
     * Postcondition: returns the hitbox of the Building
     * @return the hitbox of the Building
     */
    public Rectangle getHitbox() {
        return new Rectangle((int)x, (int)y, width, height); 
    }
    
    @Override
    public abstract void draw(Graphics2D g2d);
    public abstract String getDescription();
    public abstract Building createCopy(double x, double y);
}
