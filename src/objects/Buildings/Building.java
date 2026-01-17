/**
 * Building.java
 * The Building class for BenumZombs, defining building properties and behaviors
 * @author Richard Pu   
 * @version 1.0
 * @since 2026-01-16
 */

package objects.Buildings;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import objects.GameObject;

public abstract class Building extends GameObject {
    protected String name;
    protected String description;
    protected int level = 1;
    protected int cost;
    protected int maxHealth;
    protected int health;
    protected Image icon; 

    /**
     * Constructor for Building
     * Precondition: width and height are positive integers
     * Postcondition: Building is created
     * @param x the x-coordinate of the building
     * @param y the y-coordinate of the building
     * @param width the width of the building
     * @param height the height of the building
     * @param name the name of the building
     * @param iconName the filename of the building's icon image
     */
    public Building(double x, double y, int width, int height, String name, String iconName) {
        super(x, y, width, height, null); 
        this.name = name;
        this.health = 100;
        this.maxHealth = 100;
        
        try {
            if (iconName != null) {
                this.icon = ImageIO.read(getClass().getResource("/assets/images/buildings/toolbar/" + iconName));
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading building icon: " + iconName);
        }
    }

    /**
     * Gets the icon of the Building
     * Precondition: N/A
     * Postcondition: returns the icon of the Building
     * @return the icon of the Building
     */
    public Image getIcon() { 
        return icon; 
    }

    /**
     * Gets the name of the Building
     * Precondition: N/A
     * Postcondition: returns the name of the Building
     * @return the name of the Building
     */
    public String getName() { 
        return name; 
    }

    /**
     * Gets the level of the Building
     * Precondition: N/A
     * Postcondition: returns the level of the Building
     * @return the level of the Building
     */
    public int getLevel() { 
        return level; 
    }

    /**
     * Gets the cost of the Building
     * Precondition: N/A
     * Postcondition: returns the cost of the Building
     * @return the cost of the Building
     */
    public int getCost() { 
        return cost; 
    }

    @Override
    public abstract void draw(Graphics2D g2d); 

    public abstract String getDescription();  
}
