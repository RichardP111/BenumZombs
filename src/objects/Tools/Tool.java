/**
 * Tool.java
 * The Tool class for BenumZombs, defining general tool logic and shared properties
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-10
 */

package objects.Tools;

import java.awt.Graphics2D;
import java.awt.Rectangle;
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

    /**
     * Gets the hitbox of the tool based on player position and angle
     * Precondition: playerX and playerY are the player's coordinates, angle is the direction the player is facing
     * Postcondition: returns the hitbox rectangle of the tool
     * @param playerX the x-coordinate of the player
     * @param playerY the y-coordinate of the player
     * @param angle the angle the player is facing
     * @return the hitbox rectangle of the tool
     */
    public Rectangle getHitbox(double playerX, double playerY, double angle) {
        double cx = playerX + 25;
        double cy = playerY + 25;
        
        double reach = 35; 
        
        int hitX = (int) (cx + Math.cos(angle) * reach);
        int hitY = (int) (cy + Math.sin(angle) * reach);
        
        return new Rectangle(hitX - 30, hitY - 30, 60, 60);
    }

    /**
     * Gets the harvest power of the tool
     * Precondition: N/A
     * Postcondition: returns 0 for non-harvesting tools
     * @return the harvest power of the tool
     */
    public double getHarvestPower() {
        return 0;
    }

    /**
     * Called when the player obtains the tool
     * Precondition: N/A
     * Postcondition: Tool-specific on-get behavior is executed
     * @param player the Player object obtaining the tool
     */
    public void onGet(Player player) {}

    /**
     * Gets the upgrade cost of the tool
     * Precondition: N/A
     * Postcondition: returns the cost to upgrade the tool
     * @return the upgrade cost of the tool
     */
    public abstract int getUpgradeCost();

    /**
     * Gets the description of the tool
     * Precondition: N/A
     * Postcondition: returns the description of the tool
     * @return the description of the tool
     */
    public abstract String getDescription();
    
    /**
     * Draws the tool on the provided Graphics2D context
     * Precondition: g2d is a valid Graphics2D object, x and y are the position to draw at, angle is the rotation angle, scale is the drawing scale
     * Postcondition: The tool is drawn at the specified position, angle, and scale
     * @param g2d the Graphics2D object to draw on
     * @param x the x-coordinate to draw at
     * @param y the y-coordinate to draw at
     * @param angle the rotation angle
     * @param scale the drawing scale
     */
    public abstract void draw(Graphics2D g2d, int x, int y, double angle, double scale);
}
