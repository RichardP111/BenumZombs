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

    public HealthPotion(boolean unlocked) {
        super("Health Potion", unlocked);
        potionColor = new Color(200, 64, 49);
    }

    @Override
    public void use(){
    }

    @Override
    public String getDescription() {
        return "Heals your player to full health";
    }

    @Override
    public int getUpgradeCost() {
        return 100;
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, double angle, double scale) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(angle);
        g2d.scale(scale, scale);

        g2d.setColor(potionColor);
        g2d.fillRoundRect(-11, -63, 20, 15, 2, 2);
        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(-11, -63, 20, 15, 2, 2);

        g2d.setColor(potionColor);
        g2d.fillOval(-25, -57, 50, 50);
        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(-25, -57, 50, 50);

        g2d.setTransform(oldTransform);
    }

    public void draw(Graphics2D g2d, int x, int y, double angle) {
        draw(g2d, x, y, angle, 1.0);
    }
}
