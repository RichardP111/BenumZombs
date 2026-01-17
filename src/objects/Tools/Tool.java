/**
 * Tool.java
 * The Tool class for BenumZombs, defining general tool logic and shared properties
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-10
 */

package objects.Tools;

import java.awt.Graphics2D;
import objects.Player;

public abstract class Tool {
    protected String description;
    protected String toolName;
    protected int level = 1;
    protected boolean isUnlocked;

    /**
     * Constructor for Tool
     * Precondition: toolName is a valid string, isUnlocked is a boolean
     * Postcondition: Tool object is created with specified name and unlock status
     * @param toolName the name of the tool
     * @param isUnlocked whether the tool is unlocked
     */
    public Tool(String toolName, boolean isUnlocked) {
        this.toolName = toolName;
        this.isUnlocked = isUnlocked;
    }

    /**
     * Upgrades the tool by increasing its level by 1
     * Precondition: N/A
     * Postcondition: Tool level is increased by 1
     */
    public void upgrade() {
        level++;
    }

    /**
     * Gets the level of the tool
     * Precondition: N/A
     * Postcondition: returns the level of the tool
     * @return the level of the tool
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the tool
     * Precondition: level is a positive integer
     * Postcondition: sets the level of the tool
     * @param level the new level of the tool
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the name of the tool
     * Precondition: N/A
     * Postcondition: returns the name of the tool
     * @return the name of the tool
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * Gets whether the tool is unlocked
     * Precondition: N/A
     * Postcondition: returns true if the tool is unlocked, false otherwise
     * @return whether the tool is unlocked
     */
    public boolean getIsUnlocked() {
        return isUnlocked;
    }

    /**
     * Sets the unlock status of the tool
     * Precondition: unlocked is a boolean
     * Postcondition: sets the unlock status of the tool
     * @param unlocked the new unlock status of the tool
     */
    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    /**
     * Checks if the tool is consumable
     * Precondition: N/A
     * Postcondition: returns false for non-consumable tools
     * @return whether the tool is consumable
     */
    public boolean isConsumable() {
        return false;
    }

    /**
     * Checks if the tool can harvest resources
     * Precondition: N/A
     * Postcondition: returns false for non-harvesting tools
     * @return whether the tool can harvest resources
     */
    public boolean canHarvest() {
        return false; 
    }

    /**
     * Checks if the tool is ranged
     * Precondition: N/A
     * Postcondition: returns false for melee tools
     * @return whether the tool is ranged
     */
    public boolean isRanged() {
        return false;
    }

    /**
     * Gets the attack speed of the tool
     * Precondition: N/A
     * Postcondition: returns the attack speed of the tool
     * @return the attack speed of the tool
     */
    public double getAttackSpeed() {
        return 1.0;
    }

    /**
     * Gets the damage of the tool
     * Precondition: N/A
     * Postcondition: returns the damage of the tool
     * @return the damage of the tool
     */
    public double getDamage() {
        return 0;
    }

    public void onGet(Player player) {}

    public abstract int getUpgradeCost();

    public abstract String getDescription();
    
    public abstract void draw(Graphics2D g2d, int x, int y, double angle, double scale);
}
