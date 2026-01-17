/**
 * CollisionSystem.java
 * The CollisionSystem class for BenumZombs, managing collision detection between game objects
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-10
 */

package systems;

import java.awt.*;
import objects.Stone;
import objects.Tree;

public class CollisionSystem {
    /**
     * Checks for collisions between a bounding box and resources
     * Precondition: bouds is a valid Rectangle, resourceSystem is a valid ResourceSystem
     * Postcondition: returns true if a collision is detected, false otherwise
     * @param bouds the bounding box to check for collisions
     * @param resourceSystem the resource system containing trees and stones
     * @return true if collision detected, false otherwise
     */
    public static boolean checkCollision(Rectangle bouds, ResourceSystem resourceSystem) {
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
        
        //other stuff later if im still alive

        return false;
    }

    /**
     * Checks for hit collisions between a tool's bounding box and resources
     * Precondition: toolBounds is a valid Rectangle, resourceSystem is a valid ResourceSystem
     * Postcondition: returns the type of resource hit ("tree" or "stone"), or null if no collision
     * @param toolBounds the bounding box of the tool to check for collisions
     * @param resourceSystem the resource system containing trees and stones
     * @return the type of resource hit ("tree" or "stone"), or null if no collision
     */
    public static String checkHitCollision(Rectangle toolBounds, ResourceSystem resourceSystem) {
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
}
