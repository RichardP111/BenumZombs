/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Stone Class which defines stone objects
 * Based on Rounded Shapes by frankie zafe (https://openprocessing.org/sketch/17050#)
 */

package objects;

import java.awt.*;
import java.awt.geom.Path2D;

public class Stone {
    private final int x;
    private final int y;
    private final int size = 140; //size of stone

    /**
     * Constructor for Stone object
     * Precondition: x and y are valid integers within the game window
     * Postcondition: X and Y coordinates of the stone are set
     * @param x
     * @param y
     */
    public Stone (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the stone object on the screen
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: Stone is drawn on the screen
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        int centerX = x + size / 2;
        int centerY = y + size / 2;
        float bigRadius = size * 0.45f;
        float smallRadius = size * 0.20f;
        
        float roundness = 0.2f;

        drawRoundedHexagon(g2d, centerX, centerY, bigRadius, new Color(179, 179, 179), roundness, true); //outer hexagon
        drawRoundedHexagon(g2d, centerX - 5, centerY + 5, smallRadius, new Color(202, 202, 202), roundness, false); //inner hexagon
    }

    /**
     * Draws a rounded hexagon shape
     * Precondition: g2d is a valid Graphics2D object, centerX and centerY are valid integers, radius is a positive float, color is a valid Color object, roundness is a float between 0 and 1, outline is a boolean
     * Postcondition: Rounded hexagon is drawn on the screen
     * @param g2d
     * @param centerX
     * @param centerY
     * @param radius
     * @param color
     * @param roundness
     * @param outline
     */
    public void drawRoundedHexagon(Graphics2D g2d, int centerX, int centerY, float radius, Color color, float roundness, boolean outline){

        Path2D hexagon = new Path2D.Double();
        int numFaces = 5;
        double[][] points = new double[numFaces][2];

        //************* Generate Points *************//
        for (int i = 0; i < numFaces; i++) {
            double angleOne = (Math.PI * 2 / numFaces) * i;

            points[i][0] = centerX + Math.cos(angleOne) * radius;
            points[i][1] = centerY + Math.sin(angleOne) * radius;
        }

        //************* Create Rounded Outline *************//
        hexagon.moveTo(points[0][0], points[0][1]);
        for (int i = 0; i < numFaces; i++){
            int nextPointIndex = (i + 1) % numFaces;
            int prevPointIndex = (i - 1 + numFaces) % numFaces;

            double point1X = points[i][0] + (points[i][0] - points[prevPointIndex][0]) * roundness;
            double point1Y = points[i][1] + (points[i][1] - points[prevPointIndex][1]) * roundness;
            double point2X = points[nextPointIndex][0] - (points[nextPointIndex][0] - points[i][0]) * roundness;
            double point2Y = points[nextPointIndex][1] - (points[nextPointIndex][1] - points[i][1]) * roundness;

            hexagon.curveTo(point1X, point1Y, point2X, point2Y, points[nextPointIndex][0], points[nextPointIndex][1]);
        }
        hexagon.closePath();

        g2d.setColor(color);
        g2d.fill(hexagon);

        //************* Draw Outline *************//
        if (outline){
            g2d.setColor(new Color(47, 46, 51));
            g2d.setStroke(new BasicStroke(6));
            g2d.draw(hexagon);
        }
    } 

    /**
     * Gets the bounds of the rectangle of the stone
     * Precondition: None
     * Postcondition: Rectangle of the stone is returned
     * @return
     */
    public Rectangle getBounds() {
        int buffer = size / 5;
        return new Rectangle(x + buffer, y + buffer, size - 2 * buffer, size - 2 * buffer);
    }
}

