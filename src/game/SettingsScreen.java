/**
 * SettingsScreen.java
 * The settings screen panel for BenumZombs, allowing players to adjust game settings
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-06
 */

package game;

import helpers.FontManager;
import helpers.RoundedJButton;
import helpers.SoundManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SettingsScreen extends JPanel {

    private final RoundedJButton backButton, soundToggleButton;
    private final JLabel settingsLabel, controlTitleLabel, leftControlLabel, rightControlLabel;

    private BenumZombsGame gameInstance;

    /**
     * Constructor for SettingsScreen
     * Precondition: window is a valid JFrame, gameInstance is a valid BenumZombsGame, or null
     * Postcondition: SettingsScreen panel is created within the given window
     * @param window the main game window
     */
    @SuppressWarnings("Convert2Lambda")
    public SettingsScreen() {
        setLayout(null);
        setBackground(new Color(42, 56, 26));  

        //************* Settings Label *************//
        settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(FontManager.googleSansFlex.deriveFont(30f));   
        settingsLabel.setForeground(Color.WHITE);
        settingsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(settingsLabel);

        //************* Control Label *************//
        controlTitleLabel = new JLabel("Controls");
        controlTitleLabel.setFont(FontManager.googleSansFlex.deriveFont(24f));
        controlTitleLabel.setForeground(Color.WHITE);
        add(controlTitleLabel);

        //************* Left Keybind Label *************//
        String leftText = "<html><font color='#AAAAAA'>Movement:</font> <b>WASD</b><br>"
                        + "<font color='#AAAAAA'>Gather/Attack/Build:</font> <b>Left-Click</b><br>"
                        + "<font color='#AAAAAA'>Auto-Attack:</font> <b>Space</b><br>"
                        + "<font color='#AAAAAA'>Upgrade All:</font> <b>Hold Shift</b><br>"
                        + "<font color='#AAAAAA'>Shop Menu:</font> <b>B</b></html>";
        leftControlLabel = new JLabel(leftText);
        leftControlLabel.setFont(FontManager.googleSansFlex.deriveFont(22f));   
        leftControlLabel.setForeground(Color.WHITE);
        leftControlLabel.setVerticalAlignment(SwingConstants.TOP);
        add(leftControlLabel);

        //************* Right Keybind Label *************//
        String rightText = "<html><font color='#AAAAAA'>Turn:</font> <b>Mouse</b><br>"
                         + "<font color='#AAAAAA'>Unselect:</font> <b>Right-Click</b><br>"
                         + "<font color='#AAAAAA'>Quick Upgrade:</font> <b>E</b><br>"
                         + "<font color='#AAAAAA'>Quick Heal:</font> <b>F</b><br>"
                         + "<font color='#AAAAAA'>Cycle Weapons:</font> <b>Q</b></html>";
        rightControlLabel = new JLabel(rightText);
        rightControlLabel.setFont(FontManager.googleSansFlex.deriveFont(22f));   
        rightControlLabel.setForeground(Color.WHITE);
        rightControlLabel.setVerticalAlignment(SwingConstants.TOP);
        add(rightControlLabel);

        //************* Sound Toggle Button *************//
        soundToggleButton = new RoundedJButton(""); 
        if (SoundManager.isSoundOn) {
            soundToggleButton.setText("Sound Effects: ON");
            soundToggleButton.setBackground(new Color(60, 80, 40));
        } else {
            soundToggleButton.setText("Sound Effects: OFF");
            soundToggleButton.setBackground(new Color(156, 106, 103));
        }
        soundToggleButton.setFont(FontManager.googleSansFlex.deriveFont(18f));
        soundToggleButton.setForeground(Color.WHITE);
        add(soundToggleButton);

        //************* Back Button *************//
        backButton = new RoundedJButton("Back");
        backButton.setFont(FontManager.googleSansFlex.deriveFont(20f));  
        backButton.setBackground(new Color(103, 90, 166));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        add(backButton);

        //************* Component Listener For Resizing *************//
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });

        //************* Sound Toggle Button Action Listener *************//
        soundToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.isSoundOn = !SoundManager.isSoundOn;
                if (SoundManager.isSoundOn) {
                    soundToggleButton.setText("Sound Effects: ON");
                    soundToggleButton.setBackground(new Color(60, 80, 40));
                } else {
                    soundToggleButton.setText("Sound Effects: OFF");
                    soundToggleButton.setBackground(new Color(156, 106, 103));
                }
                SoundManager.playSound("buttonClick.wav");
                System.out.println("SettingsScreen.java - Sound Effects toggled");
            }
        });

        //************* Back Button Action Listener *************//
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameInstance != null) {
                    Main.showScreen("GAME");
                    gameInstance.requestFocusInWindow();
                    System.out.println("SettingsScreen.java - Returning to game.");
                } else {
                    Main.showScreen("MENU");
                    System.out.println("SettingsScreen.java - Returning to menu.");
                }
                SoundManager.playSound("buttonClick.wav");
            }
        });
    }

    /**
     * Sets the game instance associated with this settings screen
     * Precondition: game is a valid BenumZombsGame object or null
     * Postcondition: gameInstance is set to the provided game
     * @param game the BenumZombsGame instance to associate with this settings screen
     */
    public void setGameInstance(BenumZombsGame game) {
        this.gameInstance = game;
    }

    /**
     * Updates the layout of the components based on the current size of the panel
     * Precondition: N/A
     * Postcondition: components are repositioned and resized based on screen size
     */
    private void updateLayout() {
        int width = getWidth();
        int height = getHeight();

        //************* Size and Position of Large and Small Graphical Boxes *************//
        int largeBoxW = width - 200;
        int largeBoxH = height - 200;
        int largeBoxX = (width - largeBoxW) / 2;
        int largeBoxY = (height - largeBoxH) / 2 - 20;

        int smallBoxW = largeBoxW - 60;
        int smallBoxH = largeBoxH - 100;
        int smallBoxX = largeBoxX + 30;
        int smallBoxY = largeBoxY + 80;

        //************* Set Bounds of Main Labels and Buttons *************//
        settingsLabel.setBounds(largeBoxX + 30, largeBoxY + 20, 300, 40);
        controlTitleLabel.setBounds(smallBoxX + 20, smallBoxY + 15, 200, 30);

        int labelWidth = (smallBoxW - 40) / 2;
        int labelY = smallBoxY + 60;
        int labelHeight = smallBoxH - 70;

        leftControlLabel.setBounds(smallBoxX + 20, labelY, labelWidth, labelHeight);
        rightControlLabel.setBounds(smallBoxX + 20 + labelWidth, labelY, labelWidth, labelHeight);

        soundToggleButton.setBounds((width - 200) / 2, smallBoxY + smallBoxH - 60, 200, 40);
        backButton.setBounds((width - 150) / 2, largeBoxY + largeBoxH + 50, 150, 40);
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
    
        //************* Small Box *************//
        int smallBoxW = largeBoxW - 60;
        int smallBoxH = largeBoxH - 100;
        int smallBoxX = largeBoxX + 30;
        int smallBoxY = largeBoxY + 80;

        g2d.setColor(new Color(20, 28, 13));
        g2d.fillRoundRect(smallBoxX, smallBoxY, smallBoxW, smallBoxH, 30, 30);
    }
}
