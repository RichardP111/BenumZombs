/**
 * ShopScreen.java
 * The shop screen panel for BenumZombs, allowing players to purchase and upgrade tools
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-13
 */

package game;

import helpers.FontManager;
import helpers.RoundedJButton;
import helpers.SoundManager;
import helpers.TextFormatter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import objects.Tools.Tool;
import systems.ResourceSystem;
import systems.ToolSystem;

@SuppressWarnings("Convert2Lambda")
public class ShopScreen extends JPanel {
    private BenumZombsGame gameInstance;
    private final RoundedJButton backButton;
    private final JLabel shopLabel;
    private Point hoverPoint;
    private int cost;

    //************* Tabs and Bounds *************//
    private enum ShopTab {
        Weapons, Armor, Utility
    }

    private ShopTab currentTab = ShopTab.Weapons;
    private final Rectangle[] tabBounds = new Rectangle[ShopTab.values().length];
    private final Rectangle[] weaponCardBounds = new Rectangle[3];
    private final Rectangle[] armorCardBounds = new Rectangle[1];
    private final Rectangle[] utilityCardBounds = new Rectangle[1];
    private ShopTab[] tabs;

    /**
     * Constructor for Shop Screen
     * Precondition: N/A
     * Postcondition: Shop screen panel is created
     */
    public ShopScreen() {
        setLayout(null);
        setBackground(new Color(42, 56, 26));  
        setFocusable(true);

        tabs = ShopTab.values();
        hoverPoint = new Point(0, 0);

        //************* Shop Label *************//
        shopLabel = new JLabel("Shop");
        shopLabel.setFont(FontManager.googleSansFlex.deriveFont(30f));   
        shopLabel.setForeground(Color.WHITE);
        shopLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(shopLabel);
       
        //************* Back Button *************//
        backButton = new RoundedJButton("Back To Game");
        backButton.setFont(FontManager.googleSansFlex.deriveFont(20f));  
        backButton.setBackground(new Color(103, 90, 166));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        add(backButton);

        //************* Component Listener for Resizing *************//
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });

        //************* Back Button Action Listener *************//
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameInstance != null) {
                    Main.showScreen("GAME");
                    gameInstance.requestFocusInWindow();
                    System.out.println("ShopScreen.java - Returning to game from Shop Screen.");
                }
                SoundManager.playSound("buttonClick.wav");
            }
        });

        //************* Mouse Listener for Tab Switching and Card Clicking *************//
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hoverPoint = e.getPoint();
                repaint();
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();

                //************* Tab Switching *************//
                for (int i = 0; i < tabs.length; i++) {
                    if (tabBounds[i].contains(p)) {
                        currentTab = tabs[i];
                        SoundManager.playSound("buttonClick.wav");
                        System.out.println("ShopScreen.java - Switched tab in Shop Screen: " + currentTab);
                        repaint();
                    }
                }

                //************* Card Clicking *************//
                if (currentTab == ShopTab.Weapons && gameInstance != null) {
                    for (int i = 0; i < weaponCardBounds.length; i++) {
                        if (weaponCardBounds[i] != null && weaponCardBounds[i].contains(p)) {
                            handlePurchase(i);
                            SoundManager.playSound("buttonClick.wav");
                            System.out.println("ShopScreen.java - Clicked on weapon card in Shop Screen: " + i);
                            return;
                        }
                    }
                }

                if (currentTab == ShopTab.Armor && gameInstance != null) {
                    for (int i = 0; i < armorCardBounds.length; i++) {
                        if (armorCardBounds[i] != null && armorCardBounds[i].contains(p)) {
                            handlePurchase(i);
                            SoundManager.playSound("buttonClick.wav");
                            System.out.println("ShopScreen.java - Clicked on armor card in Shop Screen: " + i);
                            return;
                        }
                    }
                }

                if (currentTab == ShopTab.Utility && gameInstance != null) {
                    for (int i = 0; i < utilityCardBounds.length; i++) {
                        if (utilityCardBounds[i] != null && utilityCardBounds[i].contains(p)) {
                            handlePurchase(i);
                            SoundManager.playSound("buttonClick.wav");
                            System.out.println("ShopScreen.java - Clicked on utility card in Shop Screen: " + i);
                            return;
                        }
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_B) {
                    if (gameInstance != null) {
                        Main.showScreen("GAME");
                        gameInstance.requestFocusInWindow();
                        System.out.println("ShopScreen.java - Closed via 'B'");
                    }
                }
            }
        });
    }

    /**
     * Sets the game instance associated with this settings screen
     * Precondition: game is a valid BenumZombsGame object or null
     * Postcondition: gameInstance is set to the provided game
     * @param game the BenumZombsGame instance
     */
    public void setGameInstance(BenumZombsGame game) {
        this.gameInstance = game;
        this.requestFocusInWindow();
    }

    /**
     * Updates the layout of the components based on the current size of the panel
     * Precondition: N/A
     * Postcondition: components are repositioned and resized based on screen size
     */
    private void updateLayout() {
        int width = getWidth();
        int height = getHeight();

        //************* Size and Position of Large Graphical Box *************//
        int largeBoxW = width - 200;
        int largeBoxH = height - 200;
        int largeBoxX = (width - largeBoxW) / 2;
        int largeBoxY = (height - largeBoxH) / 2 - 20;

        //************* Set Bounds of Main Labels and Buttons *************//
        shopLabel.setBounds(largeBoxX + 30, largeBoxY + 20, 300, 40);
        backButton.setBounds((width - 150) / 2, largeBoxY + largeBoxH + 50, 250, 40);

        int tabWidth = (largeBoxW - 60) / tabs.length;
        for (int i = 0; i < tabs.length; i++) {
            tabBounds[i] = new Rectangle(largeBoxX + 30 + (i * tabWidth), largeBoxY + 80, tabWidth, 40);
        }
    }

    /**
     * Handles the purchase or upgrade of a tool in the specified slot
     * Precondition: slotIndex is a valid index for the tool system
     * Postcondition: tool in the specified slot is purchased or upgraded if sufficient resources are available
     * @param slotIndex the index of the tool slot to purchase or upgrade
     */
    private void handlePurchase(int slotIndex) {
        if (gameInstance == null) {
            return;
        }

        ToolSystem toolSystem = gameInstance.getToolSystem();
        ResourceSystem resourceSystem = gameInstance.getResourceSystem();

        //************* Determine Tool Based on Current Tab *************//
        Tool tool;
        switch (currentTab) {
            case Armor:
                tool = toolSystem.getToolInSlot(4);
                break;
            case Utility:
                tool = toolSystem.getToolInSlot(3);
                break;
            default:
                tool = toolSystem.getToolInSlot(slotIndex);
                break;
        }

        //************* Purchase and Upgrade Logic *************//
        cost = tool.getUpgradeCost();
        if (cost == -1) { // Max level
            System.out.println("ShopScreen.java - Tool max: " + tool.getToolName());
            return;
        }

        if (resourceSystem.getGoldCount() >= cost) {
            if (tool.isConsumable() && tool.getIsUnlocked()) {
                System.out.println("ShopScreen.java - Can only buy one at a time: " + tool.getToolName());
                return; 
            }
            resourceSystem.addGold(-cost);
            
            if (!tool.getIsUnlocked()) {
                tool.setUnlocked(true); 
                tool.onGet(gameInstance.getPlayer()); //Fill shield for armor
            } else if (!tool.isConsumable()) {
                tool.upgrade(); 
                tool.onGet(gameInstance.getPlayer());
            }
            
            SoundManager.playSound("buttonClick.wav");
            repaint();
        } else {
            System.out.println("ShopScreen.java - Not enough gold to buy, need: " + cost);
        }
    }

    /**
     * Draws a shop card for a given tool
     * Precondition: g2d is a valid Graphics2D object, tool is a valid Tool object or null
     * Postcondition: shop card is drawn on the screen
     * @param g2d the Graphics2D object to draw on
     * @param x the x-coordinate of the top-left corner of the card
     * @param y the y-coordinate of the top-left corner of the card
     * @param w the width of the card
     * @param h the height of the card
     * @param tool the Tool object to display on the card
     * @param slot the slot index of the tool
     */
    private void drawShopCard(Graphics2D g2d, int x, int y, int w, int h, Tool tool) {
        if (tool == null) {
            return;
        }

        //************* Card Background *************//
        cost = tool.getUpgradeCost();
        int currentGold = gameInstance.getResourceSystem().getGoldCount();
        Rectangle currentCard = new Rectangle(x, y, w, h);
        if (currentCard.contains(hoverPoint) && cost != -1 && currentGold >= cost && !(tool.isConsumable() && tool.getIsUnlocked())) {
            g2d.setColor(new Color(255, 255, 255, 50)); 
        } else {
            g2d.setColor(new Color(255, 255, 255, 30));
        }
        g2d.fillRoundRect(x, y, w, h, 20, 20);

        //************* Tool Icon *************//
        int originalLevel = tool.getLevel();
        int displayLevel = originalLevel;

        if (tool.getIsUnlocked() && !tool.isConsumable() && cost != -1) {
            displayLevel++;
        }

        int iconPadding = 12;
        int iconBoxSize = h - (iconPadding * 2);
        int iconX = x + iconPadding;
        int iconY = y + iconPadding;

        tool.setLevel(displayLevel);

        tool.draw(g2d, iconX + (iconBoxSize / 3), (int)(iconY + iconBoxSize - (iconBoxSize * 0.25)), 0.8, (iconBoxSize / 80) * 0.75);

        tool.setLevel(originalLevel);

        //************* Tool Info Text *************//
        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 22f));
        g2d.drawString(tool.getToolName(), iconX + iconBoxSize + 20, y + 35); 

        g2d.setFont(FontManager.googleSansFlex.deriveFont(14f));
        g2d.setColor(new Color(150, 150, 150));

        if (cost == -1) {
            g2d.drawString("Max Tier", iconX + iconBoxSize + 20, y + 55);
        } else {
            int displayTier = tool.getLevel();
            if (tool.getIsUnlocked() && !tool.isConsumable()) {
                displayTier++;
            }
            g2d.drawString("Tier " + displayTier, iconX + iconBoxSize + 20, y + 55);
        }

        //************* Cost Info *************//
        g2d.setColor(new Color(251, 177, 59));
        g2d.fillOval(x + w - 100, y + 20, 14, 14);

        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 18f));

        if (cost != -1) { 
            g2d.drawString(String.valueOf(cost), x + w - 75, y + 32);
        } else {
            g2d.drawString("MAX", x + w - 75, y + 32);
        }
    }

    /**
     * Updates the layout of the graphical boxes based on the current size of screen
     * Precondition: N/A
     * Postcondition: background boxes are painted
     * @param g the Graphics object to draw on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //************* Large Box *************//
        int largeBoxW = getWidth() - 200;
        int largeBoxH = getHeight() - 200;
        int largeBoxX = (getWidth() - largeBoxW) / 2;
        int largeBoxY = (getHeight() - largeBoxH) / 2 - 20;

        g2d.setColor(new Color(25, 34, 16));
        g2d.fillRoundRect(largeBoxX, largeBoxY, largeBoxW, largeBoxH, 30, 30);

        //************* Gold Count *************//
        g2d.setColor(new Color(251, 177, 59));
        g2d.fillOval(largeBoxW - 100, largeBoxY + 40, 14, 14);

        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 18f));

        ResourceSystem resourceSystem = gameInstance.getResourceSystem();
        g2d.drawString(TextFormatter.formatValue(resourceSystem.getGoldCount()), largeBoxW - 80, largeBoxY + 54);
    
        //************* Tabs *************//
        int tabWidth = (largeBoxW - 60) / tabs.length;
        g2d.setFont(FontManager.googleSansFlex.deriveFont(Font.TRUETYPE_FONT, 20f));

        for (int i = 0; i < tabs.length; i++) {
            if (tabs[i] == currentTab) { // Highlight current tab
                g2d.setColor(new Color(20, 28, 13));
                int tabH = Math.max(50, largeBoxH / 15);
                g2d.fillRoundRect(largeBoxX + 30 + (i * tabWidth), (largeBoxY + 130) - tabH, tabWidth - 10, tabH + 5, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.drawString(tabs[i].name(), largeBoxX + 40 + (i * tabWidth), largeBoxY + 110);
            } else {
                g2d.setColor(new Color(150, 150, 150));
                g2d.drawString(tabs[i].name(), largeBoxX + 40 + (i * tabWidth), largeBoxY + 110);
            }
        }
  
        //************* Content Area *************//
        int contentX = largeBoxX + 30;
        int contentY = largeBoxY + 130;
        int contentW = largeBoxW - 60;
        int contentH = largeBoxH - 160;

        g2d.setColor(new Color(20, 28, 13));
        g2d.fillRoundRect(contentX, contentY, contentW, contentH, 20, 20);

        //************* Shop Cards *************//
        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(16f));
    
        int cardPadding = 15;
        int cardH = (contentH - (cardPadding * 4)) / 3;
        int cardW = contentW - (cardPadding * 2);
        int rx = contentX + cardPadding;
        int ry = contentY + cardPadding;

        switch (currentTab) {
            case Weapons:
                for (int i = 0; i < 3; i++) {
                    ry = contentY + cardPadding + (i * (cardH + cardPadding));
                    
                    weaponCardBounds[i] = new Rectangle(rx, ry, cardW, cardH);
                    
                    Tool tool = gameInstance.getToolSystem().getToolInSlot(i);
                    
                    drawShopCard(g2d, rx, ry, cardW, cardH, tool);
                }
                break;
            case Armor:
                armorCardBounds[0] = new Rectangle(rx, ry, cardW, cardH);
                Tool armor = gameInstance.getToolSystem().getToolInSlot(4);
                
                drawShopCard(g2d, rx, ry, cardW, cardH, armor);
                break;
            case Utility:
                utilityCardBounds[0] = new Rectangle(rx, ry, cardW, cardH);
                
                Tool utility = gameInstance.getToolSystem().getToolInSlot(3);
                
                drawShopCard(g2d, rx, ry, cardW, cardH, utility);
                break;
        }
    }
}
