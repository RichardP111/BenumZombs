/**
 * Zombie.java
 * The Zombie class for BenumZombs, defining zombie properties and behaviors
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-18
 */

package objects;

import helpers.HealthManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.imageio.ImageIO;
import objects.Buildings.Building;
import systems.BuildingSystem;
import systems.CollisionSystem;
import systems.ResourceSystem;

public class Zombie extends GameObject {
    private double speed;
    private int damage;
    
    private int maxHealth;
    private int currentHealth;

    private static Image teacherImage;
    private Color tierColor;

    //************* Targeting and Attack *************//
    private Building targetBuilding = null;
    private double rotationAngle = 0.0;
    private long lastAttackTime = 0;
    private static final long ATTACK_COOLDOWN = 1000;

    /**
     * Constructor for Zombie
     * Precondition: N/A
     * Postcondition: Zombie object is created
     * @param x the x-coordinate of the Zombie
     * @param y the y-coordinate of the Zombie
     * @param tier the tier of the Zombie
     * @param level the level of the Zombie
     */
    public Zombie(double x, double y, int tier, int level) {
        super(x, y, 35, 35, null);

        this.maxHealth = 100 + (tier * 50) + (level * 20);
        this.currentHealth = this.maxHealth;
        this.damage = (int) (1 + (tier * 0.2) + (level * 0.05));
        this.speed = 2;

        //************* Set Tier Color *************//
        switch (tier) {
            case 1:
                tierColor = new Color(50, 77, 58); // Green
                break;
            case 2:
                tierColor = new Color(41, 74, 121); // Blue
                break;
            case 3:
                tierColor = new Color(121, 26, 50); // Red
                break;
            case 4:
                tierColor = new Color(147, 118, 43); // Yellow
                break;
            case 5:
                tierColor = new Color(116, 38, 187); // Purple
                break;
            case 6:
                tierColor = new Color(176, 87, 60); // Orange Red
                break;
            default:
                tierColor = new Color(50, 77, 58);
        }

        //************* Load Zombie Image *************//
        if (teacherImage == null) {
            try {
                teacherImage = ImageIO.read(getClass().getResource("/assets/images/zombies/benum.png"));
            } catch (IOException | IllegalArgumentException | NullPointerException e) {
                System.err.println("Error loading teacher image and you won't have a working game: " + e.getMessage());
            }
        }
    }

    /**
     * Updates the zombie's position and attack behavior
     * Precondition: N/A
     * Postcondition: Zombie's position and attack state are updated
     * @param player the Player object
     * @param buildingSystem the BuildingSystem object
     * @param resourceSystem the ResourceSystem object
     */ 
    public void update(Player player, BuildingSystem buildingSystem, ResourceSystem resourceSystem) {
        double targetX = x;
        double targetY = y;
        boolean attackingBuilding = false;

        //************* Target Building *************//
        if (targetBuilding != null) {
            if (!buildingSystem.getPlacedBuildings().contains(targetBuilding)) {
                targetBuilding = null;
            } else {
                targetX = targetBuilding.getX() + targetBuilding.getWidth() / 2 - width / 2;
                targetY = targetBuilding.getY() + targetBuilding.getHeight() / 2 - height / 2;
                attackingBuilding = true;
            }
        }

        //************* Target Stash Or Player *************//
        if (targetBuilding == null) {
            Building stash = buildingSystem.getActiveStash();
            if (stash != null) {
                targetBuilding = stash;
                targetX = stash.getX() + stash.getWidth() / 2 - width / 2;
                targetY = stash.getY() + stash.getHeight() / 2 - height / 2;
                attackingBuilding = true;
            } else {
                targetX = player.getX();
                targetY = player.getY();
                attackingBuilding = false;
            }
        }
    
        //************* Target Movement *************//
        double dx = targetX - x;
        double dy = targetY - y;
        rotationAngle = Math.atan2(dy, dx);
        
        double moveX = Math.cos(rotationAngle) * speed;
        double moveY = Math.sin(rotationAngle) * speed;

        //************* Collision Check *************//
        Rectangle nextXPos = new Rectangle((int)(x + moveX), (int)y, width, height);
        if (!CollisionSystem.checkSolidBuildingCollision(nextXPos, buildingSystem) && !CollisionSystem.checkResourceCollision(nextXPos, resourceSystem) && !CollisionSystem.checkPlayerCollision(nextXPos, player)) {
            x += moveX;
        }
        
        Rectangle nextYPos = new Rectangle((int)x, (int)(y + moveY), width, height);
        if (!CollisionSystem.checkSolidBuildingCollision(nextYPos, buildingSystem) && !CollisionSystem.checkResourceCollision(nextYPos, resourceSystem) && !CollisionSystem.checkPlayerCollision(nextYPos, player)) {
            y += moveY;
        }

        //************* Attack Handling *************//
        Rectangle zombieBounds = new Rectangle((int)x, (int)y, width, height);
        long now = System.currentTimeMillis();

        if (attackingBuilding && targetBuilding != null) {
            if (CollisionSystem.checkBuildingCollision(zombieBounds, buildingSystem)) {
                if (now - lastAttackTime > ATTACK_COOLDOWN) {
                    targetBuilding.takeDamage(damage);
                    lastAttackTime = now;
                }
            }
        }

        if (!attackingBuilding || CollisionSystem.checkPlayerCollision(zombieBounds, player)) {
             if (CollisionSystem.checkPlayerCollision(zombieBounds, player)) {
                if (now - lastAttackTime > ATTACK_COOLDOWN) {
                    player.takeDamage(damage);
                    lastAttackTime = now;
                }
             }
        }
    }

