/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Game Object Base Class
 */

package objects;

import java.awt.*;

public abstract class GameObject {
    protected double x, y;
    protected int width, height;
    protected Color color;

    /**
     * Constructor for GameObject
     * Precondition: width and height are positive integers
     * Postcondition: GameObject is created
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color
     */
    public GameObject(double x, double y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2d);

    /**
     * Get the bounding rectangle of the GameObject
     * Precondition: none
     * Postcondition: Rectangle is returned
     * @return
     */
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
}