/**
 * Projectile.java
 * The Projectile class for BenumZombs, defining projectile properties and behaviors
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-15
 */

package objects;

import game.BenumZombsGame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Projectile extends GameObject {
    private final double angle;
    private final double speed;
    private final double damage;
    private Image image;
    private boolean active = true;

    private final double startX, startY;
    private static final double MAX_RANGE = BenumZombsGame.GRID_SIZE * 25;

    private int damageRadius = 0;

    /**
     * Constructor for Projectile object
     * Precondition: x and y are valid coordinates within the game window
     * Postcondition: A Projectile object is created at the specified coordinates with given angle and damage
     * @param x the x-coordinate of the projectile
     * @param y the y-coordinate of the projectile
     * @param angle the angle at which the projectile is fired
     * @param damage the damage the projectile will inflict
     * @param damageRadius the radius of damage the projectile will inflict
     */
    public Projectile(double x, double y, double angle, double speed, int damage, int damageRadius, Image imageName) {
        super(x - 10, y - 10, 20, 20, null, null);
        this.startX = x;
        this.startY = y;
        this.angle = angle;
        this.speed = speed;
        this.damage = damage;
        this.damageRadius = damageRadius;
        this.image = imageName;
    }

    /**
     * Constructor for Projectile object with image loading
     * Precondition: x and y are valid coordinates within the game window
     * Postcondition: A Projectile object is created at the specified coordinates with given angle and damage
     * @param x the x-coordinate of the projectile
     * @param y the y-coordinate of the projectile
     * @param angle the angle at which the projectile is fired
     * @param damage the damage the projectile will inflict
     * @param imageName the filename of the image representing the projectile
     */
    public Projectile(double x, double y, double angle, double speed, int damage, int damageRadius, String imageName) {
        super(x - 22, y - 22, 45, 45, null, null);
        this.startX = x;
        this.startY = y;
        this.angle = angle;
        this.speed = speed;
        this.damage = damage;
        this.damageRadius = 0;
        
        //************* Load Projectile Image *************//
        try {
            if (imageName != null) {
                this.image = ImageIO.read(getClass().getResource("/assets/images/player/" + imageName)); 
            }
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            System.out.println("Projectile.java - Error loading projectile image: " + imageName);
        }
    }

    /**
     * Updates the projectile's position and lifespan
     * Precondition: N/A
     * Postcondition: The projectile's position is updated based on its speed and angle, and its lifespan is decremented
     */
    @Override
    public void update() {
        if (!active) {
            return;
        }
        
        //************* Update Projectile Position *************//
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;

        //************* Projectile Range Check *************//
        // Calculate distance from projectile center to spawn point using Pythagorean theorem
        double dx = x + (width / 2) - startX;
        double dy = y + (height / 2) - startY;
        double distance = Math.sqrt((dx * dx) + (dy * dy)); 

        if (distance > MAX_RANGE) {
            active = false;
        }
    }

    /**
     * Gets the projectile's active status
     * Precondition: N/A
     * Postcondition: The projectile's active status is returned
     * @return the active status of the projectile
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Sets the projectile's active status
     * Precondition: N/A
     * Postcondition: The projectile's active status is updated
     * @param active the new active status of the projectile
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the damage value of the projectile
     * Precondition: N/A
     * Postcondition: The damage value of the projectile is returned
     * @return the damage value of the projectile
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Gets the damage radius of the projectile
     * Precondition: N/A
     * Postcondition: The damage radius of the projectile is returned
     * @return the damage radius of the projectile
     */
    public double getDamageRadius() {
        return damageRadius;
    }

    /**
     * Draws the projectile on the provided Graphics2D context
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The projectile is drawn at its current position and angle
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (!active || image == null) {
            return;
        }
        AffineTransform old = g2d.getTransform();
        g2d.translate(x + width / 2, y + height / 2);
        g2d.rotate(angle+ Math.PI / 2);
        g2d.drawImage(image, -width / 2, -height / 2, width, height, null);
        g2d.setTransform(old);
    }

    /**
     * Gets the bounding rectangle of the projectile for hit detection
     * Precondition: N/A
     * Postcondition: returns Rectangle of projectile's hitbox
     * @return the Rectangle representing the projectile's hitbox
     */
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
}