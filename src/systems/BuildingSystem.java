/**
 * BuildingSystem.java
 * Manages the available buildings in the game
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-16
 */

package systems;

import game.BenumZombsGame;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import objects.Buildings.ArrowTower;
import objects.Buildings.BombTower;
import objects.Buildings.Building;
import objects.Buildings.CannonTower;
import objects.Buildings.Door;
import objects.Buildings.GoldMine;
import objects.Buildings.GoldStash;
import objects.Buildings.Harvester;
import objects.Buildings.MageTower;
import objects.Buildings.MeleeTower;
import objects.Buildings.SlowTrap;
import objects.Buildings.Wall;
import objects.Projectile;

public class BuildingSystem {
    private final Building[] slots = new Building[11];
    private final ArrayList<Building> placedBuildings = new ArrayList<>();
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    private Building activeStash = null;
    private boolean goldStashPlaced = false;

    private Building selectedBuilding = null;

    /**
     * Constructor for Building System
     * Precondition: N/A
     * Postcondition: Buildings are initialized in their respective slots
     */
    public BuildingSystem() {
        int smallSize = BenumZombsGame.GRID_SIZE; 
        int largeSize = BenumZombsGame.GRID_SIZE * 2;

        slots[0] = new Wall(0, 0);
        slots[0].setWidth(smallSize); 
        slots[0].setHeight(smallSize);

        slots[1] = new Door(0, 0);
        slots[1].setWidth(smallSize);
        slots[1].setHeight(smallSize);

        slots[2] = new SlowTrap(0, 0);
        slots[2].setWidth(smallSize);
        slots[2].setHeight(smallSize);

        slots[3] = new ArrowTower(0, 0);
        slots[3].setWidth(largeSize); 
        slots[3].setHeight(largeSize);

        slots[4] = new CannonTower(0, 0);
        slots[4].setWidth(largeSize); 
        slots[4].setHeight(largeSize);

        slots[5] = new MeleeTower(0, 0);
        slots[5].setWidth(largeSize); 
        slots[5].setHeight(largeSize);

        slots[6] = new BombTower(0, 0);
        slots[6].setWidth(largeSize); 
        slots[6].setHeight(largeSize);

        slots[7] = new MageTower(0, 0);
        slots[7].setWidth(largeSize); 
        slots[7].setHeight(largeSize);

        slots[8] = new GoldMine(0, 0);
        slots[8].setWidth(largeSize); 
        slots[8].setHeight(largeSize);

        slots[9] = new Harvester(0, 0);
        slots[9].setWidth(largeSize); 
        slots[9].setHeight(largeSize);

        slots[10] = new GoldStash(0, 0);
        slots[10].setWidth(largeSize); 
        slots[10].setHeight(largeSize);

    }

    /**
     * Creates a new Building instance based on the provided Building prototype
     * Precondition: building is not null
     * Postcondition: returns a new Building instance at specified coordinates
     * @param building the Building prototype to copy
     * @param x the x-coordinate of the new Building
     * @param y the y-coordinate of the new Building
     * @return a new Building instance
     */
    public Building createBuilding(Building building, double x, double y) {
        if (building != null) {
            Building buildingCopy = building.createCopy(x, y);
            buildingCopy.setWidth(building.getWidth());
            buildingCopy.setHeight(building.getHeight());
            return buildingCopy;
        }
        return null;
    }

    /**
     * Places a building in the game world
     * Precondition: building is not null
     * Postcondition: building is added to the placed buildings list
     * @param building the Building to place
     */
    public void placeBuilding(Building building) {
        placedBuildings.add(building);
        if (building.isUnlocker()) {
            this.activeStash = building;
            onGoldStashPlaced();
        }
    }

    /**
     * Selects a building at the specified world coordinates
     * Precondition: N/A
     * Postcondition: selectedBuilding is set to the building at the coordinates, or null if none
     * @param worldX the x-coordinate in the world
     * @param worldY the y-coordinate in the world
     * @return true if a building was selected, false otherwise
     */
    public boolean selectBuildingAt(int worldX, int worldY) {
        for (int i = placedBuildings.size() - 1; i >= 0; i--) {
            Building building = placedBuildings.get(i);
            Rectangle buildingRect = new Rectangle((int)building.getX(), (int)building.getY(), building.getWidth(), building.getHeight());
            
            // Check if click is within building bounds
            if (buildingRect.contains(worldX, worldY)) {
                selectedBuilding = building;
                return true;
            }
        }
        
        // Deselect if clicked on empty space
        selectedBuilding = null;
        return false;
    }
    
    /**
     * Deselects the currently selected building
     * Precondition: N/A
     * Postcondition: selectedBuilding is set to null
     */
    public void deselect() {
        selectedBuilding = null;
    }
    
    /**
     * Gets the currently selected building
     * Precondition: N/A
     * Postcondition: returns the currently selected building, or null if none
     * @return the currently selected building
     */
    public Building getSelectedBuilding() {
        return selectedBuilding;
    }
    
