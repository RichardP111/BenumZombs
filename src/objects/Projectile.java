/**
 * Projectile.java
 * The Projectile class for BenumZombs, defining projectile properties and behaviors
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-15
 */

package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Projectile extends GameObject {
    private final double angle;
    private final double speed;
    private final double damage;
    private int time;

    /**
     * Constructor for Projectile object
     * Precondition: x and y are valid coordinates within the game window
     * Postcondition: A Projectile object is created at the specified coordinates with given angle and damage
     * @param x the x-coordinate of the projectile
     * @param y the y-coordinate of the projectile
     * @param angle the angle at which the projectile is fired
     * @param damage the damage the projectile will inflict
     */
    public Projectile(double x, double y, double angle, double damage) {
        super(x, y, 10, 3, Color.WHITE);
        this.angle = angle;
        this.speed = 15.0;
        this.damage = damage;
        this.time = 100;
    }

    /**
     * Updates the projectile's position and lifespan
     * Precondition: N/A
     * Postcondition: The projectile's position is updated based on its speed and angle, and its lifespan is decremented
     */
    @Override
    public void update() {
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
        
        time--;
    }

    /**
     * Checks if the projectile's lifespan has ended
     * Precondition: N/A
     * Postcondition: Returns true if the projectile's time has run out, false otherwise
     * @return true if the projectile is dead, false otherwise
     */
    public boolean isDead() {
        return time <= 0;
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
     * Draws the projectile on the provided Graphics2D context
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: The projectile is drawn at its current position and angle
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform old = g2d.getTransform();
        g2d.translate(x, y);
        g2d.rotate(angle);
        
        g2d.setColor(new Color(135, 95, 69)); 
        g2d.fillRect(-10, -1, 20, 2);
        
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillPolygon(new int[]{10, 10, 14}, new int[]{-3, 3, 0}, 3);

        g2d.setTransform(old);
    }
}