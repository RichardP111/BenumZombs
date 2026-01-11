/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Helper Class for Health Management which provides health bar below objects
 */

package helpers;

import java.awt.*;

public class HealthManager {

    /**
     * Draws a status bar (e.g., health bar) below an object.
     * Precondition: g2d is a valid Graphics2D object, current and max are non-negative, and width and height are the dimensions of the object.
     * Postcondition: A status bar is rendered below the specified object.
     * @param g2d
     * @param current
     * @param max
     * @param x
     * @param y
     * @param width
     * @param height
     * @param barColor
     */
    public static void drawStatusBar(Graphics2D g2d, double current, double max, int x, int y, int width, int height, Color barColor) {
        int barW = 50; 
        int barH = 6;
        int barX = x + (width / 2) - (barW / 2);
        int barY = y + height + 15;

        g2d.setColor(new Color(0, 0, 0, 160));
        g2d.fillRoundRect(barX, barY, barW, barH, 4, 4);

        double percent = Math.max(0, current / max);
        g2d.setColor(barColor);
        g2d.fillRoundRect(barX, barY, (int)(barW * percent), barH, 4, 4);
    }
}
