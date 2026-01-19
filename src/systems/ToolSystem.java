/**
 * ToolSystem.java
 * The ToolSystem class for BenumZombs, managing tools and their usage
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-10
 */

package systems;

import java.util.ArrayList;

import objects.Tools.*;

public class ToolSystem {
    private ArrayList<Tool> inventory;
    private int activeSlot = 0;

    /**
     * Constructor for Tool System
     * Precondition: N/A
     * Postcondition: Tools are initialized in their respective slots
     */
    public ToolSystem() {
        inventory = new ArrayList<>();
        inventory.add(new Pickaxe(true));
        inventory.add(new Spear(false));
        inventory.add(new Bow(false));
        inventory.add(new HealthPotion(false));
        inventory.add(new Armor(false));
    }

    /**
     * Sets the active tool slot
     * Precondition: slotIndex is between 0 and 4
     * Postcondition: activeSlot is updated to slotIndex if the tool is unlocked
     * @param slotIndex the index of the slot to set as active
     */
    public void setActiveSlot(int slotIndex) {
        if (slotIndex >= 0 && slotIndex < inventory.size()) {
            if (inventory.get(slotIndex).getIsUnlocked()) {
                activeSlot = slotIndex;
            }
        }
    }

    /**
     * Gets the currently active slot index
     * Precondition: N/A
     * Postcondition: returns the active slot index
     * @return the active slot index
     */
    public int getActiveSlotIndex() {
        return activeSlot;
    }

    /**
     * Gets the currently active tool
     * Precondition: N/A
     * Postcondition: returns the active Tool
     * @return the active Tool
     */
    public Tool getActiveTool() {
        return inventory.get(activeSlot);
    }

    /**
     * Gets the tool in the specified slot
     * Precondition: slotIndex is between 0 and 4
     * Postcondition: returns the Tool in the specified slot, or null if invalid index
     * @param slotIndex the index of the slot to get the tool from
     * @return the Tool in the specified slot, or null if invalid index
     */
    public Tool getToolInSlot(int slotIndex) {
        if (slotIndex >= 0 && slotIndex < inventory.size()) {
            return inventory.get(slotIndex);
        }
        return null;
    }

    /**
     * Resets the ToolSystem to its initial state
     * Precondition: N/A
     * Postcondition: Tool inventory is reset and active slot is set to 0
     */
    public void reset() {
        inventory.clear();
        inventory.add(new Pickaxe(true));
        inventory.add(new Spear(false));
        inventory.add(new Bow(false));
        inventory.add(new HealthPotion(false));
        inventory.add(new Armor(false));
        activeSlot = 0;
    }
}
