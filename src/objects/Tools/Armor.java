/**
 * Armor.java
 * The Armor tool object for BenumZombs, used to provide shield health
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-15
 */

package objects.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import objects.Player;

public class Armor extends Tool {
    private Color armorColor;

    private final int[] bonusHP = {500, 1000, 1800, 4000, 10000, 20000, 35000, 50000, 65000, 85000};
    private final int[] costs = {1000, 3000, 7000, 14000, 18000, 22000, 24000, 30000, 45000, 70000};

    public Armor(boolean unlocked) {
        super("Armor", unlocked);
        armorColor = new Color(64, 135, 172);
    }

    @Override
    public int getUpgradeCost() {
        if (level >= 10){
            return -1;
        }
        return costs[level - 1];
    }

    public int getBonusHP() {
        if (!isUnlocked){
            return 0;
        }
        return bonusHP[level - 1];
    }

    @Override
    public void use(){}

    @Override
    public void onGet(Player player) {
        player.fillShield();
    }
    
    @Override
    public void draw(Graphics2D g2d, int x, int y, double angle, double scale) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.scale(scale, scale);

        switch (level) {
            case 1:
                armorColor = new Color(61, 161, 217);
                break;
            case 2:
                armorColor = new Color(53, 150, 206);
                break;
            case 3:
                armorColor = new Color(43, 143, 201);
                break;
            case 4:
                armorColor = new Color(29, 123, 177);
                break;
            case 5:
                armorColor = new Color(20, 110, 162);
                break;
            case 6:
                armorColor = new Color(15, 90, 144);
                break;
            case 7:
                armorColor = new Color(12, 86, 128);
                break;
            case 8:
                armorColor = new Color(14, 99, 125);
                break;
            case 9:
                armorColor = new Color(16, 122, 140);
                break;
            case 10:
                armorColor = new Color(22, 128, 152);
                break;
            default:
                armorColor = new Color(61, 161, 217);
        }

        Path2D shield = new Path2D.Double();
        shield.moveTo(18, -60);
        shield.quadTo(29, -52, 41, -52);
        shield.quadTo(44, -22, 28, 1);
        shield.quadTo(18, 6, 8, 1);
        shield.quadTo(-8, -22, -5, -52);
        shield.quadTo(7, -52, 18, -60);
        shield.closePath();

        g2d.setColor(armorColor);
        g2d.fill(shield);
        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(shield);
        
        g2d.setTransform(oldTransform);
    }

    public void draw(Graphics2D g2d, int x, int y, double angle) {
        draw(g2d, x, y, angle, 1.0);
    }
}
