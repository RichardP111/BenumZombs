/**
 * ToolSystem.java
 * The ToolSystem class for BenumZombs, managing tools and their usage
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-10
 */

package systems;

import objects.Tools.*;

public class ToolSystem {
    private final Tool[] slots = new Tool[5];
    private int activeSlot = 0;

    /**
     * Constructor for Tool System
     * Precondition: N/A
     * Postcondition: Tools are initialized in their respective slots
     */
    public ToolSystem(){
        slots[0] = new Pickaxe(true);
        slots[1] = new Spear(false);
        slots[2] = new Bow(false);
        slots[3] = new HealthPotion(false);
        slots[4] = new Armor(false);
    }

    /**
     * Sets the active tool slot
     * Precondition: slotIndex is between 0 and 3
     * Postcondition: activeSlot is updated to slotIndex if the tool is unlocked
     * @param slotIndex
     */
    public void setActiveSlot(int slotIndex){
        if (slotIndex >= 0 && slotIndex < slots.length){
            if (slots[slotIndex].getIsUnlocked()){
                activeSlot = slotIndex;
            }
        }
    }

    /**
     * Gets the currently active slot index
     * Precondition: N/A
     * Postcondition: returns the active slot index
     * @return
     */
    public int getActiveSlotIndex() {
        return activeSlot;
    }

    /**
     * Gets the currently active tool
     * Precondition: N/A
     * Postcondition: returns the active Tool
     * @return
     */
    public Tool getActiveTool(){
        return slots[activeSlot];
    }

    /**
     * Gets the tool in the specified slot
     * Precondition: slotIndex is between 0 and 3
     * Postcondition: returns the Tool in the specified slot, or null if invalid index
     * @param slotIndex
     * @return
     */
    public Tool getToolInSlot(int slotIndex){
        if (slotIndex >= 0 && slotIndex < slots.length){
            return slots[slotIndex];
        }
        return null;
    }   
}
