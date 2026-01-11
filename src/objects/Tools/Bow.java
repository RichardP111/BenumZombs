/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Bow Class which defines bow object
 */

package objects.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Bow extends Tool {
    private Color headColor;

    public Bow(boolean unlocked) {
        super("Bow", unlocked);
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

        switch (level) {
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
                headColor = new Color(186, 85, 211);
                break;
            default:
                headColor = new Color(65, 241, 131);
        }

        g2d.setColor(new Color(135, 95, 693));
        g2d.fillRoundRect(-2, -20, 4, 40, 2, 2);

        Path2D head = new Path2D.Double();
        head.moveTo(-15, -20);
        head.quadTo(0, -10, 15, -20);
        head.lineTo(5, -12);
        head.quadTo(0, -8, -5, -12);
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
