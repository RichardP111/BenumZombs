/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Pickaxe Class which defines pickaxe object
 */

package objects.Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Pickaxe extends Tool {
    private Color headColor;

    public Pickaxe(boolean unlocked) {
        super("Pickaxe", unlocked);
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
                headColor = new Color(65, 241, 131);
                break;
            default:
                headColor = new Color(102, 102, 102);
        }

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
