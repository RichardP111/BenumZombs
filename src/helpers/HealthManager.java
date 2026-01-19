/**
 * HealthManager.java
 * Helper class for managing health bars in BenumZombs
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-08
 */

package helpers;

import java.awt.*;

public class HealthManager {

    /**
     * Draws a status bar (e.g., health bar) below an object.
     * Precondition: g2d is a valid Graphics2D object, current and max are non-negative, and width and height are the dimensions of the object.
     * Postcondition: A status bar is rendered below the specified object.
     * @param g2d the Graphics2D object to draw on
     * @param current the current value (e.g., current health)
     * @param max the maximum value (e.g., maximum health)
     * @param x the x-coordinate of the object
     * @param y the y-coordinate of the object
     * @param width the width of the object
     * @param height the height of the object
     * @param barColor the color of the status bar
     * @param armorBar whether the bar is an armor bar (true) or a health bar (false)
     */
    public static void drawStatusBar(Graphics2D g2d, double current, double max, int x, int y, int width, int height, Color barColor, boolean armorBar) {
        int barW = 50; 
        int barH = 6;
        int barX = x + (width / 2) - (barW / 2);
        int barY;

        if (armorBar) { // if drawing armor bar, position it below health bar
            barY = y + height + 25;
        } else { // health bar
            barY = y + height + 15;
        }

        g2d.setColor(new Color(0, 0, 0, 160));
        g2d.fillRoundRect(barX, barY, barW, barH, 4, 4);

        //************* Draw Filled Percentage *************//
        double percent = Math.max(0, current / max);
        g2d.setColor(barColor);
        g2d.fillRoundRect(barX, barY, (int)(barW * percent), barH, 4, 4);
    }
}