    /**
     * Draws the zombie on the screen
     * Precondition: N/A
     * Postcondition: Zombie is drawn on the screen
     * @param g2d the Graphics2D object for drawing
     */
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double centerX = x + width / 2;
        double centerY = y + height / 2;

        AffineTransform oldTransform = g2d.getTransform(); // Save current transform
        g2d.rotate(rotationAngle - Math.PI / 2, centerX, centerY);

        //************* Draw Zombie Hands *************//
        g2d.setColor(tierColor); 
        g2d.fillOval((int)x + 3, (int)y + 28, 12, 12); 
        g2d.fillOval((int)x + width - 17, (int)y + 28, 12, 12);
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawOval((int)x + 3, (int)y + 28, 12, 12);
        g2d.drawOval((int)x + width - 17, (int)y + 28, 12, 12);

        //************* Draw Zombie Body *************//
        if (teacherImage != null) {
            g2d.drawImage(teacherImage, (int)x, (int)y, width, height, null);
            g2d.setColor(new Color(tierColor.getRed(), tierColor.getGreen(), tierColor.getBlue(), 100)); 
            g2d.fillOval((int)x, (int)y, width, height); 
        } else {
            g2d.setColor(tierColor);
            g2d.fillOval((int)x, (int)y, width, height); 
        }
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3f)); 
        g2d.drawOval((int)x, (int)y, width, height);

        g2d.setTransform(oldTransform); // Restore original transform

        HealthManager.drawStatusBar(g2d, currentHealth, maxHealth, (int)x, (int)y, width, height, new Color(122, 55, 32), false);
    }

    /**
     * Checks if the zombie is dead
     * Precondition: N/A
     * Postcondition: returns true if the zombie's health is zero or below
     * @return true if the zombie is dead, false otherwise
     */
    public boolean isDead() { 
        return currentHealth <= 0; 
    }

    /**
     * Gets the bounding rectangle of the zombie for hit detection
     * Precondition: N/A
     * Postcondition: returns Rectangle of zombie's hitbox
     * @return the Rectangle representing the zombie's hitbox
     */
    public Rectangle getBounds() { 
        return new Rectangle((int)x, (int)y, width, height); 
    }

    /**
     * Reduces the zombie's health by the specified damage amount
     * Precondition: N/A
     * Postcondition: zombie's health is reduced
     * @param damage the amount of damage to inflict
     * @param fromPlayer true if damage is from player, false otherwise
     */
    public void takeDamage(int damage, boolean fromPlayer) { 
        currentHealth -= damage; 
    }

    /**
     * Gets the damage value of the zombie
     * Precondition: N/A
     * Postcondition: returns the damage value of the zombie
     * @return the damage value of the zombie
     */
    public int getDamage() { 
        return damage; 
    }

    @Override
    public void update() {}
}