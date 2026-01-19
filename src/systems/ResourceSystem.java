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
     * Postcondition: ResourceSystem object is created
     */
    public ResourceSystem() {
        trees = new ArrayList<>();
        stones = new ArrayList<>();

        woodCount = 0;
        stoneCount = 0;
        goldCount = 0;
        tokenCount = 0;
    }

    /**
     * Spawns resources randomly on the map
     * Precondition: count is a positive integer
     * Postcondition: Resources are spawned on the map
     * @param count the number of resources to spawn
     */
    public void spawnResources( int count) {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Point point = RandomGeneration.getRandomLocation();

            if (random.nextFloat() < 0.5) { //50% chance for tree or stone
                trees.add(new Tree(point.x, point.y));
            } else {
                stones.add(new Stone(point.x, point.y));
            }
        }
    }

    /**
     * Updates the resources' animation states
     * Precondition: N/A
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
     * @param g2d the Graphics2D object used for drawing
     */
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < trees.size(); i++) {
            trees.get(i).draw(g2d);
        }
        for (int i = 0; i < stones.size(); i++) {
            stones.get(i).draw(g2d);
        }
    }

    /**
     * Gets the list of trees
     * Precondition: N/A
     * Postcondition: List of trees is returned
     * @return the list of Tree objects
     */
    public ArrayList<Tree> getTrees() {
        return trees;
    }

    /**
     * Gets the list of stones
     * Precondition: N/A
     * Postcondition: List of stones is returned
     * @return the list of Stone objects
     */
    public ArrayList<Stone> getStones() {
        return stones;
    }

    /**
     * Adds wood to the resource count
     * Precondition: amount is a integer
     * Postcondition: Wood count is increased by amount
     * @param amount the amount of wood to add
     */
    public void addWood(int amount) {
        woodCount += amount;      
    }

    /**
     * Adds stone to the resource count
     * Precondition: amount is a integer
     * Postcondition: Stone count is increased by amount
     * @param amount the amount of stone to add
     */
    public void addStone(int amount) {
        stoneCount += amount;      
    }

    /**
     * Adds gold to the resource count
     * Precondition: amount is a integer
     * Postcondition: Gold count is increased by amount
     * @param amount the amount of gold to add
     */
    public void addGold(int amount) {
        goldCount += amount;      
    }

    /**
     * Adds tokens to the resource count
     * Precondition: amount is a integer
     * Postcondition: Token count is increased by amount
     * @param amount the amount of tokens to add
     */
    public void addTokens(int amount) {
        tokenCount += amount;      
    }

    /**
     * Gets the current wood count
     * Precondition: N/A
     * Postcondition: Current wood count is returned
     * @return the current wood count
     */
    public int getWoodCount() {
        return woodCount;
    }

    /**
     * Gets the current stone count
     * Precondition: N/A
     * Postcondition: Current stone count is returned
     * @return the current stone count
     */
    public int getStoneCount() {
        return stoneCount;
    }

    /**
     * Gets the current gold count
     * Precondition: N/A
     * Postcondition: Current gold count is returned
     * @return the current gold count
     */
    public int getGoldCount() {
        return goldCount;
    }

    /**
     * Gets the current token count
     * Precondition: N/A
     * Postcondition: Current token count is returned
     * @return the current token count
     */
    public int getTokenCount() {
        return tokenCount;
    }

    /**
     * Checks if the player can afford a cost in resources
     * Precondition: woodCost, stoneCost, and goldCost are non-negative integers
     * Postcondition: returns true if the player can afford the cost, false otherwise
     * @param woodCost the wood cost to check
     * @param stoneCost the stone cost to check
     * @param goldCost the gold cost to check
     * @return true if the player can afford the cost, false otherwise
     */
    public boolean canAfford(int woodCost, int stoneCost, int goldCost) {
        return woodCount >= woodCost && stoneCount >= stoneCost && goldCount >= goldCost;
    }

    /**
     * Resets the resource counts
     * Precondition: N/A
     * Postcondition: Resource counts are reset
     * @param keepGold true to keep gold count, false to reset it
     */
    public void reset(boolean keepGold) {
        this.woodCount = 0;
        this.stoneCount = 0;
        this.tokenCount = 0;
        if (!keepGold) {
            this.goldCount = 0;
        }
    }

    // Very super duper ultra classified level 99 clearance secret dev mode method to add resources because im too lazy to acutally play the game normally
    public void devModeAddResources() {
        woodCount = 99999999;
        stoneCount = 99999999;
        goldCount = 99999999;
        tokenCount = 99999999;
    }
}
