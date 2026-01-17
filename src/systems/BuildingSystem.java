/**
 * BuildingSystem.java
 * Manages the available buildings in the game
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-16
 */

package systems;

import objects.Buildings.*;

public class BuildingSystem {
    private final Building[] slots = new Building[11];
    //private int activeSlot = -1; 

    /**
     * Constructor for Building System
     * Precondition: N/A
     * Postcondition: Buildings are initialized in their respective slots
     */
    public BuildingSystem() {

        slots[0] = new Wall(0, 0);
        slots[1] = new Door(0, 0);
        slots[2] = new SlowTrap(0, 0);
        slots[3] = new ArrowTower(0, 0);
        slots[4] = new CannonTower(0, 0);
        slots[5] = new MeleeTower(0, 0);
        slots[6] = new BombTower(0, 0);
        slots[7] = new MageTower(0, 0);
        slots[8] = new GoldMine(0, 0);
        slots[9] = new Harvester(0, 0);
        slots[10] = new GoldStash(0, 0);
    }

    /**
     * Gets the building in the specified slot
     * Precondition: index is between 0 and 10
     * Postcondition: returns the Building in the specified slot
     * @param index the index of the slot
     * @return the Building in the specified slot
     */
    public Building getBuildingInSlot(int index) {
        if (index >= 0 && index < slots.length) {
            return slots[index];
        }
        return null;
    }
}