/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Resource System for managing resources and spawning
 */

package systems;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import objects.Stone;
import objects.Tree;

public class ResourceSystem {
    private final ArrayList<Tree> trees = new ArrayList<>();
    private final ArrayList<Stone> stones = new ArrayList<>();

    /**
     * Constructor for Resource System
     * Precondition: N/A
     * Postcondition: N/A
     * @param player
     */
    public ResourceSystem(){
    }

    /**
     * Spawns resources randomly on the map
     * Precondition: count is a positive integer
     * Postcondition: Resources are spawned on the map
     * @param count
     */
    public void spawnResources( int count){
        Random random = new Random();

        for (int i = 0; i < count; i++){
            java.awt.Point point = helpers.RandomGeneration.getRandomLocation();

            if (random.nextFloat() < 0.5){
                trees.add(new Tree(point.x, point.y));
            } else {
                stones.add(new Stone(point.x, point.y));
            }
        }
    }

    public void update() {
    }

    /**
     * Draws the resources on the screen
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: Resources are drawn on the screen
     * @param g2d
     */
    public void draw(Graphics2D g2d){
        for (int i = 0; i < trees.size(); i++){
            trees.get(i).draw(g2d);
        }
        for (int i = 0; i < stones.size(); i++){
            stones.get(i).draw(g2d);
        }
    }

    /**
     * Gets the list of trees
     * Precondition: None
     * Postcondition: List of trees is returned
     * @return
     */
    public ArrayList<Tree> getTrees() {
        return trees;
    }

    /**
     * Gets the list of stones
     * Precondition: None
     * Postcondition: List of stones is returned
     * @return
     */
    public ArrayList<Stone> getStones() {
        return stones;
    }
}
