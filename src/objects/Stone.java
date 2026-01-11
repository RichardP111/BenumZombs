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
    private final int size = 140;

    public Stone (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        int centerX = x + size / 2;
        int centerY = y + size / 2;
        float roundness = 0.4f;
        float bigRadius = size / 2f;
        float smallRadius = size / 4;

        drawRoundedHexagon(g2d, centerX, centerY, bigRadius, new Color(179, 179, 179), roundness);
        drawRoundedHexagon(g2d, centerX, centerY, smallRadius, new Color(202, 202, 202), roundness);
    }

    public void drawRoundedHexagon(Graphics2D g2d, int centerX, int centerY, float radius, Color color, float roundness){

        Path2D hexagon = new Path2D.Double();
        int numFaces = 6;
        double[][] points = new double[numFaces][2];

        for (int i = 0; i < numFaces; i++) {
            double angleOne = (Math.PI * 2 / numFaces) * i;

            points[i][0] = centerX + Math.cos(angleOne) * radius;
            points[i][1] = centerY + Math.sin(angleOne) * radius;
        }

        hexagon.moveTo(points[0][0], points[0][1]);
        for (int i = 0; i < numFaces; i++){
            int nextPointIndex = (i + 1) % numFaces;
            int prevPointIndex = (i - 1 + numFaces) % numFaces;

            double point1X = points[i][0] - (points[i][0] - points[prevPointIndex][0]) * roundness;
            double point1Y = points[i][1] - (points[i][1] - points[prevPointIndex][1]) * roundness;
            double point2X = points[i][0] + (points[nextPointIndex][0] - points[i][0]) * roundness;
            double point2Y = points[i][1] + (points[nextPointIndex][1] - points[i][1]) * roundness;

            hexagon.curveTo(point1X, point1Y, point2X, point2Y, points[nextPointIndex][0], points[nextPointIndex][1]);
        }
        hexagon.closePath();

        g2d.setColor(color);
        g2d.fill(hexagon);

        g2d.setColor(new Color(47, 46, 51));
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(hexagon);
    } 

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}

