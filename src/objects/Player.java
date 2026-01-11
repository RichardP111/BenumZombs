/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Player Class
 */

package objects;

import helpers.FontManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Player extends GameObject {

    private final double speed = 30; //player speed
    private final String name;

    private final int maxHealth = 100;
    private int currentHealth = 100;

    /**
     * Constructor for Player
     * Precondition: x, y are within game world bounds
     * Postcondition: Player object is created at (x, y) with name
     * @param x
     * @param y
     * @param name
     */
    public Player(double x, double y, String name) {
        super(x, y, 50, 50, new Color(252, 200, 117));
        this.name = name;
    }

    /**
     * Gets the center X coordinate of the player
     * Precondition: N/A
     * Postcondition: returns center X coordinate
     * @return
     */
    public double getCenterX() { 
        return x + width / 2.0; 
    }

    /**
     * Gets the center Y coordinate of the player
     * Precondition: N/A
     * Postcondition: returns center Y coordinate
     * @return
     */
    public double getCenterY() { 
        return y + height / 2.0; 
    }

    /**
     * Gets the width of the player
     * Precondition: N/A
     * Postcondition: returns width
     * @return
     */
    public int getWidth() { 
        return width; 
    }

    /**
     * Gets the height of the player
     * Precondition: N/A
     * Postcondition: returns height
     * @return
     */
    public int getHeight() { 
        return height; 
    }

    /**
     * Gets the maximum health of the player
     * Precondition: N/A
     * Postcondition: returns max health
     * @return
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Gets the current health of the player
     * Precondition: N/A
     * Postcondition: returns current health
     * @return
     */ 
    public int getCurrentHealth() {
        return currentHealth;
    }


    /**
     * Moves the player based on input directions and world bounds
     * Precondition: minX, maxX, minY, maxY define valid movement bounds
     * Postcondition: player position is updated within bounds
     * @param up
     * @param down
     * @param left
     * @param right
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    public void move(boolean up, boolean down, boolean left, boolean right, int minX, int maxX, int minY, int maxY) {
        if (up && y > minY){
            y -= speed;
        }
        if (down && y < maxY){
            y += speed;
        }  
        if (left && x > minX){
            x -= speed;
        }
        if (right && x < maxX){
            x += speed;
        }
    }

    /**
     * Reduces the player's health by the specified amount
     * Precondition: amount is non-negative
     * Postcondition: currentHealth is decreased by amount, not below 0
     * @param amount
     */
    public void takeDamage(int amount) {
        currentHealth = Math.max(0, currentHealth - amount);
    }

    /**
     * Updates the player's state
     * Precondition: N/A
     * Postcondition: N/A
     */
    @Override
    public void update() {}

    /**
     * Draws the player at specified screen coordinates, rotated to face mouse position
     * Precondition: g2d is a valid Graphics2D object, screenX/Y are the top-left screen coordinates to draw the player, mouseX/Y are the current mouse coordinates
     * Postcondition: player is drawn on screen facing mouse position
     * @param g2d
     * @param screenX
     * @param screenY
     * @param mouseX
     * @param mouseY
     */
    public void drawAt(Graphics2D g2d, int screenX, int screenY, int mouseX, int mouseY) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //************* Player Rotation *************//
        double centerX = screenX + width / 2.0;
        double centerY = screenY + height / 2.0;
        double angle = Math.atan2(mouseY - centerY, mouseX - centerX);

        java.awt.geom.AffineTransform oldTransform = g2d.getTransform(); // Save current transformation 

        g2d.rotate(angle - Math.PI / 2, centerX, centerY);

        //************* Player Arms *************//
        g2d.setColor(color);
        g2d.fillOval(screenX + 3, screenY + 38, 16, 16); 
        g2d.fillOval(screenX + width - 19, screenY + 38, 16, 16);
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawOval(screenX + 3, screenY + 38, 16, 16);
        g2d.drawOval(screenX + width - 19, screenY + 38, 16, 16);

        //************* Player Body *************//
        g2d.setColor(color);
        g2d.fillOval(screenX, screenY, width, height); 
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3f)); 
        g2d.drawOval(screenX, screenY, width, height);

        g2d.setTransform(oldTransform); // Restore original transformation for player name and health bar

        //************* Player Name Label *************//
        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD,17));
        FontMetrics fm = g2d.getFontMetrics();
        int nameX = screenX + (width - fm.stringWidth(name)) / 2;
        g2d.drawString(name, nameX, screenY - 20);

        //************* Local Player Health *************//
        helpers.HealthManager.drawStatusBar(g2d, currentHealth, maxHealth, screenX, screenY, width, height, new Color(113, 191, 71));
    }

    /**
     * Draws the player at its current position without rotation
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: player is drawn on screen at its (x, y) position
     */
    @Override
    public void draw(Graphics2D g2d) {
        drawAt(g2d, (int)x, (int)y, 0, 0);
    }
}
