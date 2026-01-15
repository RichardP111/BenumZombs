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
    protected String toolName;
    protected int level = 1;
    protected boolean isUnlocked;

    /**
     * Constructor for Tool
     * Precondition: toolName is a valid string, isUnlocked is a boolean
     * Postcondition: Tool object is created with specified name and unlock status
     * @param toolName
     * @param isUnlocked
     */
    public Tool(String toolName, boolean isUnlocked) {
        this.toolName = toolName;
        this.isUnlocked = isUnlocked;
    }

    /**
     * Upgrades the tool by increasing its level by 1
     * Precondition: None
     * Postcondition: Tool level is increased by 1
     */
    public void upgrade() {
        level++;
    }

    /**
     * Gets the level of the tool
     * Precondition: None
     * Postcondition: returns the level of the tool
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the name of the tool
     * Precondition: None
     * Postcondition: returns the name of the tool
     * @return
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * Gets whether the tool is unlocked
     * Precondition: None
     * Postcondition: returns true if the tool is unlocked, false otherwise
     * @return
     */
    public boolean getIsUnlocked() {
        return isUnlocked;
    }

    /**
     * Sets the unlock status of the tool
     * Precondition: unlocked is a boolean
     * Postcondition: sets the unlock status of the tool
     * @param unlocked
     */
    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    public boolean isConsumable() {
        return false;
    }

    public void onGet(Player player) {}

    public abstract int getUpgradeCost();
    
    public abstract void draw(Graphics2D g2d, int x, int y, double angle, double scale);

    public abstract void use();
}
