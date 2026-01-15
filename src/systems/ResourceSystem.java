/**
 * ResourceSystem.java
 * The ResourceSystem class for BenumZombs, managing resources and their spawning
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-08
 */

package systems;

import helpers.RandomGeneration;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import objects.Stone;
import objects.Tree;

public class ResourceSystem {
    private final ArrayList<Tree> trees;
    private final ArrayList<Stone> stones;

    private int woodCount;
    private int stoneCount;
    private int goldCount;
    private int tokenCount;

    /**
     * Constructor for Resource System
     * Precondition: N/A
     * Postcondition: N/A
     * @param player
     */
    public ResourceSystem(){
        trees = new ArrayList<>();
        stones = new ArrayList<>();

        woodCount = 0;
        stoneCount = 0;
        goldCount = 1000000;
        tokenCount = 0;
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
            Point point = RandomGeneration.getRandomLocation();

            if (random.nextFloat() < 0.5){ //50% chance for tree or stone
                trees.add(new Tree(point.x, point.y));
            } else {
                stones.add(new Stone(point.x, point.y));
            }
        }
    }

    /**
     * Updates the resources' animation states
     * Precondition: None
     * Postcondition: Resources are updated
     */
    public void update() {
        for (int j = 0; j < trees.size(); j++) {
            Tree tree = trees.get(j);
            tree.update();
        }

        for (int i = 0; i < stones.size(); i++) {
            Stone stone = stones.get(i);
            stone.update();
        }
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

    /**
     * Adds wood to the resource count
     * Precondition: amount is a positive integer
     * Postcondition: Wood count is increased by amount
     * @param amount
     */
    public void addWood(int amount) {
        woodCount += amount;      
    }

    /**
     * Adds stone to the resource count
     * Precondition: amount is a positive integer
     * Postcondition: Stone count is increased by amount
     * @param amount
     */
    public void addStone(int amount) {
        stoneCount += amount;      
    }

    /**
     * Adds gold to the resource count
     * Precondition: amount is a positive integer
     * Postcondition: Gold count is increased by amount
     * @param amount
     */
    public void addGold(int amount) {
        goldCount += amount;      
    }

    /**
     * Adds tokens to the resource count
     * Precondition: amount is a positive integer
     * Postcondition: Token count is increased by amount
     * @param amount
     */
    public void addTokens(int amount) {
        tokenCount += amount;      
    }

    /**
     * Gets the current wood count
     * Precondition: None
     * Postcondition: Current wood count is returned
     * @return
     */
    public int getWoodCount() {
        return woodCount;
    }

    /**
     * Gets the current stone count
     * Precondition: None
     * Postcondition: Current stone count is returned
     * @return
     */
    public int getStoneCount() {
        return stoneCount;
    }

    /**
     * Gets the current gold count
     * Precondition: None
     * Postcondition: Current gold count is returned
     * @return
     */
    public int getGoldCount() {
        return goldCount;
    }

    /**
     * Gets the current token count
     * Precondition: None
     * Postcondition: Current token count is returned
     * @return
     */
    public int getTokenCount() {
        return tokenCount;
    }
}
