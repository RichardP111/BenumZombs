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
import objects.Player;
import objects.Stone;
import objects.Tree;

public class ResourceSystem {
    private final ArrayList<Tree> trees = new ArrayList<>();
    private final ArrayList<Stone> stones = new ArrayList<>();
    //private final Player player;

    public ResourceSystem(Player player){
        //this.player = player;
    }

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

    public void draw(Graphics2D g2d){
        for (int i = 0; i < trees.size(); i++){
            trees.get(i).draw(g2d);
        }
        for (int i = 0; i < stones.size(); i++){
            stones.get(i).draw(g2d);
        }
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }
    public ArrayList<Stone> getStones() {
        return stones;
    }
}
