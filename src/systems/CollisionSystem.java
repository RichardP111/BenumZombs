/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Collision System for managing collisions
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
     * @param bouds
     * @param resourceSystem
     * @return
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

        return false;
    }
}
