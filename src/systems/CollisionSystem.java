/**
 * CollisionSystem.java
 * The CollisionSystem class for BenumZombs, managing collision detection between game objects
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-10
 */

package systems;

import java.awt.Rectangle;
import java.util.ArrayList;
import objects.Buildings.Building;
import objects.Stone;
import objects.Tree;

public class CollisionSystem {
    /**
     * Checks for collisions between a bounding box and resources (trees and stones)
     * Precondition: bouds is a valid Rectangle, resourceSystem is a valid ResourceSystem
     * Postcondition: returns true if a collision is detected, false otherwise
     * @param bouds the bounding box to check for collisions
     * @param resourceSystem the resource system containing trees and stones
     * @return true if a collision is detected, false otherwise
     */
    public static boolean checkResourceCollision(Rectangle bouds, ResourceSystem resourceSystem) {
        //************* Check Collisions Against Trees *************//
        for (int i = 0; i < resourceSystem.getTrees().size(); i++) {
            Tree tree = resourceSystem.getTrees().get(i);
            if (bouds.intersects(tree.getBounds())) {
                return true;
            }
        } 

        //************* Check Collisions Against Stones *************//
        for (int i = 0; i < resourceSystem.getStones().size(); i++) {
            Stone stone = resourceSystem.getStones().get(i);
            if (bouds.intersects(stone.getBounds())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for hit collisions between a bounding box and resources (trees and stones)
     * Precondition: toolBounds is a valid Rectangle, resourceSystem is a valid ResourceSystem
     * Postcondition: returns "tree" if a tree is hit, "stone" if a stone is hit, null otherwise
     * @param toolBounds the bounding box to check for hit collisions
     * @param resourceSystem the resource system containing trees and stones
     * @return "tree" if a tree is hit, "stone" if a stone is hit, null otherwise
     */
    public static String checkResourceHitCollision(Rectangle toolBounds, ResourceSystem resourceSystem) {
        //************* Check Collisions Against Trees *************//
        for (int i = 0; i < resourceSystem.getTrees().size(); i++) {
            Tree tree = resourceSystem.getTrees().get(i);
            if (toolBounds.intersects(tree.getBounds())) {
                tree.playAnimation();
                return "tree";
            }
        } 

        //************* Check Collisions Against Stones *************//
        for (int i = 0; i < resourceSystem.getStones().size(); i++) {
            Stone stone = resourceSystem.getStones().get(i);
            if (toolBounds.intersects(stone.getBounds())) {
                stone.playAnimation();
                return "stone";
            }
        }

        return null;
    }

    /**
     * Checks for collisions between a bounding box and placed buildings
     * Precondition: box is a valid Rectangle, buildingSystem is a valid BuildingSystem
     * Postcondition: returns true if a collision is detected, false otherwise
     * @param box the bounding box to check for collisions
     * @param buildingSystem the building system containing placed buildings
     * @return true if a collision is detected, false otherwise
     */
    public static boolean checkBuildingCollision(Rectangle box, BuildingSystem buildingSystem) {
        ArrayList<Building> buildings = buildingSystem.getPlacedBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            Rectangle buildingRect = new Rectangle((int)building.getX(), (int)building.getY(), building.getWidth(), building.getHeight());
            if (buildingRect.intersects(box)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for collisions between a bounding box and solid buildings (excluding Door and Slow Trap)
     * Precondition: box is a valid Rectangle, buildingSystem is a valid BuildingSystem
     * Postcondition: returns true if a collision is detected with a solid building, false otherwise
     * @param box the bounding box to check for collisions
     * @param buildingSystem the building system containing placed buildings
     * @return true if a collision is detected with a solid building, false otherwise
     */
    public static boolean checkSolidBuildingCollision(Rectangle box, BuildingSystem buildingSystem) {
        ArrayList<Building> buildings = buildingSystem.getPlacedBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            
            if (building.getName().equals("Door") || building.getName().equals("Slow Trap")) {
                break;
            }

            Rectangle buildingRect = new Rectangle((int)building.getX(), (int)building.getY(), building.getWidth(), building.getHeight());
            if (buildingRect.intersects(box)) {
                return true;
            }
        }
        return false;
    }
}
