/**
 * HealthPotion.java
 * The HealthPotion tool object for BenumZombs, used to restore health
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-11
 */

package objects.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class HealthPotion extends Tool {
    private final Color potionColor;

    /**
     * Constructor for HealthPotion
     * Precondition: N/A
     * Postcondition: HealthPotion tool object is created
     * @param unlocked whether the health potion is unlocked
     */
    public HealthPotion(boolean unlocked) {
        super("Health Potion", unlocked);
        potionColor = new Color(200, 64, 49);
        this.description = "Heals your player to full health";
    }

    /**
     * Gets the description of the health potion
     * Precondition: N/A
     * Postcondition: returns the description of the health potion
     * @return the description of the health potion
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the upgrade cost of the health potion
     * Precondition: N/A
     * Postcondition: returns the upgrade cost of the health potion
     * @return the upgrade cost of the health potion
     */
    @Override
    public int getUpgradeCost() {
        return 100;
    }

    /**
     * Checks if the health potion is consumable
     * Precondition: N/A
     * Postcondition: returns true since health potion is consumable
     * @return true if the health potion is consumable, false otherwise
     */
    @Override
    public boolean isConsumable() {
        return true;
    }

    /**
     * Draws the health potion at the specified position, angle, and scale
     * Precondition: g2d is a valid Graphics2D object, x and y are valid coordinates, angle is a valid rotation angle, scale is a positive scaling factor
     * Postcondition: Health potion is drawn on the Graphics2D context at the specified position, angle, and scale
     * @param g2d the Graphics2D context to draw on
     * @param x the x-coordinate to draw the health potion
     * @param y the y-coordinate to draw the health potion
     * @param angle the rotation angle of the health potion
     * @param scale the scaling factor for the health potion
     */
    @Override
    public void draw(Graphics2D g2d, int x, int y, double angle, double scale) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(angle);
        g2d.scale(scale, scale);

        //************* Draw Potion Body *************//
        g2d.setColor(potionColor);
        g2d.fillRoundRect(-11, -63, 20, 15, 2, 2);
        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(-11, -63, 20, 15, 2, 2);

        //************* Draw Potion Neck *************//
        g2d.setColor(potionColor);
        g2d.fillOval(-25, -57, 50, 50);
        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(-25, -57, 50, 50);

        g2d.setTransform(oldTransform);
    }

    /**
     * Draws the health potion at the specified position and angle with default scale
     * Precondition: g2d is a valid Graphics2D object, x and y are valid coordinates, angle is a valid rotation angle
     * Postcondition: Health potion is drawn on the Graphics2D context at the specified position and angle with default scale
     * @param g2d the Graphics2D context to draw on
     * @param x the x-coordinate to draw the health potion
     * @param y the y-coordinate to draw the health potion
     * @param angle the rotation angle of the health potion
     */
    public void draw(Graphics2D g2d, int x, int y, double angle) {
        draw(g2d, x, y, angle, 1.0);
    }
}
