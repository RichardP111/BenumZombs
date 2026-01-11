/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Tree Class which defines tree objects
 * Based on Rounded Shapes by frankie zafe (https://openprocessing.org/sketch/17050#)
 */

package objects;

import java.awt.*;
import java.awt.geom.Path2D;

public class Tree {
    private final int x;
    private final int y;
    private final int size = 140;

    public Tree (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        int centerX = x + size / 2;
        int centerY = y + size / 2;
        float roundness = 0.2f;

        float bigOuterRadius = size * 0.45f;
        float bigInnerRadius = size * 0.35f;
        float smallOuterRadius = size * 0.20f;
        float smallInnerRadius = size * 0.15f;

        drawRoundedStar(g2d, centerX, centerY, bigOuterRadius, bigInnerRadius, new Color(78, 100, 55), roundness, true);
        drawRoundedStar(g2d, centerX, centerY, smallOuterRadius, smallInnerRadius, new Color(88, 123, 57), roundness, false);
    }

    public void drawRoundedStar(Graphics2D g2d, int centerX, int centerY, float outerRadius, float innerRadius, Color color, float roundness, boolean outline){

        Path2D star = new Path2D.Double();
        int numPoints = 7;
        int totalPoints = numPoints * 2;
        double[][] points = new double[totalPoints][2];

        for (int i = 0; i < totalPoints; i++) {
            double angle = (Math.PI * 2 / totalPoints) * i;
            float radius;

            if (i % 2 == 0){
                radius = outerRadius;
            } else {
                radius = innerRadius;
            }

            points[i][0] = centerX + Math.cos(angle) * radius;
            points[i][1] = centerY + Math.sin(angle) * radius;
        }

        star.moveTo(points[0][0], points[0][1]);
        for (int i = 0; i < totalPoints; i++){
            int nextPointIndex = (i + 1) % totalPoints;
            int prevPointIndex = (i - 1 + totalPoints) % totalPoints;

            double point1X = points[i][0] + (points[i][0] - points[prevPointIndex][0]) * roundness;
            double point1Y = points[i][1] + (points[i][1] - points[prevPointIndex][1]) * roundness;
            double point2X = points[nextPointIndex][0] - (points[nextPointIndex][0] - points[i][0]) * roundness;
            double point2Y = points[nextPointIndex][1] - (points[nextPointIndex][1] - points[i][1]) * roundness;

            star.curveTo(point1X, point1Y, point2X, point2Y, points[nextPointIndex][0], points[nextPointIndex][1]);
        }
        star.closePath();

        g2d.setColor(color);
        g2d.fill(star);

        if (outline){
            g2d.setColor(new Color(47, 46, 51));
            g2d.setStroke(new BasicStroke(6));
            g2d.draw(star);
        }
    } 

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
