/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Head-Up Display System shown during gameplay
 */

package systems;

import game.BenumZombsGame;
import helpers.FontManager;
import java.awt.*;
import objects.Player;
import objects.Tools.Tool;

public class HeadUpDisplay {
    private final Player player;
    private float time = 0.0f;
    private final float dayLength = 0.00005f; // Speed of day-night cycle

    /**
     * Constructor for HeadUpDisplay
     * Precondition: player is a valid Player object
     * Postcondition: HeadUpDisplay is created for the player
     * @param player
     */
    public HeadUpDisplay(Player player) {
        this.player = player;
    }

    /**
     * Keeps track of time for day-night cycle
     * Precondition: N/A
     * Postcondition: time is updated for day-night cycle
     */
    public void update() {
        time += dayLength;
        if (time > 1.0f){
            time = 0.0f;
        }
    }

    /**
     * Gets the current time value for day-night cycle
     * Precondition: N/A
     * Postcondition: returns time value between 0.0 and 1.0
     * @return
     */
    public float getTime() {
        return time;
    }

    /**
     * Draws the HUD elements on the screen
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: HUD elements are drawn on the screen
     * @param g2d
     * @param screenW
     * @param screenH
     */
    public void draw(Graphics2D g2d, int screenW, int screenH, ToolSystem toolSystem) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawTimeBar(g2d, screenW, screenH);
        drawMinimap(g2d, screenW, screenH);
        drawResourcePanel(g2d, screenW, screenH);
        drawHealthBar(g2d, screenW, screenH);
        drawToolbars(g2d, screenW, screenH, toolSystem);
    }

    /**
     * Draws the resource panel on the HUD
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: resource panel is drawn on the HUD
     * @param g2d
     * @param screenW
     * @param screenH
     */
    public void drawResourcePanel(Graphics2D g2d, int screenW, int screenH) {
        //************* Resource Panel Size *************//
        int resourcePanelW = 240, resourcePanelH = 130;
        int resourcePanelX = screenW - resourcePanelW - 20;
        int resourcePanelY = screenH - resourcePanelH - 70;

        //************* Resource Panel Background *************//
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect(resourcePanelX, resourcePanelY, resourcePanelW, resourcePanelH, 20, 20);

        //************* Resource Panel Text *************//
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 16f));
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawString("WOOD: ", resourcePanelX + 15, resourcePanelY + 30);
        g2d.drawString("STONE: ", resourcePanelX + (resourcePanelW / 2) + 15, resourcePanelY + 30);
        g2d.drawString("GOLD: ", resourcePanelX + 15, resourcePanelY + 70);
        g2d.drawString("TOKENS: ", resourcePanelX + (resourcePanelW / 2) + 15, resourcePanelY + 70);
        g2d.drawString("WAVE: ", resourcePanelX + 15, resourcePanelY + 110);

        //************* Resource Panel Values *************//
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 16f));
        g2d.setColor(Color.WHITE);
        g2d.drawString("0", resourcePanelX + (resourcePanelW / 2) - 20, resourcePanelY + 30); 
        g2d.drawString("1", resourcePanelX + (resourcePanelW - 20), resourcePanelY + 30);
        g2d.drawString("2", resourcePanelX + (resourcePanelW / 2) - 20, resourcePanelY + 70);
        g2d.drawString("3", resourcePanelX + (resourcePanelW - 20), resourcePanelY + 70);
        g2d.drawString("--", resourcePanelX + (resourcePanelW - 20), resourcePanelY + 110);

        //************* Resource Panel Dividers *************//
        g2d.setColor(new Color(105, 140, 65, 100));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(resourcePanelX + (resourcePanelW / 2), resourcePanelY, resourcePanelX + (resourcePanelW / 2), resourcePanelY + 50);
        g2d.drawLine(resourcePanelX, resourcePanelY + 45, resourcePanelX + resourcePanelW, resourcePanelY + 45);
        g2d.drawLine(resourcePanelX, resourcePanelY + 85, resourcePanelX + resourcePanelW, resourcePanelY + 85);
        g2d.drawLine(resourcePanelX + (resourcePanelW / 2), resourcePanelY + 20, resourcePanelX + (resourcePanelW / 2), resourcePanelY + 85);
    }

    /**
     * Draws the health bar on the HUD
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: health bar is drawn on the HUD
     * @param g2d
     * @param screenW
     * @param screenH
     */
    public void drawHealthBar(Graphics2D g2d, int screenW, int screenH) {
        //************* Health Bar Size and Position *************//
        int healthBarW = 240, healthBarH = 40;
        int healthBarX = screenW - healthBarW - 20;
        int healthBarY = screenH - 55;

        //************* Health Bar Background *************//
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect(healthBarX, healthBarY, healthBarW, healthBarH, 10, 10);

        //************* Health Bar Fill *************//
        int innerX = healthBarX + 5, innerY = healthBarY + 5;
        int innerW = healthBarW - 10, innerH = healthBarH - 10;
        
        g2d.setColor(new Color(20, 40, 15, 100));
        g2d.fillRoundRect(innerX, innerY, innerW, innerH, 5, 5);

        g2d.setColor(new Color(100, 161, 10));
        double healthPercent = (double) player.getCurrentHealth() / player.getMaxHealth();
        g2d.fillRoundRect(innerX, innerY, (int)(innerW * healthPercent), innerH, 5, 5);

        //************* Health Bar Label *************//
        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 16f));
        g2d.drawString("HEALTH", healthBarX + 10, healthBarY + (healthBarH / 2) + 6);
    }

    /**
     * Draws the time bar on the HUD
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: time bar is drawn on the HUD
     * @param g2d
     * @param screenW
     * @param screenH
     */
    public void drawTimeBar(Graphics2D g2d, int screenW, int screenH) {
        //************* Time Bar Size and Position *************//
        int barW = 150, barH = 20;
        int barX = 20, barY = screenH - 200;

        int offset = (int)(time * barW);

        Shape oldClip = g2d.getClip(); // Save current clip

        g2d.setClip(new java.awt.geom.RoundRectangle2D.Float(barX, barY, barW, barH, 15, 15));

        //************* Time Bar Fill *************//
        g2d.setColor(new Color(132, 115, 212, 180)); // Purple
        g2d.fillRect(barX - offset, barY, barW / 2, barH);
        
        g2d.setColor(new Color(214, 171, 53, 180)); // Yellow
        g2d.fillRect(barX + (barW / 2) - offset, barY, barW / 2, barH);
        
        g2d.setColor(new Color(132, 115, 212, 180)); // Purple
        g2d.fillRect(barX + barW - offset, barY, barW / 2, barH);
        
        g2d.setColor(new Color(214, 171, 53, 180));  // Yellow
        g2d.fillRect(barX + (int)(barW * 1.5) - offset, barY, barW / 2, barH);

        g2d.setClip(oldClip);

        //************* Time Bar Pointer *************//
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawLine(barX + (barW / 2), barY, barX + (barW / 2), barY + barH);

        //************* Time Bar Border *************//
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawRoundRect(barX, barY, barW, barH, 15, 15);
    }

    /**
     * Draws the minimap on the HUD
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: minimap is drawn on the HUD
     * @param g2d
     * @param screenW
     * @param screenH
     */
    public void drawMinimap(Graphics2D g2d, int screenW, int screenH) {
        //************* Minimap Size and Position *************//
        int miniSize = 150;
        int miniX = 20, miniY = screenH - miniSize - 15;

        //************* Minimap Background *************//
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect(miniX, miniY, miniSize, miniSize, 15, 15);

        //************* Minimap World Area *************//
        int walkableArea = BenumZombsGame.PLAY_AREA - (BenumZombsGame.BORDER_THICKNESS * 2);

        double relativeX = (player.getCenterX() - (BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS)) / (double)walkableArea;
        double relativeY = (player.getCenterY() - (BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS)) / (double)walkableArea;

        int dotX = miniX + (int)(relativeX * miniSize);
        int dotY = miniY + (int)(relativeY * miniSize);

        //************* Minimap Player Dot *************//
        g2d.setColor(new Color(132, 115, 212)); 
        g2d.fillOval(dotX - 3, dotY - 3, 6, 6);
    }

    /**
     * Draws the toolbars on the HUD
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: toolbars are drawn on the HUD
     * @param g2d
     * @param screenW
     * @param screenH
     * @param toolSystem
     */
    public void drawToolbars(Graphics2D g2d, int screenW, int screenH, ToolSystem toolSystem) {
        int slotSize = 50;
        int slotPadding = 10;

        int totalWidth = (4 * slotSize) + (3 * slotPadding);
        int toolBarX = (screenW - totalWidth) / 2;
        int toolBarY = screenH - 140;

        for (int i = 0; i < 4; i++){
            int x = toolBarX + i * (slotSize + slotPadding);
            g2d.setColor(new Color(247, 247, 247, 20));
            if (toolSystem.getActiveTool() == toolSystem.getToolInSlot(i)){
                g2d.setColor(new Color(247, 247, 247, 50));
            }
            g2d.fillRoundRect(x, toolBarY, slotSize, slotSize, 10, 10);

            Tool tool = toolSystem.getToolInSlot(i);
            if (tool != null && tool.getIsUnlocked()){
                tool.draw(g2d, x + slotSize / 2 - 10, toolBarY + slotSize / 2 + 10, 0.8, 0.5);
            }
        }

        int buildingBarW = (10 * slotSize) + (9 * slotPadding);
        int buildingBarX = (screenW - buildingBarW) / 2;
        int buildingBarY = screenH - 70;

        for (int i = 0; i < 10; i++){
            int x = buildingBarX + i * (slotSize + slotPadding);

            g2d.setColor(new Color(0, 0, 0, 30));
            g2d.fillRoundRect(x, buildingBarY, slotSize, slotSize, 10, 10);
        }

    }
}
