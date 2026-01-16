/**
 * Spear.java
 * The Spear tool object for BenumZombs, used to attack 
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-11
 */

package objects.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Spear extends Tool {
    private Color headColor;

    private final int[] costs = {1400, 2800, 5600, 11200, 22500, 45000, 90000};
    private final double[] damageValues = {20, 80, 120, 300, 2000, 8000, 10000};
    private final double[] attackSpeeds = {4, 4, 4, 4, 4, 4, 4};

    /**
     * Constructor for Spear
     * Precondition: N/A
     * Postcondition: Spear tool object is created
     * @param unlocked
     */
    public Spear(boolean unlocked) {
        super("Spear", unlocked);
    }

    @Override
    public void use(){
    }

    @Override
    public String getDescription() {
        return "Meelee weapon with high attack speed";
    }

    @Override
    public int getUpgradeCost() {
        if (level >= 7){
            return -1;
        }
        return costs[level]; 
    }

    @Override
    public double getDamage() {
        return damageValues[level - 1];
    }

    @Override
    public double getAttackSpeed() {
        return attackSpeeds[level - 1];
    }

    /**
     * Draws the spear at the specified position, angle, and scale
     * Precondition: g2d is a valid Graphics2D object, x and y are valid coordinates, angle is a valid rotation angle, scale is a positive scaling factor
     * Postcondition: Spear is drawn on the Graphics2D context at the specified position, angle, and scale
     * @param g2d
     * @param x
     * @param y
     */
    @Override
    public void draw(Graphics2D g2d, int x, int y, double angle, double scale) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(angle);
        g2d.scale(scale, scale);

        switch (level) {
            case 1:
                headColor = new Color(102, 102, 102);
                break;
            case 2:
                headColor = new Color(198, 156, 109);
                break;
            case 3:
                headColor = new Color(204, 204, 204);
                break;
            case 4:
                headColor = new Color(250, 176, 59);
                break;
            case 5:
                headColor = new Color(100, 185, 237);
                break;
            case 6:
                headColor = new Color(186, 54, 63);
                break;
            case 7:
                headColor = new Color(65, 242, 131);
                break;
            default:
                headColor = new Color(102, 102, 102);
        }

        g2d.setColor(new Color(135, 95, 69));
        g2d.fillRoundRect(-2, -65, 4, 80, 2, 2);

        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(-2, -65, 4, 80, 2, 2);

        Path2D head = new Path2D.Double();
        head.moveTo(-5, -50);
        head.lineTo(-5, -80);
        head.lineTo(0, -85);
        head.lineTo(5, -80);
        head.lineTo(5, -50);
        head.lineTo(0, -45);
        head.closePath();

        g2d.setColor(headColor);
        g2d.fillRoundRect(-12, -50, 25, 8, 2, 2);

        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(-12, -50, 25, 8, 2, 2);

        g2d.setColor(headColor);
        g2d.fill(head);

        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(head);

        g2d.setTransform(oldTransform);
    }

    public void draw(Graphics2D g2d, int x, int y, double angle) {
        draw(g2d, x, y, angle, 1.0);
    }
}
