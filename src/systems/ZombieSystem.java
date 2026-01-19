/**
 * ZombieSystem.java
 * The ZombieSystem class for BenumZombs, managing zombies and their behaviors
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-18
 */

package systems;

import helpers.RandomGeneration;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import objects.Player;
import objects.Projectile;
import objects.Zombie;

public class ZombieSystem {
    private final ArrayList<Zombie> zombies = new ArrayList<>();
    private boolean waveSpawnedForNight = false;

    /**
     * Updates the state of all zombies in the system
     * Precondition: N/A
     * Postcondition: All zombies are updated based on player actions and game state
     * @param player the player object
     * @param buildingSystem the building system
     * @param resourceSystem the resource system
     * @param timeOfDay the current time of day in the game
     * @param waveCount the current wave count
     */
    public void update(Player player, BuildingSystem buildingSystem, ResourceSystem resourceSystem, float timeOfDay, int waveCount) {
        boolean isNight = (timeOfDay >= 0.25f && timeOfDay <= 0.75f);
        if (!isNight) {
            waveSpawnedForNight = false;
        }

        //************* Spawn Waves *************//
        if (buildingSystem.isGoldStashPlaced() && isNight && !waveSpawnedForNight) {
            spawnWave(waveCount, buildingSystem, resourceSystem);
            waveSpawnedForNight = true;
            System.out.println("ZombieSystem.java - Spawned wave " + waveCount);
        }

        //************* Update Zombies *************//
        Rectangle playerTool = player.getToolBounds();
        int toolDamage = player.getToolDamage();
        ArrayList<Projectile> allProjectiles = player.getProjectiles();
        allProjectiles.addAll(player.getProjectiles());
        allProjectiles.addAll(buildingSystem.getProjectiles());

        for (int i = 0; i < zombies.size(); i++) {
            Zombie zombie = zombies.get(i);
            zombie.update(player, buildingSystem, resourceSystem);

            //************* Handle Player Tool Damage *************//
            if (playerTool != null && zombie.getBounds().intersects(playerTool)) {
                zombie.takeDamage(toolDamage, true); 
            }

            //************* Handle Projectile Damage *************//
            for (int j = 0; j < allProjectiles.size(); j++) {
                Projectile projectile = allProjectiles.get(j);
                if (projectile.getActive() && zombie.getBounds().intersects(projectile.getBounds())) {
                    zombie.takeDamage((int) projectile.getDamage(), projectile.getDamageRadius() == 0); 
                    if (projectile.getDamageRadius() > 0) {
                        applyAreaDamage(projectile.getX() + projectile.getWidth()/2, projectile.getY() + projectile.getHeight()/2, projectile.getDamageRadius(), projectile.getDamage());
                    }
                    projectile.setActive(false);
                }
            }

            //************* Remove Dead Zombies *************//
            if (zombie.isDead()) {
                zombies.remove(i);
                i--;
            }
        }
    }

    /**
     * Spawns a wave of zombies based on the current wave count
     * Precondition: N/A
     * Postcondition: A new wave of zombies is spawned and added to the system
     * @param waveCount the current wave count
     * @param buildingSystem the building system
     * @param resourceSystem the resource system
     */
    private void spawnWave(int waveCount, BuildingSystem buildingSystem, ResourceSystem resourceSystem) {
        int tier = ((waveCount - 1) / 10) + 1;
        int level = ((waveCount - 1) % 10) + 1;

        if (tier > 6) { // Cap tier at 6
            tier = 6;
        }

        int zombieCount = 10 + (waveCount * 2); // Increase zombie count with each wave
        for (int i = 0; i < zombieCount; i++) {
            Point spawnPoint; //Make sure zombies does not spawn on resources
            while (true) {
                spawnPoint = RandomGeneration.getRandomBaseLocation(buildingSystem);
                Rectangle zombieBounds = new Rectangle(spawnPoint.x, spawnPoint.y, 45, 45);
                
                if (!CollisionSystem.checkResourceCollision(zombieBounds, resourceSystem)) {
                    break; 
                }
            }
            zombies.add(new Zombie(spawnPoint.x, spawnPoint.y, tier, level));
        
        }
    }

    /**
     * Applies area damage to zombies within a certain radius
     * @param cx the x-coordinate of the center of the damage area
     * @param cy the y-coordinate of the center of the damage area
     * @param radius the radius of the damage area
     * @param damage the amount of damage to apply
     */
    private void applyAreaDamage(double cx, double cy, double radius, double damage) {
        for (int i = 0; i < zombies.size(); i++) {
            Zombie zombie = zombies.get(i);
            double dist = Math.hypot(zombie.getX() + zombie.getWidth()/2 - cx, zombie.getY() + zombie.getHeight()/2 - cy);
            if (dist <= radius) {
                zombie.takeDamage( (int) damage, false);
            }
        }
    }

    /**
     * Draws all zombies in the system
     * Precondition: N/A
     * Postcondition: All zombies are drawn to the provided Graphics2D context
     * @param g2d the Graphics2D context to draw on
     */
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < zombies.size(); i++) {
            zombies.get(i).draw(g2d);
        }
    }
    
    /**
     * Gets the list of zombies in the system
     * Precondition: N/A
     * Postcondition: The list of zombies is returned
     * @return the list of zombies
     */
    public ArrayList<Zombie> getZombies() {
        return zombies; 
    }
}