    /**
     * Removes a building from the placed buildings list
     * Precondition: building is not null
     * Postcondition: building is removed from the placed buildings list
     * @param building the Building to remove
     */
    public void removeBuilding(Building building) {
        placedBuildings.remove(building);
        if (building == selectedBuilding) {
            selectedBuilding = null;
        }
    }

    /**
     * Checks if a given area is occupied by any placed buildings
     * Precondition: area is not null
     * Postcondition: returns true if the area is occupied, false otherwise
     * @param area the area to check
     * @return true if the area is occupied, false otherwise
     */
    public boolean isOccupied(Rectangle area) {
        for (int i = 0; i < placedBuildings.size(); i++) {
            Building building = placedBuildings.get(i);
            Rectangle buildingRect = new Rectangle((int)building.getX(), (int)building.getY(), building.getWidth(), building.getHeight());
            if (buildingRect.intersects(area)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles actions to be taken when the Gold Stash is placed
     * Precondition: N/A
     * Postcondition: All locked buildings are unlocked
     */
    public void onGoldStashPlaced() {
        goldStashPlaced = true;
        
        for (Building slot : slots) {
            if (slot != null) {
                slot.setLocked(false);
            }
        }
        System.out.println("BuildingSystem.java - Gold Stash Placed");
    }

    /**
     * Checks if the Gold Stash has been placed
     * Precondition: N/A
     * Postcondition: returns true if the Gold Stash is placed, false otherwise
     * @return true if the Gold Stash is placed, false otherwise
     */
    public boolean isGoldStashPlaced() {
        return goldStashPlaced;
    }
    
    /**
     * Checks if the limit for a specific building type has been reached
     * Precondition: building is not null
     * Postcondition: returns true if the limit is reached, false otherwise
     * @param building the Building type to check
     * @return true if the limit is reached, false otherwise
     */
    public boolean isLimitReached(Building building) {
        return getBuildingCount(building) >= getBuildingLimit(building);
    }


    /**
     * Gets the count of a specific building type that has been placed
     * Precondition: building is not null
     * Postcondition: returns the count of the specified building type
     * @param building the Building type to count
     * @return the count of the specified building type
     */
    public int getBuildingCount(Building building) {
        int count = 0;
        String targetName = building.getName();
        for (int i = 0; i < placedBuildings.size(); i++) {
            if (placedBuildings.get(i).getName().equals(targetName)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the limit of a specific building type
     * Precondition: building is not null
     * Postcondition: returns the limit of the specified building type
     * @param building the Building type to get the limit for
     * @return the limit of the specified building type
     */
    public int getBuildingLimit(Building building) {
        if (activeStash == null) {
            if (building.isUnlocker()) return 1;
            return 0; 
        }
        return building.getLimit();
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

    /**
     * Gets the active Gold Stash building
     * Precondition: N/A
     * Postcondition: returns the active Gold Stash building
     * @return the active Gold Stash building
     */
    public Building getActiveStash() {
        return activeStash;
    }

    /**
     * Gets the list of placed buildings
     * Precondition: N/A
     * Postcondition: returns the list of placed buildings
     * @return the list of placed buildings
     */
    public ArrayList<Building> getPlacedBuildings() {
        return placedBuildings;
    }

    /**
     * Adds a projectile to the projectile list
     * Precondition: projectile is not null
     * Postcondition: projectile is added to the projectile list
     * @param projectile the Projectile to add
     */
    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }
    
    /**
     * Gets the list of active projectiles
     * Precondition: N/A
     * Postcondition: returns the list of active projectiles
     * @return the list of active projectiles
     */
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * Resets the BuildingSystem to its initial state
     * Precondition: N/A
     * Postcondition: All placed buildings and projectiles are cleared, and building slots are locked
     */
    public void reset() {
        placedBuildings.clear();
        projectiles.clear();
        activeStash = null;
        goldStashPlaced = false;
        selectedBuilding = null;
        
        for (Building slot : slots) {
            if (slot != null && !slot.isUnlocker()) {
                slot.setLocked(false);
            }
        }
    }

    /**
     * Updates all placed buildings
     * Precondition: N/A
     * Postcondition: all placed buildings are updated
     */
    public void update(ResourceSystem resourceSystem, ZombieSystem zombieSystem) {
        //************* Update Placed Buildings *************//
        for (int i = 0; i < placedBuildings.size(); i++) {
            Building building = placedBuildings.get(i);
            building.update(resourceSystem, zombieSystem, this);

            //************* Remove Destroyed Buildings *************//
            if (building.getHealth() <= 0) {
                removeBuilding(building);
                i--; 
            }
        }

        //************* Update Projectiles *************//
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            p.update();
            if (!p.getActive()) {
                projectiles.remove(i);
                i--;
            }
        }
    }

    /**
     * Draws all placed buildings
     * Precondition: g2d is not null
     * Postcondition: all placed buildings are drawn
     * @param g2d the Graphics2D object to draw with
     */
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < placedBuildings.size(); i++) {
            Building building = placedBuildings.get(i);
            building.draw(g2d);

            building.drawHealthBar(g2d);
        }

        //************* Draw Projectiles *************//
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            projectile.draw(g2d);
        }
    }
}