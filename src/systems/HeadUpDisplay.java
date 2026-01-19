/**
 * HeadUpDisplay.java
 * The HeadUpDisplay class for BenumZombs, managing the game's HUD elements
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-08
 */

package systems;

import game.BenumZombsGame;
import game.Main;
import helpers.FontManager;
import helpers.RoundedJButton;
import helpers.SoundManager;
import helpers.TextFormatter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import objects.Buildings.Building;
import objects.Player;
import objects.Tools.Tool;

public class HeadUpDisplay {
    private final Player player;
    private float time = 0.0f;
    private final float dayLength = 0.00005f; // Speed of day-night cycle

    public Rectangle settingsButtonBounds;
    public Rectangle shopButtonBounds;
    private Image settingsIcon;
    private Image shopIcon;

    private Rectangle upgradeButtonBounds;
    private Rectangle sellButtonBounds;

    private final BenumZombsGame game;
    private final ToolSystem toolSystem;

    private boolean isDeathScreenVisible = false;
    private String deathMessage = "";
    private RoundedJButton respawnButton;

    public static int tutorialStep = 1;
    public static boolean tutorialActive = true;

    /**
     * Constructor for HeadUpDisplay
     * Precondition: player is a valid Player object
     * Postcondition: HeadUpDisplay is created for the player
     * @param player  the Player object for whom the HUD is created
     * @param game the main game object
     * @param toolSystem the ToolSystem object managing tools
     */
    public HeadUpDisplay(BenumZombsGame game, Player player, ToolSystem toolSystem) {
        this.game = game;
        this.player = player;
        this.toolSystem = toolSystem;

        initializeRespawnButton();
        loadIcons();
    }

    /**
     * Loads icons for the HUD
     * Precondition: N/A
     * Postcondition: icons are loaded for settings and shop buttons
     */
    private void loadIcons() {
        try {
            settingsIcon = ImageIO.read(getClass().getResource("/assets/images/settingIcon.png"));
            shopIcon = ImageIO.read(getClass().getResource("/assets/images/shopIcon.png"));
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            System.out.println("HeadUpDisplay.java - Your icons are very broken. Good luck!!!! :)))): " + e.getMessage());
        }
    }

