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
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.imageio.ImageIO;
import objects.Buildings.Building;
import systems.BuildingSystem;
import systems.CollisionSystem;

public class Zombie extends GameObject {
    private int tier;
    private int level;
    private double speed;
    private int damage;
    
    private int maxHealth;
    private int currentHealth;

    private static Image teacherImage;
    private Color tierColor;

    private boolean targetingPlayer = false;

    public Zombie(double x, double y, int tier, int level) {
        super(x, y, 35, 35, null);
        this.tier = tier;
        this.level = level;

        maxHealth = (tier * 50) + (level * 20);
        currentHealth = maxHealth;
        damage = (int) (10 + (tier * 0.2) + (level * 0.05));
        speed = 2;

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

        if (teacherImage == null) {
            try {
                teacherImage = ImageIO.read(getClass().getResource("/assets/images/zombies/benum.png"));
            } catch (IOException | IllegalArgumentException | NullPointerException e) {
                System.err.println("Error loading teacher image and you won't have a working game: " + e.getMessage());
            }
        }
    }

    public void update(Player player, BuildingSystem buildingSystem) {
        double targetX = x;
        double targetY = y;
        boolean hasATarget = false;

        if (targetingPlayer){
            targetX = player.getX();
            targetY = player.getY();
            hasATarget = true;
        } else {
            Building stash = buildingSystem.getActiveStash();
            if (stash != null) {
                targetX = stash.getX();
                targetY = stash.getY();
                hasATarget = true;
            }
        }

        if (!hasATarget) {
            return;
        }

        double dx = targetX - x;
        double dy = targetY - y;
        double angle = Math.atan2(dy, dx);
        
        double moveX = Math.cos(angle) * speed;
        double moveY = Math.sin(angle) * speed;

        Rectangle nextXPos = new Rectangle((int)(x + moveX), (int)y, width, height);
        if (!CollisionSystem.checkSolidBuildingCollision(nextXPos, buildingSystem)) {
            x += moveX;
        }
        
        Rectangle nextYPos = new Rectangle((int)x, (int)(y + moveY), width, height);
        if (!CollisionSystem.checkSolidBuildingCollision(nextYPos, buildingSystem)) {
            y += moveY;
        }

        if (new Rectangle((int)x, (int)y, width, height).intersects(player.getBounds())){
            player.takeDamage(this.damage / 10);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (teacherImage != null) {
            AffineTransform oldTransform = g2d.getTransform();

            g2d.setColor(new Color(tierColor.getRed(), tierColor.getGreen(), tierColor.getBlue(), 100));
            g2d.fillOval((int)x + 5, (int)y + 28, 12, 12); 
            g2d.fillOval((int)x + width - 19, (int)y + 28, 12, 12);
            
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3f));
            g2d.drawOval((int)x + 5, (int)y + 28, 12, 12);
            g2d.drawOval((int)x + width - 19, (int)y + 28, 12, 12);

            //************* Zombie Body *************//
            g2d.drawImage(teacherImage, (int)x, (int)y, width, height, null);

            g2d.setColor(new Color(tierColor.getRed(), tierColor.getGreen(), tierColor.getBlue(), 100));
            g2d.fillOval((int)x, (int)y, width, height); 
            
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3f)); 
            g2d.drawOval((int)x, (int)y, width, height);
            g2d.setTransform(oldTransform);

        }

        HealthManager.drawStatusBar(g2d, currentHealth, maxHealth, (int)x, (int)y, width, height, new Color(122, 55, 32), false);
    }

    public boolean isDead() { 
        return currentHealth <= 0; 
    }

    public Rectangle getBounds() { 
        return new Rectangle((int)x, (int)y, width, height); 
    }

    public void takeDamage(int damage, boolean fromPlayer) { 
        currentHealth -= damage; 

        if (fromPlayer && !targetingPlayer) {
            if (Math.random() < 0.3) {
                targetingPlayer = true;
            }
        }
    }

    public int getDamage() { 
        return damage; 
    }

    @Override
    public void update() {
    }
}