/**
 * Pickaxe.java
 * The Pickaxe tool object for BenumZombs, used to mine resources
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-11
 */

package objects.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Pickaxe extends Tool {
    private Color headColor;

    private final int[] costs = {0, 1000, 3000, 6000, 8000, 24000, 90000};
    private final double[] harvestValues = {1.5, 3.0, 3.0, 4.5, 4.5, 6.0, 9.0};
    private final double[] attackSpeeds = {3.33, 3.33, 3.51, 4.0, 5.0, 5.0, 5.0};

    /**
     * Constructor for Pickaxe
     * Precondition: N/A
     * Postcondition: Pickaxe tool object is created
     * @param unlocked
     */
    public Pickaxe(boolean unlocked) {
        super("Pickaxe", unlocked);
    }

    @Override
    public void use(){
    }

    @Override
    public int getUpgradeCost() {
        if (level >= 7){
            return -1;
        }
        return costs[level]; 
    }

    public double getHarvestPower() {
        return harvestValues[level - 1];
    }

    public double getAttackSpeed() {
        return attackSpeeds[level - 1];
    }

    /**
     * Draws the pickaxe at the specified position, angle, and scale
     * Precondition: g2d is a valid Graphics2D object, x and y are valid coordinates, angle is a valid rotation angle, scale is a positive scaling factor
     * Postcondition: Pickaxe is drawn on the Graphics2D context at the specified position, angle, and scale
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
        };

        g2d.setColor(new Color(135, 95, 69));
        g2d.fillRoundRect(-2, -55, 4, 75, 2, 2); 
        
        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(-2, -55, 4, 75, 2, 2);

        Path2D head = new Path2D.Double();
        head.moveTo(-14, -45);
        head.lineTo(10, -50);
        head.lineTo(11, -60);
        head.lineTo(-10, -70);
        head.lineTo(-25, -60);
        head.closePath();

        g2d.setColor(headColor);
        g2d.fill(head);

        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(head);

        g2d.setTransform(oldTransform);
    }

    public void draw(Graphics2D g2d, int x, int y, double angle) {
        draw(g2d, x, y, angle, 0.9);
    }
}
