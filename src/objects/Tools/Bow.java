/**
 * Bow.java
 * The Bow tool object for BenumZombs, used to attack from a distance
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-11
 */

package objects.Tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Bow extends Tool {
    private Color bowColor;

    private final int[] costs = {100, 400, 2000, 7000, 24000, 30000, 90000};
    private final double[] damageValues = {20, 40, 100, 300, 2400, 10000, 14000};
    private final double[] attackSpeeds = {30, 30, 30, 40, 40, 40, 60};

    /**
     * Constructor for Bow
     * Precondition: N/A
     * Postcondition: Bow tool object is created
     * @param unlocked whether the bow is unlocked
     */
    public Bow(boolean unlocked) {
        super("Bow", unlocked);
        this.description = "Ranged weapon with high damage";
    }

    /**
     * Gets the description of the bow
     * Precondition: N/A
     * Postcondition: returns the description of the bow
     * @return the description of the bow
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the bow is a ranged weapon
     * Precondition: N/A
     * Postcondition: returns true since bow is ranged
     * @return true if the bow is ranged, false otherwise
     */
    @Override
    public boolean isRanged() {
        return true;
    }

    /**
     * Gets the upgrade cost of the bow
     * Precondition: N/A
     * Postcondition: returns the upgrade cost of the bow
     * @return the upgrade cost of the bow
     */
    @Override
    public int getUpgradeCost() {
        if (level >= 7) {
            return -1;
        }
        return costs[level];
    }

    /**
     * Gets the damage of the bow
     * Precondition: N/A
     * Postcondition: returns the damage of the bow
     * @return the damage of the bow
     */
    @Override
    public double getDamage() {
        return damageValues[level - 1];
    }

    /**
     * Gets the attack speed of the bow
     * Precondition: N/A
     * Postcondition: returns the attack speed of the bow
     * @return the attack speed of the bow
     */
    @Override
    public double getAttackSpeed() {
        return attackSpeeds[level - 1];
    }

    /**
     * Draws the bow at the specified position, angle, and scale
     * Precondition: g2d is a valid Graphics2D object, x and y are valid coordinates, angle is a valid rotation angle, scale is a positive scaling factor
     * Postcondition: Bow is drawn on the Graphics2D context at the specified position, angle, and scale
     * @param g2d the Graphics2D context to draw on
     * @param x the x-coordinate to draw the bow
     * @param y the y-coordinate to draw the bow
     * @param angle the rotation angle of the bow
     * @param scale the scaling factor for the bow
     */
    @Override
    public void draw(Graphics2D g2d, int x, int y, double angle, double scale) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(angle);
        g2d.scale(scale, scale);

        Color stringColor = new Color(252, 200, 117);

        //************* Set Bow Color*************//
        switch (level) {
            case 1:
                bowColor = new Color(102, 102, 102);
                break;
            case 2:
                bowColor = new Color(198, 156, 109);
                break;
            case 3:
                bowColor = new Color(204, 204, 204);
                break;
            case 4:
                bowColor = new Color(250, 176, 59);
                break;
            case 5:
                bowColor = new Color(100, 185, 237);
                break;
            case 6:
                bowColor = new Color(186, 54, 63);
                break;
            case 7:
                bowColor = new Color(65, 242, 131);
                break;
            default:
                bowColor = new Color(102, 102, 102);
        }
        
        //************* Draw Arrow *************//
        g2d.setColor(new Color(135, 95, 69));
        g2d.fillRoundRect(-5, -70, 4, 70, 2, 2);

        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(-5, -70, 4, 70, 2, 2);

        //************* Draw Arrow Head *************//
        Path2D head = new Path2D.Double();
        head.moveTo(-11, -60);
        head.lineTo(-4, -75);
        head.lineTo(3, -60);
        head.closePath();

        g2d.setColor(bowColor);
        g2d.fill(head);

        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(head);

        //************* Draw Bow String *************//
        g2d.setColor(stringColor);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(0, -12, -35, -14);
        g2d.drawLine(0, -12, 35, -14);

        //************* Draw Bow *************//
        Path2D bow = new Path2D.Double();
        bow.moveTo(-35, -14);
        bow.quadTo(0, -60, 35, -14);      

        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(6.5f));
        g2d.draw(bow);

        g2d.setColor(bowColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(bow);
        
        g2d.setTransform(oldTransform);
    }

    /**
     * Draws the bow at the specified position and angle with default scale
     * Precondition: g2d is a valid Graphics2D object, x and y are valid coordinates, angle is a valid rotation angle
     * Postcondition: Bow is drawn on the Graphics2D context at the specified position and angle with default scale
     * @param g2d the Graphics2D context to draw on
     * @param x the x-coordinate to draw the bow
     * @param y the y-coordinate to draw the bow
     * @param angle the rotation angle of the bow
     */
    public void draw(Graphics2D g2d, int x, int y, double angle) {
        draw(g2d, x, y, angle, 1.0);
    }
}
