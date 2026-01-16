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

    public Projectile(double x, double y, double angle, double damage) {
        super(x, y, 10, 3, Color.WHITE);
        this.angle = angle;
        this.speed = 15.0;
        this.damage = damage;
        this.time = 100;
    }

    @Override
    public void update() {
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
        
        time--;
    }

    public boolean isDead() {
        return time <= 0;
    }

    public double getDamage() {
        return damage;
    }

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