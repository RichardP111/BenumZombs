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
    private final double[] attackSpeeds = {2, 2, 2, 2, 2, 2, 2};

    public Bow(boolean unlocked) {
        super("Bow", unlocked);
    }

    @Override
    public void use(){
    }

    @Override
    public String getDescription() {
        return "Ranged weapon with high damage";
    }

    @Override
    public boolean isRanged() {
        return true;
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

    @Override
    public void draw(Graphics2D g2d, int x, int y, double angle, double scale) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(angle);
        g2d.scale(scale, scale);

        Color stringColor = new Color(252, 200, 117);

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
        
        g2d.setColor(new Color(135, 95, 69));
        g2d.fillRoundRect(-5, -70, 4, 70, 2, 2);

        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(-5, -70, 4, 70, 2, 2);

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

        g2d.setColor(stringColor);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(0, -12, -35, -14);
        g2d.drawLine(0, -12, 35, -14);

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

    public void draw(Graphics2D g2d, int x, int y, double angle) {
        draw(g2d, x, y, angle, 1.0);
    }
}
