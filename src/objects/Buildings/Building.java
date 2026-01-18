/**
 * Building.java
 * The Building class for BenumZombs, defining building properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import objects.GameObject;

public abstract class Building extends GameObject {
    protected String name;
    protected String description;
    protected int level = 1;
    protected int maxLevel = 7;

    protected int woodCost;
    protected int stoneCost;
    protected boolean isLocked = true;
    protected int limits; // Limit for number of buildings of a type

    protected int[] upgradeWoodCosts;
    protected int[] upgradeStoneCosts;
    protected int[] upgradeGoldCosts;

    protected int maxHealth;
    protected int health;
    protected Image icon; 

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
        super(x, y, width, height, null); 
        this.name = name;
        this.health = 100;
        this.maxHealth = 100;

        upgradeWoodCosts = new int[maxLevel];
        upgradeStoneCosts = new int[maxLevel];
        upgradeGoldCosts = new int[maxLevel];
        
        //************* Load Building Icon *************//
        try {
            if (iconName != null) {
                this.icon = ImageIO.read(getClass().getResource("/assets/images/buildings/toolbar/" + iconName));
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading building icon: " + iconName);
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
    public void setWidth(int w) {
        this.width = w;
    }
    
    /**
     * Sets the height of the Building
     * Precondition: h is a positive integer
     * Postcondition: sets the height of the Building
     * @param h the height to set
     */
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
        if (level >= maxLevel){
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
        if (level >= maxLevel){
            return 0;
        }
        int index = level - 1;
        if (index < upgradeWoodCosts.length){
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
        if (level >= maxLevel){
            return 0;
        }
        int index = level - 1;
        if (index < upgradeStoneCosts.length){
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
        if (level >= maxLevel){
            return 0;
        }
        int index = level - 1;
        if (index < upgradeGoldCosts.length){
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
     * Draws the Building
     * Precondition: g2d is not null
     * Postcondition: the Building is drawn on the Graphics2D object
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (icon != null) {
            int drawOffset = 20;

            int drawW = width + drawOffset;
            int drawH = height + drawOffset;
            
            int drawX = (int)x - (drawOffset / 2);
            int drawY = (int)y - (drawOffset / 2);

            g2d.drawImage(icon, drawX, drawY, drawW, drawH, null);
        }
    }

    public abstract String getDescription();
    public abstract Building createCopy(double x, double y);
}
