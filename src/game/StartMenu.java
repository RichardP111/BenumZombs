/**
 * StartMenu.java
 * The start menu panel for BenumZombs, allowing players to start the game, adjust settings, or exit
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-06
 */

package game;

import helpers.FontManager;
import helpers.RoundedJButton;
import helpers.RoundedJText;
import helpers.SoundManager;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartMenu extends JPanel {

    private final JLabel titleLabel, subtitleLabel, versionLabel;
    private final RoundedJButton startButton, settingButton, exitButton;   
    private final JButton creditsButton;
    private RoundedJText nameField;

    private static String playerName = "Player";

    /**
     * Constructor for StartMenu
     * Precondition: N/A
     * Postcondition: StartMenu panel is created
     */
    @SuppressWarnings("Convert2Lambda")
    public StartMenu() {
        setLayout(null);
        setBackground(new Color(42, 56, 26));

        //************* Title Label *************//
        titleLabel = new JLabel("BenumZombs");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(FontManager.googleSansFlex.deriveFont(Font.BOLD, 60f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        //************* Slogan Label *************//
        subtitleLabel = new JLabel("Build. Defend. Survive.");
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        subtitleLabel.setFont(FontManager.googleSansFlex.deriveFont(20f));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(subtitleLabel);

        //************* Version Label *************//
        versionLabel = new JLabel("v1.0");
        versionLabel.setForeground(Color.LIGHT_GRAY);
        versionLabel.setFont(FontManager.googleSansFlex.deriveFont(15f));
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(versionLabel);

        //************* Text Box Field For Name *************//
        nameField = new RoundedJText("Enter a name");
        nameField.setFont(FontManager.googleSansFlex.deriveFont(24f));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.addMouseListener(new MouseAdapter() { //if user clicks the text field, clear default text
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nameField.getText().equals("Enter a name")) {
                    nameField.setText("");
                }
            }
        }); 
        nameField.addKeyListener(new KeyListener() { //if user types in the text field, clear default text
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
            @Override
            public void keyTyped(KeyEvent e) {
                if (nameField.getText().equals("Enter a name")) {
                    nameField.setText("");
                }
            }
        });
        add(nameField);

        //************* Start Button *************//
        startButton = new RoundedJButton("Play");
        startButton.setFont(FontManager.googleSansFlex.deriveFont(24f));
        startButton.setBackground(new Color(100, 184, 32));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        add(startButton);

        //************* Settings Button *************//
        settingButton = new RoundedJButton("Settings");
        settingButton.setFont(FontManager.googleSansFlex.deriveFont(20f));
        settingButton.setBackground(new Color(103, 90, 166));
        settingButton.setForeground(Color.WHITE);
        settingButton.setFocusPainted(false);
        add(settingButton);

        //************* Exit Button *************//
        exitButton = new RoundedJButton("Exit");
        exitButton.setFont(FontManager.googleSansFlex.deriveFont(20f));
        exitButton.setBackground(new Color(103, 90, 166));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        add(exitButton);

        //************* Credits Button *************//
        creditsButton = new JButton("Made with love by Richard  |  Credits");
        creditsButton.setFont(FontManager.googleSansFlex.deriveFont(15f));
        creditsButton.setForeground(Color.LIGHT_GRAY);
        creditsButton.setBorderPainted(false);
        creditsButton.setContentAreaFilled(false);
        creditsButton.setFocusPainted(false);
        creditsButton.setOpaque(false);
        add(creditsButton);

        //************* Component Listener For Resizing *************//
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });

        //************* Start Button Action Listener *************//
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().equals("Enter a name") || nameField.getText().trim().isEmpty()) { //set default name if none entered
                    nameField.setText("Player");
                }
                playerName = nameField.getText();

                BenumZombsGame game = new BenumZombsGame(playerName);
                Main.mainPanel.add(game, "GAME");
                
                Main.showScreen("GAME");
                game.requestFocusInWindow();
                SoundManager.playSound("buttonClick.wav");
                System.out.println("StartMenu.java - Game started from Start Menu.");
            }
        });

        //************* Settings Button Action Listener *************//
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.settingsScreen.setGameInstance(null);
                Main.showScreen("SETTINGS");
                SoundManager.playSound("buttonClick.wav");
                System.out.println("StartMenu.java - Settings shown");
            }
        });

        //************* Exit Button Action Listener *************//
        exitButton.addActionListener(new ActionListener() {
             @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("StartMenu.java - Game exited");
                SoundManager.playSound("buttonClick.wav");
                System.exit(0);
            }
        });

        //************* Credits Button Action Listener *************//
        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://docs.google.com/document/d/1JccsGbz9-viOxhGuASJULYrJF2vKoy3G_eF5oNd1vVI/edit?usp=sharing"));
                    SoundManager.playSound("buttonClick.wav");
                    System.out.println("StartMenu.java - Credits shown");
                } catch (IOException | URISyntaxException ex) {
                    System.out.println("StartMenu.java - ur credit link is super borken rn and you better fix it" + ex.getMessage());
                }
            }
        });

    }

    /**
     * Gets the player's name from the text field
     * Precondition: none
     * Postcondition: player's name is returned
     * @return
     */
    public static String getPlayerName(){
        return playerName;
    }

    /**
     * Updates the layout of the components based on the current size of the panel
     * Precondition: none
     * Postcondition: components are repositioned and resized based on screen size
     */
    private void updateLayout() {
        int width = getWidth();
        int height = getHeight();

        titleLabel.setBounds(0, height / 10, width, 50);
        subtitleLabel.setBounds(0, height / 6, width, 40);
        versionLabel.setBounds(10, 10, 30, 15);
        nameField.setBounds((width - 400) / 2, height / 2 - 75, 400, 50);
        startButton.setBounds((width - 400) / 2, height / 2 + 10, 400, 50);
        settingButton.setBounds(width - 175, height - 50, 150, 40);
        exitButton.setBounds(25, height - 50, 150, 40);
        creditsButton.setBounds((width - 300) / 2, height - 30, 300, 15);
    }

    /**
     * Paints the background graphical boxes
     * Precondition: N/A
     * Postcondition: Background boxes are painted
     * @param g the Graphics object to draw on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int boxW = 500;
        int boxH = 250;
        int boxX = (getWidth() - boxW) / 2;
        int boxY = (getHeight() - boxH) / 2;

        g2d.setColor(new Color(25, 34, 16));
        g2d.fillRoundRect(boxX, boxY, boxW, boxH, 30, 30);
    }
}
