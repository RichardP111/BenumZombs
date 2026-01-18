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

    public void update(Player player, BuildingSystem buildingSystem, float timeOfDay, int waveCount) {
        boolean isNight = (timeOfDay >= 0.25f && timeOfDay <= 0.75f);
        if (!isNight) {
            waveSpawnedForNight = false;
        }
        
        if (buildingSystem.isGoldStashPlaced() && isNight && !waveSpawnedForNight) {
            spawnWave(waveCount, buildingSystem);
            waveSpawnedForNight = true;
            System.out.println("ZombieSystem.java - Spawned wave " + waveCount);
        }

        Rectangle playerTool = player.getToolBounds();
        int toolDamage = player.getToolDamage();
        ArrayList<Projectile> projectiles = player.getProjectiles();

        for (int i = 0; i < zombies.size(); i++) {
            Zombie zombie = zombies.get(i);
            zombie.update(player, buildingSystem);

            if (playerTool != null && zombie.getBounds().intersects(playerTool)) {
                zombie.takeDamage(toolDamage, true); 
            }

            for (int j = 0; j < projectiles.size(); j++) {
                Projectile projectile = projectiles.get(j);
                if (projectile.getActive() && zombie.getBounds().intersects(projectile.getBounds())) {
                    zombie.takeDamage((int) projectile.getDamage(), true); 
                    projectile.setActive(false);
                }
            }

            if (zombie.isDead()) {
                zombies.remove(i);
                i--;
            }
        }
    }

    private void spawnWave(int waveCount, BuildingSystem buildingSystem) {
        int tier = ((waveCount - 1) / 10) + 1;
        int level = ((waveCount - 1) % 10) + 1;

        if (tier > 6){
            tier = 6;
        }

        int zombieCount = 10 + (waveCount * 2);
        for (int i = 0; i < zombieCount; i++) {
            Point spawnPoint = RandomGeneration.getRandomBaseLocation(buildingSystem);
            zombies.add(new Zombie(spawnPoint.x, spawnPoint.y, tier, level));
        }
    }

    public void draw(Graphics2D g2d) {
        for (Zombie z : zombies) {
            z.draw(g2d);
        }
    }
    
    public ArrayList<Zombie> getZombies() { return zombies; }
}