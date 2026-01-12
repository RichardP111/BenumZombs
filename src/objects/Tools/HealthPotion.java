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
import java.awt.geom.Path2D;

public class HealthPotion extends Tool {
    private Color headColor;

    public HealthPotion(boolean unlocked) {
        super("HealthPotion", unlocked);
    }

    @Override
    public void use(){
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, double angle, double scale) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(angle);
        g2d.scale(scale, scale);

        headColor = switch (level) {
            case 2 -> new Color(198, 156, 109);
            case 3 -> new Color(204, 204, 204);
            case 4 -> new Color(250, 176, 59);
            case 5 -> new Color(100, 185, 237);
            case 6 -> new Color(186, 54, 63);
            case 7 -> new Color(186, 85, 211);
            default -> new Color(65, 241, 131);
        };

        g2d.setColor(new Color(135, 95, 693));
        g2d.fillRoundRect(-2, -20, 4, 40, 2, 2);

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
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(-2, -20, 4, 40, 2, 2);
        g2d.draw(head);

        g2d.setTransform(oldTransform);
    }

    public void draw(Graphics2D g2d, int x, int y, double angle) {
        draw(g2d, x, y, angle, 1.0);
    }
}
