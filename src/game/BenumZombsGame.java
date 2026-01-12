/**
 * BenumZombsGame.java
 * The main game panel for BenumZombs, handling game logic and rendering
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-08
 */

package game;

import helpers.RandomGeneration;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.Timer;
import objects.Player;
import systems.HeadUpDisplay;
import systems.ResourceSystem;
import systems.ToolSystem;

public class BenumZombsGame extends JPanel implements ActionListener {
    //************* Game Entities *************//
    private final Player player;
    private final HeadUpDisplay headUpDisplay;
    private final ResourceSystem resourceSystem;
    private final ToolSystem toolSystem;

    //************* World Constants *************//
    public static final int GRID_SIZE = 35;
    public static final int OFFSET = GRID_SIZE * 20;
    public static final int PLAY_AREA = GRID_SIZE * 240;
    public static final int WORLD_AREA = PLAY_AREA + (OFFSET * 2); 
    public static final int BORDER_THICKNESS = GRID_SIZE * 50;

    //************* Instance Variables *************//
    private int waveCount;
    private final Timer gameTimer;
    private float lastTime = 0.0f;
    private boolean up, down, left, right;
    private double worldX, worldY;

    /**
     * Constructor for BenumZombsGame
     * Precondition: playerName is a valid String
     * Postcondition: BenumZombsGame panel is created with player and HUD
     * @param playerName
     */
    public BenumZombsGame(String playerName) {
        setFocusable(true);
        setBackground(new Color(105, 141, 65));

        // Start Player, HUD, resources
        toolSystem = new ToolSystem();

        Point spawn = RandomGeneration.getRandomLocation();
        player = new Player(spawn.x, spawn.y, playerName, toolSystem);

        resourceSystem = new ResourceSystem();
        resourceSystem.spawnResources(25);

        headUpDisplay = new HeadUpDisplay(player);
        waveCount = 0;

        updateCamera();

        //************* Key and Mouse Listeners *************//
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeys(e.getKeyCode(), true);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                handleKeys(e.getKeyCode(), false);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                repaint();
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                if (headUpDisplay.settingsButtonBounds != null && headUpDisplay.settingsButtonBounds.contains(p)) { // Clicked settings button
                    Main.settingsScreen.setGameInstance(BenumZombsGame.this);
                    Main.showScreen("SETTINGS");
                    return;
                }

                //shop stuff here later

                if (e.getButton() == MouseEvent.BUTTON1) { // Left click
                    player.setMouseHolding(true);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Left click
                    player.setMouseHolding(false);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        gameTimer = new Timer(16, this);
        gameTimer.start();
    }

    /**
     * Updates the camera position based on the player's position
     * Precondition: N/A
     * Postcondition: worldX and worldY are updated to center on player
     */
    private void updateCamera() {
        worldX = (getWidth() / 2) - player.getCenterX();
        worldY = (getHeight() / 2) - player.getCenterY();
    }

    /**
     * Handles key press and release events to update movement flags
     * Precondition: keyCode is a valid KeyEvent code, isPressed indicates key state
     * Postcondition: movement flags are updated based on key events
     * @param keyCode
     * @param isPressed
     */
    private void handleKeys(int keyCode, boolean isPressed) {
        if (isPressed == true && keyCode == KeyEvent.VK_SPACE) {
            player.toggleSpaceSwing();
        }
        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> up = isPressed;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = isPressed;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = isPressed;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = isPressed;
            default -> {
            }
        }
    }

    /**
     * Handle player movement, HUD update, camera update, and repaint
     * Precondition: N/A
     * Postcondition: player is moved, HUD is updated, camera is updated, panel is repainted
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //************* Moveable Area *************//
        int minX = OFFSET + BORDER_THICKNESS;
        int minY = OFFSET + BORDER_THICKNESS;
        int maxX = OFFSET + PLAY_AREA - BORDER_THICKNESS - player.getWidth();
        int maxY = OFFSET + PLAY_AREA - BORDER_THICKNESS - player.getHeight();

        player.move(up, down, left, right, minX, maxX, minY, maxY, resourceSystem);
        
        //************* Update Systems *************//
        player.updateSwing(resourceSystem);
        headUpDisplay.update();
        resourceSystem.update();

        float currentTime = headUpDisplay.getTime();    
        if (currentTime < lastTime) {
            waveCount++;
        }
        lastTime = currentTime;

        updateCamera();
        repaint();
    }

    /**
     * Draws the night overlay based on the time of day
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: night overlay is drawn on the screen
     * @param g2d
     */
    private void drawNightOverlay(Graphics2D g2d) {
        // Calculate darkness based on HUD time
        float hudTime = headUpDisplay.getTime();
        float darkness = 1.0f - Math.abs(2.0f * hudTime - 1.0f);
        int alpha = (int) (darkness * 160);

        g2d.setColor(new Color(0, 0, 20, alpha));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Returns the current wave count
     * Precondition: N/A
     * Postcondition: returns the wave count
     * @return
     */
    public int getWaveCount() {
        return waveCount;
    }

    /**
     * Paints the game panel including world, player, night overlay, and HUD
     * Precondition: g is a valid Graphics object
     * Postcondition: game elements are drawn on the panel
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.translate(worldX, worldY); // Move world based on camera

        //************* Draw World Background and Grid *************//
        g2d.setColor(new Color(105, 141, 65));
        g2d.fillRect(0, 0, WORLD_AREA, WORLD_AREA);

        g2d.setColor(new Color(85, 113, 58, 170));
        g2d.setStroke(new BasicStroke(3.2f));
        for(int i = 0; i <= WORLD_AREA; i += GRID_SIZE){
            g2d.drawLine(i, 0, i, WORLD_AREA);
        }
        for(int j = 0; j <= WORLD_AREA; j += GRID_SIZE){ 
            g2d.drawLine(0, j, WORLD_AREA, j); 
        }

        //************* Draw Play Area Borders *************//
        g2d.setColor(new Color(0, 0, 0, 130));

        g2d.fillRect(OFFSET, OFFSET, PLAY_AREA, BORDER_THICKNESS); // Top
        g2d.fillRect(OFFSET, OFFSET + PLAY_AREA - BORDER_THICKNESS, PLAY_AREA, BORDER_THICKNESS); // Bottom
        g2d.fillRect(OFFSET, OFFSET, BORDER_THICKNESS, PLAY_AREA); // Left
        g2d.fillRect(OFFSET + PLAY_AREA - BORDER_THICKNESS, OFFSET, BORDER_THICKNESS, PLAY_AREA); // Right

        resourceSystem.draw(g2d); //draw resources

        g2d.translate(-worldX, -worldY); // Reset translation for player and HUD drawing

        //************* Draw Player *************//
        double screenCenterX = getWidth() / 2 - player.getWidth() / 2;
        double screenCenterY = getHeight() / 2 - player.getHeight() / 2;
        
        Point mousePos = getMousePosition(); // Mouse position for player rotation
        if (mousePos != null) {
            player.drawAt(g2d, (int)screenCenterX, (int)screenCenterY, mousePos.x, mousePos.y);
        } else {
            player.drawAt(g2d, (int)screenCenterX, (int)screenCenterY, getWidth()/2, getHeight()/2);
        }

        //************* Draw Heads Up Display and Night *************//
        drawNightOverlay(g2d);
        headUpDisplay.draw(g2d, getWidth(), getHeight(), toolSystem, resourceSystem, waveCount);
    }
}