    /**
     * Initializes the respawn button for the death screen
     * Precondition: N/A
     * Postcondition: respawn button is created and added to the game
     */
    @SuppressWarnings("Convert2Lambda")
    private void initializeRespawnButton() {
        //************* Respawn Button *************//
        respawnButton = new RoundedJButton("Respawn");
        respawnButton.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 24f));
        respawnButton.setBackground(new Color(103, 90, 166));
        respawnButton.setForeground(Color.WHITE);
        respawnButton.setFocusable(false); 
        respawnButton.setVisible(false);
        game.add(respawnButton);

        //************* Respawn Button Action *************//
        respawnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.playSound("buttonClick.wav");
                game.respawnPlayer(); 
            }
        });
    }

    /**
     * Handles mouse click events on the HUD
     * Precondition: point is a valid Point object representing the mouse click location
     * Postcondition: appropriate action is taken based on the clicked HUD element
     * @param point the Point object representing the mouse click location 
     * @return true if a HUD element was clicked and handled, false otherwise
     */
    public boolean handleMouseClick(Point point) {
        BuildingSystem buildingSystem = game.getBuildingSystem();
        Building building = buildingSystem.getSelectedBuilding();
        ResourceSystem resourceSystem = game.getResourceSystem();

        //************* Upgrade Button Handling *************//
        if (resourceSystem != null && building != null) {

            // Get stash level to determine upgrade eligibility
            Building stash = buildingSystem.getActiveStash();
            int stashLvl;
            if (stash != null) {
                stashLvl = stash.getLevel();
            } else {
                stashLvl = 1;
            }

            if (upgradeButtonBounds != null && upgradeButtonBounds.contains(point)) {
                if (building.canUpgrade(stashLvl)) {
                    int woodCost = building.getUpgradeWoodCost();
                    int stoneCost = building.getUpgradeStoneCost();
                    int goldCost = building.getUpgradeGoldCost();

                    // Check if player has enough resources
                    if (resourceSystem.canAfford(woodCost, stoneCost, goldCost)) { 
                        resourceSystem.addWood(-woodCost);
                        resourceSystem.addStone(-stoneCost);
                        resourceSystem.addGold(-goldCost); 

                        building.upgrade();
                        SoundManager.playSound("upgradeSound.wav");
                        System.out.println("HeadUpDisplay.java - Upgraded Building: " + building.getName());
                    }
                }
                return true;
            }
            
            //************* Sell Button Handling *************//
            if (sellButtonBounds != null && sellButtonBounds.contains(point)) {
                if (building.canBeSold()) {
                    resourceSystem.addWood(building.getWoodSellValue()); 
                    resourceSystem.addStone(building.getStoneSellValue());
                    buildingSystem.removeBuilding(building);
                    SoundManager.playSound("sellSound.wav");
                    System.out.println("HeadUpDisplay.java - Sold Building: " + building.getName());
                }
                return true;
            }
        }

        //************* Settings Button Handling *************//
        if (settingsButtonBounds != null && settingsButtonBounds.contains(point)) { 
            Main.settingsScreen.setGameInstance(game);
            Main.showScreen("SETTINGS");
            SoundManager.playSound("buttonClick.wav");
            System.out.println("HeadUpDisplay.java - Opened Settings Screen");
            return true;
        }

        //************* Shop Button Handling *************//
        if (shopButtonBounds != null && shopButtonBounds.contains(point)) { 
            Main.shopScreen.setGameInstance(game);
            Main.showScreen("SHOP");
            SoundManager.playSound("buttonClick.wav");
            System.out.println("HeadUpDisplay.java - Opened Shop Screen");
            return true;
        }

        //************* Toolbar Click Handling *************//
        return handleToolbarClick(point);
    }

    /**
     * Handles clicks on the toolbar
     * Precondition: p is a valid Point object representing the mouse click location
     * Postcondition: active tool is changed if a toolbar slot is clicked
     * @param point the Point object representing the mouse click location
     */
    public boolean handleToolbarClick(Point point) {
        //************* Toolbar Bounds *************//
        int width = game.getWidth();
        int height = game.getHeight();
        
        int slotSize = 50;
        int slotPadding = 10;
        int totalWidth = (slotSize * 4) + (slotPadding * 3);
        int toolBarX = (width - totalWidth) / 2;
        int toolBarY = height - 140;

        //************* Toolbar Click Handling *************//
        Rectangle toolBarRect = new Rectangle(toolBarX, toolBarY, totalWidth, slotSize);
        boolean clickedToolbar = toolBarRect.contains(point);

        if (clickedToolbar) {
            for (int i = 0; i < 4; i++) {
                int x = toolBarX + i * (slotSize + slotPadding);
                Rectangle slotBounds = new Rectangle(x, toolBarY, slotSize, slotSize);

                if (slotBounds.contains(point)) { // Clicked tool slot
                    Tool tool = toolSystem.getToolInSlot(i);
                    if (tool != null && tool.getIsUnlocked()) {
                        toolSystem.setActiveSlot(i);
                        SoundManager.playSound("buttonClick.wav");
                        System.out.println("HeadUpDisplay.java - Switched to: " + tool.getToolName());
                    }
                }
            }
        }
        //************* Building Bar Bounds *************//
        int buildingBarW = (11 * slotSize) + (9 * slotPadding);
        int buildingBarX = (width - buildingBarW) / 2;
        int buildingBarY = height - 70;
        
        Rectangle buildingBarRect = new Rectangle(buildingBarX, buildingBarY, buildingBarW, slotSize);
        boolean clickedBuildingBar = buildingBarRect.contains(point);

        //************* Building Bar Click Handling *************//
        if (clickedBuildingBar) {
            BuildingSystem buildingSystem = game.getBuildingSystem();
            ResourceSystem resourceSystem = game.getResourceSystem();

            if (buildingSystem != null) {
                for (int i = 0; i < 11; i++) { //slots 1-11
                    int x = buildingBarX + i * (slotSize + slotPadding);
                    Rectangle slotBounds = new Rectangle(x, buildingBarY, slotSize, slotSize);
                    
                    if (slotBounds.contains(point)) { // Clicked this building slot
                        Building building = buildingSystem.getBuildingInSlot(i);
                        if (building != null && !building.isLocked()) {
                            
                            if (building.isUnlocker() && buildingSystem.isGoldStashPlaced()) { // Only one Gold Stash allowed
                                System.out.println("HeadUpDisplay.java - Gold Stash already placed");
                                return true;
                            }

                            if (buildingSystem.isLimitReached(building)) { // Building limit reached
                                System.out.println("HeadUpDisplay.java - Building Limit Reached");
                                return true;
                            }

                            if (!building.isUnlocker()) {
                                if (resourceSystem.getWoodCount() < building.getWoodCost() || resourceSystem.getStoneCount() < building.getStoneCost()) { // Check resources
                                    System.out.println("HeadUpDisplay.java - Not enough resources");
                                    return true;
                                }
                            }

                            game.startPlacement(building); 
                            SoundManager.playSound("buttonClick.wav");
                            System.out.println("HeadUpDisplay.java - Started placement for: " + building.getName());
                        }
                    }
                }
            }
        }
        return clickedToolbar || clickedBuildingBar;
    }

    /**
     * Keeps track of time for day-night cycle
     * Precondition: N/A
     * Postcondition: time is updated for day-night cycle
     */
    public void update() {
        time += dayLength;
        if (time > 1.0f) {
            time = 0.0f;
        }
    }

    /**
     * Gets the current time value for day-night cycle
     * Precondition: N/A
     * Postcondition: returns time value between 0.0 and 1.0
     * @return the current time value for day-night cycle
     */
    public float getTime() {
        return time;
    }

    /**
     * Draws the HUD elements on the screen
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: HUD elements are drawn on the screen
     * @param g2d the Graphics2D object used for drawing
     * @param screenW the width of the screen
     * @param screenH the height of the screen
     * @param toolSystem the ToolSystem object managing tools
     * @param resourceSystem the ResourceSystem object managing resources
     * @param waveCount the current wave count in the game
     */
    public void draw(Graphics2D g2d, int screenW, int screenH, ToolSystem toolSystem, ResourceSystem resourceSystem, int waveCount) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawTimeBar(g2d, screenH);
        drawMinimap(g2d, screenH);
        drawResourcePanel(g2d, screenW, screenH, resourceSystem, waveCount);
        drawHealthBar(g2d, screenW, screenH);
        drawToolbars(g2d, screenW, screenH, toolSystem);
        drawActionButtons(g2d, screenW, screenH);
        drawTutorial(g2d, screenW, screenH);
        drawDeathOverlay(g2d, screenW, screenH);

        //************* Draw Upgrade/Sell Box *************//
        Building selected = game.getBuildingSystem().getSelectedBuilding();
        if (selected != null) {
            drawUpgradeBox(g2d, selected);
        } else {
            upgradeButtonBounds = null;
            sellButtonBounds = null;
        }
    }

    /**
     * Draws the resource panel on the HUD
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: resource panel is drawn on the HUD
     * @param g2d the Graphics2D object used for drawing
     * @param screenW the width of the screen
     * @param screenH the height of the screen
     * @param resourceSystem the ResourceSystem object managing resources
     * @param waveCount the current wave count in the game
     */
    private void drawResourcePanel(Graphics2D g2d, int screenW, int screenH, ResourceSystem resourceSystem, int waveCount) {
        //************* Resource Panel Size *************//
        int resourcePanelW = 240, resourcePanelH = 130;
        int resourcePanelX = screenW - resourcePanelW - 20;
        int resourcePanelY = screenH - resourcePanelH - 80;

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
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 14f));
        g2d.setColor(Color.WHITE);
        g2d.drawString(TextFormatter.formatValue(resourceSystem.getWoodCount()), resourcePanelX + (resourcePanelW / 2) - 40, resourcePanelY + 30); 
        g2d.drawString(TextFormatter.formatValue(resourceSystem.getStoneCount()), resourcePanelX + (resourcePanelW - 35), resourcePanelY + 30);
        g2d.drawString(TextFormatter.formatValue(resourceSystem.getGoldCount()), resourcePanelX + (resourcePanelW / 2) - 45, resourcePanelY + 70);
        g2d.drawString(TextFormatter.formatValue(resourceSystem.getTokenCount()), resourcePanelX + (resourcePanelW - 25), resourcePanelY + 70);
        g2d.drawString(String.valueOf(waveCount), resourcePanelX + (resourcePanelW - 20) - 10, resourcePanelY + 110);

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
     * @param g2d the Graphics2D object used for drawing
     * @param screenW the width of the screen
     * @param screenH the height of the screen
     */
    private void drawHealthBar(Graphics2D g2d, int screenW, int screenH) {
        //************* Health Bar Size and Position *************//
        int healthBarW = 240, healthBarH = 40;
        int healthBarX = screenW - healthBarW - 20;
        int healthBarY = screenH - 55;

        //************* Armor Bar *************//
        int shield = player.getShieldHealth();
        int maxShield = player.getMaxShieldHealth();

        if (maxShield > 0) {
            int armorBarH = 13; 
            int armorBarY = (int)(healthBarY - armorBarH / 1.5f) - 10;

            //************* Health Bar Background *************//
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.fillRoundRect(healthBarX, armorBarY, healthBarW, armorBarH, 10, 10);

            //************* Armor Bar Fill *************//
            int armorInnerX = healthBarX + 5, armorInnerY = armorBarY + armorBarH / 4;
            int armorInnerW = healthBarW - 10, armorInnerH = armorBarH / 2;

            g2d.setColor(new Color(20, 50, 80, 100));
            g2d.fillRoundRect(armorInnerX, armorInnerY, armorInnerW, armorInnerH, 5, 5);
 
            g2d.setColor(new Color(61, 161, 217)); 
            double armorPercent = (double) shield / maxShield;

            g2d.fillRoundRect(armorInnerX, armorInnerY, (int)(armorInnerW * armorPercent), armorInnerH, 5, 5);
        
        }

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
     * @param g2d the Graphics2D object used for drawing
     * @param screenW the width of the screen
     * @param screenH the height of the screen
     */
    private void drawTimeBar(Graphics2D g2d, int screenH) {
        //************* Time Bar Size and Position *************//
        int barW = 150, barH = 20;
        int barX = 20, barY = screenH - 200;

        int offset = (int)(time * barW);
        int shift = barW / 4;

        Shape oldClip = g2d.getClip(); // Save current clip

        g2d.setClip(new RoundRectangle2D.Float(barX, barY, barW, barH, 15, 15));

        //************* Time Bar Fill *************//
        g2d.setColor(new Color(132, 115, 212, 180)); // purple
        g2d.fillRect(barX - (barW / 2) - offset + shift, barY, barW / 2, barH);

        g2d.setColor(new Color(214, 171, 53, 180)); // yellow
        g2d.fillRect(barX - offset + shift, barY, barW / 2, barH);
        
        g2d.setColor(new Color(132, 115, 212, 180)); // purple
        g2d.fillRect(barX + (barW / 2) - offset + shift, barY, barW / 2, barH);
        
        g2d.setColor(new Color(214, 171, 53, 180)); // yellow
        g2d.fillRect(barX + barW - offset + shift, barY, barW / 2, barH);
        
        g2d.setColor(new Color(132, 115, 212, 180)); // purple
        g2d.fillRect(barX + (int)(barW * 1.5) - offset + shift, barY, barW / 2, barH);

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
     * @param g2d the Graphics2D object used for drawing
     * @param screenH the height of the screen
     */
    private void drawMinimap(Graphics2D g2d, int screenH) {
        //************* Minimap Size and Position *************//
        int miniSize = 150;
        int miniX = 20, miniY = screenH - miniSize - 15;

        //************* Minimap Background *************//
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect(miniX, miniY, miniSize, miniSize, 15, 15);

        //************* Minimap World Area *************//
        // Player position relative to world
        int walkableArea = BenumZombsGame.PLAY_AREA - (BenumZombsGame.BORDER_THICKNESS * 2);

        double relativeX = (player.getCenterX() - (BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS)) / (double)walkableArea;
        double relativeY = (player.getCenterY() - (BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS)) / (double)walkableArea;
        int dotX = miniX + (int)(relativeX * miniSize);
        int dotY = miniY + (int)(relativeY * miniSize);

        // Stash position relative to world
        Building stash = game.getBuildingSystem().getActiveStash();
        if (stash != null) {
            double stashRelativeX = (stash.getX() - (BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS)) / (double)walkableArea;
            double stashRelativeY = (stash.getY() - (BenumZombsGame.OFFSET + BenumZombsGame.BORDER_THICKNESS)) / (double)walkableArea;
            int stashDotX = miniX + (int)(stashRelativeX * miniSize);
            int stashDotY = miniY + (int)(stashRelativeY * miniSize);

            //************* Minimap Stash Dot *************//
            g2d.setColor(new Color(135, 95, 69)); 
            g2d.fillRoundRect(stashDotX, stashDotY, 5, 5, 5, 5);
        }

        //************* Minimap Player Dot *************//
        g2d.setColor(new Color(132, 115, 212)); 
        g2d.fillOval(dotX - 3, dotY - 3, 6, 6);
    }

    /**
     * Draws the toolbars on the HUD
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: toolbars are drawn on the HUD
     * @param g2d the Graphics2D object used for drawing
     * @param screenW the width of the screen
     * @param screenH the height of the screen
     * @param toolSystem the ToolSystem object managing tools
     */
    private void drawToolbars(Graphics2D g2d, int screenW, int screenH, ToolSystem toolSystem) {
        //************* Toolbar Size and Position *************//
        int slotSize = 50;
        int slotPadding = 10;
        Point mousePos = game.getMousePosition();

        int totalWidth = (4 * slotSize) + (3 * slotPadding);
        int toolBarX = (screenW - totalWidth) / 2;
        int toolBarY = screenH - 140;

        Tool toolToolTip = null;
        Building buildingToolTip = null;
        int hoverSlotX = 0;

        //************* Tool Bar Slots and Tools *************//
        for (int i = 0; i < 4; i++) {
            int x = toolBarX + i * (slotSize + slotPadding);
            Rectangle slotRect = new Rectangle(x, toolBarY, slotSize, slotSize);
            boolean isHovered = (mousePos != null && slotRect.contains(mousePos));

            Tool tool = toolSystem.getToolInSlot(i);
            boolean isUnlocked = tool.getIsUnlocked();

            if (isHovered && isUnlocked) { // Hover effect
                g2d.setColor(new Color(247, 247, 247, 55));
            } else {
                g2d.setColor(new Color(247, 247, 247, 30));
            }
            g2d.fillRoundRect(x, toolBarY, slotSize, slotSize, 10, 10);
            
            if (isUnlocked) {  // Draw tool icon if unlocked
                tool.draw(g2d, x + slotSize / 2 - 10, toolBarY + slotSize / 2 + 10, 0.8, 0.5);

                if (isHovered) { // Open info box on hover
                    toolToolTip = tool;
                    hoverSlotX = x;
                }
            }
        }

        //************* Building Bar Size and Position *************//
        int buildingBarW = (11 * slotSize) + (9 * slotPadding);
        int buildingBarX = (screenW - buildingBarW) / 2;
        int buildingBarY = screenH - 70;
        BuildingSystem buildingSystem = game.getBuildingSystem();

        //************* Building Bar Slots *************//
        for (int i = 0; i < 11; i++) {
            int x = buildingBarX + i * (slotSize + slotPadding);
            Rectangle slotRect = new Rectangle(x, buildingBarY, slotSize, slotSize);
            boolean isHovered = (mousePos != null && slotRect.contains(mousePos));

            Building building = buildingSystem.getBuildingInSlot(i);
            boolean isLocked = building.isLocked();

            if (isHovered && !isLocked) { //Hover effect
                g2d.setColor(new Color(0, 0, 0, 55));
            } else {
                g2d.setColor(new Color(0, 0, 0, 30));
            }
            g2d.fillRoundRect(x, buildingBarY, slotSize, slotSize, 10, 10);
            
            if (building.getIcon() != null) { // Draw building icon
                g2d.drawImage(building.getIcon(), x + 5, buildingBarY + 5, slotSize - 10, slotSize - 10, null);

                if (isLocked || (building.isUnlocker() && buildingSystem.isGoldStashPlaced()) || buildingSystem.isLimitReached(building)) {
                    g2d.setColor(new Color(0, 0, 0, 130));
                    g2d.fillRoundRect(x, buildingBarY, slotSize, slotSize, 10, 10);
                }
   
                if (isHovered) { // Open info box on hover
                    buildingToolTip = building;
                    hoverSlotX = x;
                }
            }

            if (i < 10) { // Draw keybind numbers
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.TRUETYPE_FONT, 8f));
                    String keyNum = String.valueOf(i);
                    g2d.drawString(keyNum, x + slotSize - 10, buildingBarY + slotSize - 5);
                }
            
        }

        if (toolToolTip != null) { // Draw tool info box
            drawToolTip(g2d, hoverSlotX, toolBarY, slotSize, toolToolTip);
        }
        if (buildingToolTip != null) { // Draw building info box
            drawBuildingTip(g2d, hoverSlotX, buildingBarY, slotSize, buildingToolTip);
        }
    }

    /**
     * Draws the action buttons on the HUD
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: action buttons are drawn on the HUD
     * @param g2d the Graphics2D object used for drawing
     * @param screenW the width of the screen
     * @param screenH the height of the screen
     */
    private void drawActionButtons(Graphics2D g2d, int screenW, int screenH) {
        //************* Action Buttons Size and Position *************//
        int buttonSize = 60;

        int settingButtonX = screenW - buttonSize;
        int settingButtonY = screenH / 2 + buttonSize/2;
        int shopButtonX = screenW - buttonSize;
        int shopButtonY = screenH / 2 - buttonSize/2;

        //************* Action Buttons Backgrounds and Bounds *************//
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect(settingButtonX, settingButtonY, buttonSize, buttonSize, 15, 15);
        settingsButtonBounds = new Rectangle(settingButtonX, settingButtonY, buttonSize, buttonSize);

        g2d.fillRoundRect(shopButtonX, shopButtonY, buttonSize, buttonSize, 15, 15);
        shopButtonBounds = new Rectangle(shopButtonX, shopButtonY, buttonSize, buttonSize);

        //************* Action Buttons Icons *************//
        int iconSize = 30;
        int offset = (buttonSize / 2) - (iconSize / 2);

        if (settingsIcon != null) {
            g2d.drawImage(settingsIcon, settingsButtonBounds.x + offset, settingsButtonBounds.y + offset, iconSize, iconSize, null);
        }

        if (shopIcon != null) {
            g2d.drawImage(shopIcon, shopButtonBounds.x + offset, shopButtonBounds.y + offset, iconSize, iconSize, null);
        }
    }

    /**
     * Draws the tooltip for a tool
     * Precondition: g2d is a valid Graphics2D object, slotX and slotY are the position of the tool slot, slotSize is the size of the tool slot, tool is a valid Tool object
     * Postcondition: tooltip is drawn for the tool
     * @param g2d the Graphics2D object used for drawing
     * @param slotX the x-coordinate of the tool slot
     * @param slotY the y-coordinate of the tool slot
     * @param slotSize the size of the tool slot
     * @param tool the Tool object for which the tooltip is drawn
     */
    private void drawToolTip(Graphics2D g2d, int slotX, int slotY, int slotSize, Tool tool) {
        //************* Tool Info Box Text *************//
        ArrayList<String> lines = new ArrayList<>();
        lines.add(tool.getToolName()); 
        
        if (tool.getLevel() >= 7) {
            lines.add("Tier: MAX");
        } else {
            lines.add("Tier " + tool.getLevel() + " Item");
        }

        lines.addAll(TextFormatter.wrapText(tool.getDescription(), 30)); 

        //************* Tool Info Box Size and Position *************//
        int boxW = 220;
        int boxH = (lines.size() * 20) + 20; 
        int boxX = slotX + (slotSize / 2) - (boxW / 2); 
        int boxY = slotY - boxH - 15; 

        g2d.setColor(new Color(0, 0, 0, 130)); 
        g2d.fillRoundRect(boxX, boxY, boxW, boxH, 10, 10);

        int textY = boxY + 25;
        int textX = boxX + 10;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            
            if (i == 0) { // Tool name
                g2d.setColor(Color.WHITE); 
                g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 15f));
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.setFont(FontManager.googleSansFlex.deriveFont(12f));
            }
            
            g2d.drawString(line, textX, textY);
            textY += 20; 
        }
    }

    /**
     * Draws the tooltip for a building
     * Precondition: g2d is a valid Graphics2D object, slotX and slotY are the position of the building slot, slotSize is the size of the building slot, building is a valid Building object
     * Postcondition: tooltip is drawn for the building
     * @param g2d the Graphics2D object used for drawing
     * @param slotX the x-coordinate of the building slot
     * @param slotY the y-coordinate of the building slot
     * @param slotSize the size of the building slot
     * @param building the Building object for which the tooltip is drawn
     */
    private void drawBuildingTip(Graphics2D g2d, int slotX, int slotY, int slotSize, Building building) {
        BuildingSystem buildingSystem = game.getBuildingSystem();
        ResourceSystem resourceSystem = game.getResourceSystem();

        //************* Building Info Box Bounds *************//
        ArrayList<String> description = TextFormatter.wrapText(building.getDescription(), 30);
        int boxW = 240; 
        int boxH = 85 + (description.size() * 18); 
        int boxX = slotX + (slotSize / 2) - (boxW / 2); 
        int boxY = slotY - boxH - 15;
        int textX = boxX + 10;
        int textY = boxY + 25;

        g2d.setColor(new Color(0, 0, 0, 130));
        g2d.fillRoundRect(boxX, boxY, boxW, boxH, 10, 10);

        g2d.setColor(Color.WHITE); // Building name
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 15f));
        g2d.drawString(building.getName(), textX, textY);

        if (buildingSystem != null && !building.isLocked() && !building.isUnlocker()) {
            //************* Placed Buildings Count *************//
             int current = buildingSystem.getBuildingCount(building); 
             int max = buildingSystem.getBuildingLimit(building);
             String placedText = current + "/" + max;
             
             g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 12f));
             if (buildingSystem.isLimitReached(building)) {
                 g2d.setColor(new Color(197, 82, 59));
             } else {
                 g2d.setColor(Color.GRAY); 
             }
             
             int placedW = g2d.getFontMetrics().stringWidth(placedText);
             g2d.drawString(placedText, boxX + boxW - 15 - placedW, textY);
        }

        //************* Building Tier *************//
        textY += 15;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(12f));
        if (building.getLevel() >= 7) {
            g2d.drawString("Tier: MAX", textX, textY);
        } else {
            g2d.drawString("Tier " + building.getLevel() + " Building", textX, textY);
        }

        //************* Building Description *************//
        textY += 10; 
        for (int i = 0; i < description.size(); i++) {
            textY += 18;
            g2d.drawString(description.get(i), textX, textY);
        }

        //************* Building Cost *************//
        textY += 25; 
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 12f));

        if (building.getName().equals("Gold Stash")) { // Set cost to free for Gold Stash
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawString("Free", textX, textY);
        } else {
            int woodCost = building.getWoodCost();
            int stoneCost = building.getStoneCost();
            
            //************* Wood Cost Text *************//
            if (resourceSystem.getWoodCount() < woodCost && !building.isLocked()) {
                g2d.setColor(new Color(197, 82, 59));
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
            }
            
            String woodText = woodCost + " Wood,";
            g2d.drawString(woodText, textX, textY);
            
            int woodW = g2d.getFontMetrics().stringWidth(woodText);

            //************* Stone Cost Text *************//
            if (resourceSystem.getStoneCount() < stoneCost && !building.isLocked()) {
                g2d.setColor(new Color(197, 82, 59));
            } else {
                g2d.setColor(Color.LIGHT_GRAY); 
            }
            
            g2d.drawString(stoneCost + " Stone", textX + woodW + 5, textY);
        }
    }

    /**
     * Draws the upgrade box for a selected building
     * Precondition: g2d is a valid Graphics2D object, building is a valid Building object
     * Postcondition: upgrade box is drawn for the selected building
     * @param g2d the Graphics2D object used for drawing
     * @param building the Building object for which the upgrade box is drawn
     */
    private void drawUpgradeBox(Graphics2D g2d, Building building) {
        double cameraX = (game.getWidth() / 2) - player.getCenterX();
        double cameraY = (game.getHeight() / 2) - player.getCenterY();
        int buildingScreenX = (int)(building.getX() + cameraX);
        int buildingScreenY = (int)(building.getY() + cameraY);
        
        //************* Upgrade Box Size and Position *************//
        int boxW = 400; 
        int contentHeight = 170;
        int boxH = contentHeight;
        int boxX = buildingScreenX + (building.getWidth() / 2) - (boxW / 2);
        int boxY = buildingScreenY - boxH - 20; 

        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillRoundRect(boxX, boxY, boxW, boxH, 10, 10);

        int textX = boxX + 10;
        int textY = boxY + 30;
        g2d.setColor(Color.WHITE); // Building name
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 23f));
        g2d.drawString(building.getName(), textX, textY);

        //************* Health Bar *************//
        int barW = 90;
        int barH = 30;
        int barX = boxX + boxW - barW - 10;
        int barY = boxY + 15;

        int innerX = barX + 3, innerY = barY + 3;
        int innerW = barW - 6, innerH = barH - 6;
        
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillRoundRect(barX, barY, barW, barH, 10, 10);
        
        g2d.setColor(new Color(100, 161, 10));
        double hpPercent = (double)building.getHealth() / building.getMaxHealth();
        if (hpPercent < 0) {
            hpPercent = 0;
        }
        g2d.fillRoundRect(innerX, innerY, (int)(innerW * hpPercent), innerH, 10, 10);

        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 12f));
        g2d.drawString("HP", barX + 10, barY + 18);

        //************ Tier Label *************//
        int currentButtonY = textY + 25;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 16f));
        g2d.drawString("Tier " + building.getLevel() + " Building", textX, currentButtonY);
        
        //************* Building Control Buttons *************//
        int buttonH = 35;
        int buttonW = boxW - 20;
        int buttonX = boxX + 10;
        currentButtonY += 35;

        upgradeButtonBounds = new Rectangle(buttonX, currentButtonY, buttonW, buttonH);

        BuildingSystem buildingSystem = game.getBuildingSystem();
        Building stash = buildingSystem.getActiveStash();
            int stashLvl;
            if (stash != null) {
                stashLvl = stash.getLevel();
            } else {
                stashLvl = 1;
            }
        
        //************* Upgrade Button *************//
        if (building.getLevel() >= building.getMaxLevel()) { // If max level reached
             g2d.setColor(new Color(53, 92, 27)); 
             g2d.fillRoundRect(buttonX, currentButtonY, buttonW, buttonH, 10, 10);
             g2d.setColor(new Color(131, 140, 124)); 
             g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 12f));
             g2d.drawString("Max Level", upgradeButtonBounds.x + 10, upgradeButtonBounds.y + 22);
        } else if (!building.canUpgrade(stashLvl)) { // If stash level too low
             g2d.setColor(new Color(53, 92, 27));
             g2d.fillRoundRect(buttonX, currentButtonY, buttonW, buttonH, 10, 10);
             g2d.setColor(new Color(131, 140, 124));
             g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 12f));
             g2d.drawString("Requires Stash Level " + (building.getLevel() + 1), upgradeButtonBounds.x + 7, upgradeButtonBounds.y + 22);
        } else { // Normal upgrade
             ResourceSystem resourceSystem = game.getResourceSystem();
    
             if (resourceSystem.canAfford(building.getUpgradeWoodCost(), building.getUpgradeStoneCost(), building.getUpgradeGoldCost())) { // Adjust button color based on affordability
                g2d.setColor(new Color(99, 183, 32));
             } else {
                g2d.setColor(new Color(53, 92, 27));
             }
             g2d.fillRoundRect(buttonX, currentButtonY, buttonW, buttonH, 10, 10);
             
             g2d.setColor(Color.WHITE);
             g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 12f));

             //************* Upgrade Cost Label *************//
             StringBuilder label = new StringBuilder("Upgrade: ");
             if (building.getUpgradeWoodCost() > 0) {
                label.append(building.getUpgradeWoodCost()).append(" wood, ");
             }
             if (building.getUpgradeStoneCost() > 0) {
                label.append(building.getUpgradeStoneCost()).append(" stone, ");
             }
             if (building.getUpgradeGoldCost() > 0) {
                label.append(building.getUpgradeGoldCost()).append(" gold");
             }

             g2d.drawString(label.toString(), upgradeButtonBounds.x + 10, upgradeButtonBounds.y + 22);
        }

        //************* Sell Button *************//
        currentButtonY += buttonH + 5;
        sellButtonBounds = new Rectangle(buttonX, currentButtonY, buttonW, buttonH);
        
        if (building.canBeSold()) { // If building can be sold
            g2d.setColor(new Color(179, 53, 60));
            g2d.fillRoundRect(buttonX, currentButtonY, buttonW, buttonH, 10, 10);
            
            g2d.setColor(Color.WHITE);
            String sellLabel = "Sell (" + building.getWoodSellValue() + " wood, " + building.getStoneSellValue() + " stone)";
            g2d.drawString(sellLabel, sellButtonBounds.x + 10, sellButtonBounds.y + 22);
        } else { //For Gold Stash
            g2d.setColor(new Color(99, 53, 45));
            g2d.fillRoundRect(buttonX, currentButtonY, buttonW, buttonH, 10, 10);
            
            g2d.setColor(new Color(136, 143, 123));
            g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 12f));
            g2d.drawString("Sell", sellButtonBounds.x + 10, sellButtonBounds.y + 22);
        }
    }

    /**
     * Draws the death overlay on the screen
     * Precondition: g2d is a valid Graphics2D object, screenW and screenH are the screen dimensions
     * Postcondition: death overlay is drawn on the screen
     * @param g2d the Graphics2D object used for drawing
     * @param screenW the width of the screen
     * @param screenH the height of the screen
     */
    private void drawDeathOverlay(Graphics2D g2d, int screenW, int screenH) {
        if (isDeathScreenVisible) {
            //************* Death Panel Size *************//
            int deathPanelW = screenW - (screenW / 4), deathPanelH = screenH - (screenH / 3);
            int deathPanelX = (screenW / 2) - (deathPanelW / 2);
            int deathPanelY = (screenH / 2) - (deathPanelH / 2);

            //************* Death Panel Background *************//
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, screenW, screenH);

            g2d.setColor(new Color(0, 0, 0, 170));
            g2d.fillRoundRect(deathPanelX, deathPanelY, deathPanelW, deathPanelH, 20, 20);

            //************* Death Panel Text *************//
            g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 25f));
            g2d.setColor(Color.WHITE);
            String titleText = "You Died";
            int titleW = g2d.getFontMetrics().stringWidth(titleText);
            g2d.drawString(titleText, (screenW / 2) - (titleW / 2), deathPanelY + 60);        

            g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 17f));
            g2d.setColor(Color.LIGHT_GRAY);
            int messageW = g2d.getFontMetrics().stringWidth(deathMessage);
            g2d.drawString(deathMessage, (screenW / 2) - (messageW / 2), deathPanelY + 120);

            //************* Respawn Button *************//
            int buttonWidth = 200;
            int buttonHeight = 40;
            if (respawnButton != null) {
                respawnButton.setBounds((screenW / 2) - (buttonWidth / 2), deathPanelY + deathPanelH - buttonHeight - 20, buttonWidth, buttonHeight);
                if (!respawnButton.isVisible()) {
                    respawnButton.setVisible(true);
                }
            }
        }
    }

    /**
     * Shows the death screen with a message
     * Precondition: message is a valid String
     * Postcondition: death screen is displayed with the provided message
     * @param message the message to display on the death screen
     */
    public void showDeathScreen(String message) {
        SoundManager.playSound("deathSound.wav");
        this.isDeathScreenVisible = true;
        this.deathMessage = message;
    }
    
    /**
     * Hides the death screen
     * Precondition: N/A
     * Postcondition: death screen is hidden
     */
    public void hideDeathScreen() {
        this.isDeathScreenVisible = false;
        if (respawnButton != null) {
            respawnButton.setVisible(false);
        }
    }
    
    /**
     * Checks if the death screen is currently visible
     * Precondition: N/A
     * Postcondition: returns true if death screen is visible, false otherwise
     * @return true if death screen is visible, false otherwise
     */
    public boolean isDeathScreenVisible() {
        return isDeathScreenVisible;
    }

    private void drawTutorial(Graphics2D g2d, int screenW, int screenH) {
        if (tutorialActive){
            //************* Tutorial Panel Size *************//
            int tutorialPanelW = screenW - (screenW / 2) - 50, tutorialPanelH = 80;
            int tutorialPanelX = (screenW / 2) - (tutorialPanelW / 2);
            int tutorialPanelY = 10;

            g2d.setColor(new Color(0, 0, 0, 130));
            g2d.fillRoundRect(tutorialPanelX, tutorialPanelY, tutorialPanelW, tutorialPanelH, 20, 20);

            //************* Tutorial Text *************//
            g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 16f));
            g2d.setColor(Color.WHITE);
            String text = "";
            ArrayList<String> description = new ArrayList<>();
            switch(tutorialStep){
                case 1:
                    text = "Start off by gathering some resources. Collect 10 wood and stone using WASD keys and harvesting with Left Click";
                    description = TextFormatter.wrapText(text, 50);
                    break;
                case 2:
                    text = "Now you're ready to place down your gold stash! Once you establish your base zombies will start spawning each night.";
                    description = TextFormatter.wrapText(text, 50);
                    break;
                case 3:
                    text = "You're ready to start building your defenses! Start by using the 5 wood you gathered earlier to place an Arrow Tower from the toolbar below.";
                    description = TextFormatter.wrapText(text, 50);
                    break;
                case 4:
                    text = "Now you're protected you should start generating gold. Do this by building a Gold Mine from the toolbar, this will passively give you gold.";
                    description = TextFormatter.wrapText(text, 50);
                    tutorialActive = false;

                    break;
            }
            int textY = tutorialPanelY + 10;
            for (int i = 0; i < description.size(); i++) {
                textY += 18;
                g2d.drawString(description.get(i), tutorialPanelX + 80, textY);
            }
        }
    }
}
