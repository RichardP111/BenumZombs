/**
 * GameObject.java
 * The GameObject base class for BenumZombs, defining general game object properties and behaviors
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-07
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
     * @param x the x-coordinate of the game object
     * @param y the y-coordinate of the game object
     * @param width the width of the game object
     * @param height the height of the game object
     * @param color the color of the game object
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
}