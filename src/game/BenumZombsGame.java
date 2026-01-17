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
import java.awt.Rectangle;
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
import objects.Tools.Tool;
import systems.BuildingSystem;
import systems.CollisionSystem;
import systems.HeadUpDisplay;
import systems.ResourceSystem;
import systems.ToolSystem;

public class BenumZombsGame extends JPanel implements ActionListener {
    //************* Game Entities *************//
    private final Player player;
    private final HeadUpDisplay headUpDisplay;
    private final ResourceSystem resourceSystem;
    private final ToolSystem toolSystem;
    private final BuildingSystem buildingSystem;

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
     * @param playerName The name of the player
     */
    public BenumZombsGame(String playerName) {
        setFocusable(true);
        setBackground(new Color(105, 141, 65));

        //************* Systems Initialization and Player Spawn *************//
        toolSystem = new ToolSystem();
        buildingSystem = new BuildingSystem();
        resourceSystem = new ResourceSystem();
        resourceSystem.spawnResources(25);

        Point spawn;
        while (true) {
            spawn = RandomGeneration.getRandomLocation();
            Rectangle playerHitbox = new Rectangle(spawn.x, spawn.y, 50, 50);
            
            if (!CollisionSystem.checkCollision(playerHitbox, resourceSystem)) {
                break; 
            }
        }
        player = new Player(spawn.x, spawn.y, playerName, toolSystem);

        headUpDisplay = new HeadUpDisplay(this, player, toolSystem);
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
                    System.out.println("BenumZombsGame.java - Opening Settings Screen from Game.");
                    return;
                }

                if (headUpDisplay.shopButtonBounds != null && headUpDisplay.shopButtonBounds.contains(p)) { // Clicked shop button
                    Main.shopScreen.setGameInstance(BenumZombsGame.this);
                    Main.showScreen("SHOP");
                    System.out.println("BenumZombsGame.java - Opening Shop Screen from Game.");
                    return;
                }

                if (headUpDisplay.handleToolbarClick(p)) {
                    return; 
                }

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
     * Returns the Player instance
     * Precondition: N/A
     * Postcondition: returns the player
     * @return the player instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the BuildingSystem instance
     * Precondition: N/A
     * Postcondition: returns the building system
     * @return the building system instance
     */
    public BuildingSystem getBuildingSystem() {
        return buildingSystem;
    }

/**
     * Returns the ToolSystem instance
     * Precondition: N/A
     * Postcondition: returns the tool system
     * @return the tool system instance
     */
    public ToolSystem getToolSystem() {
        return this.toolSystem; 
    }

    /**
     * Returns the ResourceSystem instance
     * Precondition: N/A
     * Postcondition: returns the resource system
     * @return the resource system instance
     */
    public ResourceSystem getResourceSystem() {
        return this.resourceSystem; 
    }

    /**
     * Handles key press and release events to update movement flags
     * Precondition: keyCode is a valid KeyEvent code, isPressed indicates key state
     * Postcondition: movement flags are updated based on key events
     * @param keyCode    key code of the pressed/released key
     * @param isPressed  true if key is pressed, false if released
     */
    private void handleKeys(int keyCode, boolean isPressed) {
        if (isPressed) {
            if (keyCode == KeyEvent.VK_SPACE) { // Toggle space swing
                player.toggleSpaceSwing();
            }

            if (keyCode == KeyEvent.VK_B) { // Open shop
                Main.shopScreen.setGameInstance(BenumZombsGame.this);
                Main.showScreen("SHOP");
                System.out.println("BenumZombsGame.java - Shop Opened via 'B'");
                return;
            }

            if (keyCode == KeyEvent.VK_Q) { // Cycle tools
                int current = toolSystem.getActiveSlotIndex();
                for (int i = 1; i <= 4; i++) {
                    int nextSlot = (current + i) % 4;
                    Tool nextTool = toolSystem.getToolInSlot(nextSlot);
                    if (nextTool != null && nextTool.getIsUnlocked()) {
                        toolSystem.setActiveSlot(nextSlot);
                        break; 
                    }
                }
            }

            if (keyCode == KeyEvent.VK_F) { // Quick use potion
                player.usePotion();
            }
        }
        
        //************* Movement Keys *************//
        switch (keyCode) {
        case KeyEvent.VK_W, KeyEvent.VK_UP:
            up = isPressed;
            break;
        case KeyEvent.VK_S, KeyEvent.VK_DOWN:
            down = isPressed;
            break;
        case KeyEvent.VK_A, KeyEvent.VK_LEFT:
            left = isPressed;
            break;
        case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
            right = isPressed;
            break;
        default:
        }
    }

    /**
     * Handle player movement, HUD update, camera update, and repaint
     * Precondition: N/A
     * Postcondition: player is moved, HUD is updated, camera is updated, panel is repainted
     * @param e the action event triggering the update
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
        player.update();
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
     * @param g2d the Graphics2D object used for drawing
     */
    private void drawNightOverlay(Graphics2D g2d) {
        float hudTime = headUpDisplay.getTime(); // Calculate darkness based on HUD time
        float darkness = 1.0f - Math.abs(2.0f * hudTime - 1.0f);
        int alpha = (int) (darkness * 160);

        g2d.setColor(new Color(0, 0, 20, alpha));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Returns the current wave count
     * Precondition: N/A
     * Postcondition: returns the wave count
     * @return the current wave count
     */
    public int getWaveCount() {
        return waveCount;
    }

    /**
     * Paints the game panel including world, player, night overlay, and HUD
     * Precondition: g is a valid Graphics object
     * Postcondition: game elements are drawn on the panel
     * @param g the Graphics object used for drawing
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
        player.drawProjectiles(g2d);

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