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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("Convert2Lambda")
public class ShopScreen extends JPanel {
    private BenumZombsGame gameInstance;
    private final RoundedJButton backButton;
    private final JLabel shopLabel;

    private enum ShopTab {
        Weapons, Armor, Utility
    }

    private ShopTab currentTab = ShopTab.Weapons;
    private final Rectangle[] tabBounds = new Rectangle[ShopTab.values().length];
    private ShopTab[] tabs;

    public ShopScreen() {
        setLayout(null);
        setBackground(new Color(42, 56, 26));  

        tabs = ShopTab.values();

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

        //************* Mouse Listener for Tab Switching *************//
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                for (int i = 0; i < tabs.length; i++) {
                    if (tabBounds[i].contains(p)) {
                        currentTab = tabs[i];
                        SoundManager.playSound("buttonClick.wav");
                        System.out.println("ShopScreen.java - Switched tab in Shop Screen: " + currentTab);
                        repaint();
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
    }

    /**
     * Sets the game instance associated with this settings screen
     * Precondition: game is a valid BenumZombsGame object or null
     * Postcondition: gameInstance is set to the provided game
     * @param game
     */
    public void setGameInstance(BenumZombsGame game) {
        this.gameInstance = game;
    }

    /**
     * Updates the layout of the components based on the current size of the panel
     * Precondition: none
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
     * Updates the layout of the graphical boxes based on the current size of screen
     * Precondition: none
     * Postcondition: background boxes are painted
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
        g2d.setColor(new Color(20, 28, 13));
        g2d.fillRoundRect(largeBoxX + 30, largeBoxY + 130, largeBoxW - 60, largeBoxH - 160, 20, 20);

        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.googleSansFlex.deriveFont(16f));

        switch (currentTab) {
            case Weapons -> g2d.drawString("weapon stuff im lazy", largeBoxX + 50, largeBoxY + 170);
            case Armor -> g2d.drawString("aromr", largeBoxX + 50, largeBoxY + 170);
            case Utility -> g2d.drawString("util ", largeBoxX + 50, largeBoxY + 170);
        }
    }
}
