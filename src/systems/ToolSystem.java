/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Tool System for managing tools
 */

package systems;

import objects.Tools.*;

public class ToolSystem {
    private final Tool[] slots = new Tool[4];
    private int activeSlot = 0;

    public ToolSystem(){
        slots[0] = new Pickaxe(true);
        slots[1] = new Spear(false);
        slots[2] = new Bow(false);
        slots[3] = new HealthPotion(false);
    }

    public void setActiveSlot(int slotIndex){
        if (slotIndex >= 0 && slotIndex < slots.length){
            if (slots[slotIndex].getIsUnlocked()){
                activeSlot = slotIndex;
            }
        }
    }

    public Tool getActiveTool(){
        return slots[activeSlot];
    }

    public Tool getToolInSlot(int slotIndex){
        if (slotIndex >= 0 && slotIndex < slots.length){
            return slots[slotIndex];
        }
        return null;
    }   
}
