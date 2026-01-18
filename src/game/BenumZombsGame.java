/**
 * BenumZombsGame.java
 * The main game panel for BenumZombs, handling game logic and rendering
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-08
 */
package game;

import helpers.RandomGeneration;
import helpers.SoundManager;
import java.awt.AlphaComposite;
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
import objects.Buildings.Building;
import objects.Player;
import objects.Tools.Tool;
import systems.BuildingSystem;
import systems.CollisionSystem;
import systems.HeadUpDisplay;
import systems.ResourceSystem;
import systems.ToolSystem;

public class BenumZombsGame extends JPanel implements ActionListener{
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

    //************* Placement Constants *************//
    private static final double MAX_PLACEMENT_DISTANCE = GRID_SIZE * 10;
    private static final double MAX_STASH_RANGE = GRID_SIZE * 17;
    private static final int BORDER_ZONE_BLOCKS = GRID_SIZE * 5;

    private Building placementBuilding = null; 
    private boolean isPlacing = false;
    private int ghostX, ghostY;
    private boolean isPlacementValid = false;

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
    public BenumZombsGame(String playerName){
        setFocusable(true);
        setBackground(new Color(105, 141, 65));

        //************* Systems Initialization and Player Spawn *************//
        toolSystem = new ToolSystem();
        buildingSystem = new BuildingSystem();
        resourceSystem = new ResourceSystem();
        resourceSystem.spawnResources(25);

        Point spawn; //Make sure player does not spawn on resources
        while (true){
            spawn = RandomGeneration.getRandomLocation();
            Rectangle playerHitbox = new Rectangle(spawn.x, spawn.y, 50, 50);
            
            if (!CollisionSystem.checkResourceCollision(playerHitbox, resourceSystem)){
                break; 
            }
        }
        player = new Player(spawn.x, spawn.y, playerName, toolSystem);

        headUpDisplay = new HeadUpDisplay(this, player, toolSystem);
        waveCount = 0;

        updateCamera();

        //************* Key Listeners *************//
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                handleKeys(e.getKeyCode(), true);
            }
            @Override
            public void keyReleased(KeyEvent e){
                handleKeys(e.getKeyCode(), false);
            }
        });

        //************* Mouse Listeners *************//
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                if (isPlacing){
                    updateBuildingPosition(e.getX(), e.getY());
                }
                repaint();
            }
            @Override
            public void mouseDragged(MouseEvent e){
                if (isPlacing){
                    updateBuildingPosition(e.getX(), e.getY());
                }
                repaint();
            }
        });

        addMouseListener(new MouseListener(){
            @Override
            public void mousePressed(MouseEvent e){
                Point p = e.getPoint();

                if (headUpDisplay.handleMouseClick(p)){ // Clicked HUD
                } else if (isPlacing && e.getButton() == MouseEvent.BUTTON1){ // Place building
                    placeBuilding();
                } else if (isPlacing && e.getButton() == MouseEvent.BUTTON3){ // Cancel placement
                    cancelPlacement();
                } else if (!isPlacing && e.getButton() == MouseEvent.BUTTON1){ // Start swinging
                    int realWorldX = (int)(p.x - worldX);
                    int realWorldY = (int)(p.y - worldY);
                    
                    boolean selected = buildingSystem.selectBuildingAt(realWorldX, realWorldY);
                    
                    if (!selected) {
                        player.setMouseHolding(true);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                if (e.getButton() == MouseEvent.BUTTON1){ // Stop swinging
                    player.setMouseHolding(false);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e){}
        });

        gameTimer = new Timer(16, this);
        gameTimer.start();
    }

    /**
     * Updates the camera position based on the player's position
     * Precondition: N/A
     * Postcondition: worldX and worldY are updated to center on player
     */
    private void updateCamera(){
        worldX = (getWidth() / 2) - player.getCenterX();
        worldY = (getHeight() / 2) - player.getCenterY();
    }

    /**
     * Returns the Player instance
     * Precondition: N/A
     * Postcondition: returns the player
     * @return the player instance
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Returns the BuildingSystem instance
     * Precondition: N/A
     * Postcondition: returns the building system
     * @return the building system instance
     */
    public BuildingSystem getBuildingSystem(){
        return buildingSystem;
    }

/**
     * Returns the ToolSystem instance
     * Precondition: N/A
     * Postcondition: returns the tool system
     * @return the tool system instance
     */
    public ToolSystem getToolSystem(){
        return this.toolSystem; 
    }

    /**
     * Returns the ResourceSystem instance
     * Precondition: N/A
     * Postcondition: returns the resource system
     * @return the resource system instance
     */
    public ResourceSystem getResourceSystem(){
        return this.resourceSystem; 
    }

    /**
     * Returns the current wave count
     * Precondition: N/A
     * Postcondition: returns the wave count
     * @return the current wave count
     */
    public int getWaveCount(){
        return waveCount;
    }

    /**
     * Draws the night overlay based on the time of day
     * Precondition: g2d is a valid Graphics2D object
     * Postcondition: night overlay is drawn on the screen
     * @param g2d the Graphics2D object used for drawing
     */
    private void drawNightOverlay(Graphics2D g2d){
        float hudTime = headUpDisplay.getTime(); // Calculate darkness based on HUD time
        float darkness = 1.0f - Math.abs(2.0f * hudTime - 1.0f);
        int alpha = (int) (darkness * 160);

        g2d.setColor(new Color(0, 0, 20, alpha));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Handles key press and release events to update movement flags
     * Precondition: keyCode is a valid KeyEvent code, isPressed indicates key state
     * Postcondition: movement flags are updated based on key events
     * @param keyCode    key code of the pressed/released key
     * @param isPressed  true if key is pressed, false if released
     */
    private void handleKeys(int keyCode, boolean isPressed){
        if (isPressed){
            if (keyCode == KeyEvent.VK_SPACE){ // Toggle space swing
                player.toggleSpaceSwing();
            }

            if (keyCode == KeyEvent.VK_ESCAPE){ // Cancel placement
                cancelPlacement();
            }

            if (keyCode == KeyEvent.VK_B){ // Open shop
                Main.shopScreen.setGameInstance(BenumZombsGame.this);
                Main.showScreen("SHOP");
                System.out.println("BenumZombsGame.java - Shop Opened via 'B'");
                return;
            }

            if (keyCode == KeyEvent.VK_Q){ // Cycle tools
                int current = toolSystem.getActiveSlotIndex();
                for (int i = 1; i <= 4; i++){
                    int nextSlot = (current + i) % 4;
                    Tool nextTool = toolSystem.getToolInSlot(nextSlot);
                    if (nextTool != null && nextTool.getIsUnlocked()){
                        toolSystem.setActiveSlot(nextSlot);
                        break; 
                    }
                }
            }

            if (keyCode == KeyEvent.VK_F){ // Quick use potion
                player.usePotion();
            }

            if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) { // Building hotkeys
                int index = keyCode - KeyEvent.VK_0;
                Building building = buildingSystem.getBuildingInSlot(index);
                if (building != null && !building.isLocked()) {
                    if (buildingSystem.isLimitReached(building)) {
                        System.out.println("BenumZombsGame.java - Building Limit Reached");
                        return;
                    }
                    if (!building.isUnlocker()) {
                        if (resourceSystem.getWoodCount() < building.getWoodCost() || resourceSystem.getStoneCount() < building.getStoneCost()) {
                            System.out.println("BenumZombsGame.java - Not Enough Resources");
                            return;
                        }
                    }
                    startPlacement(building);
                    SoundManager.playSound("buttonClick.wav");
                }
            }

            if (keyCode == KeyEvent.VK_F12){ // Super secret dev mode key
                resourceSystem.devModeAddResources();
                System.out.println("BenumZombsGame.java - Dev Mode activated");
            }
        }
        
        //************* Movement Keys *************//
        switch (keyCode){
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
    public void actionPerformed(ActionEvent e){
        //************* Moveable Area *************//
        int minX = OFFSET + BORDER_THICKNESS;
        int minY = OFFSET + BORDER_THICKNESS;
        int maxX = OFFSET + PLAY_AREA - BORDER_THICKNESS - player.getWidth();
        int maxY = OFFSET + PLAY_AREA - BORDER_THICKNESS - player.getHeight();

        player.move(up, down, left, right, minX, maxX, minY, maxY, resourceSystem, buildingSystem);
        
        //************* Update Systems *************//
        player.update();
        player.updateSwing(resourceSystem);
        headUpDisplay.update();
        resourceSystem.update();

        float currentTime = headUpDisplay.getTime();    
        if (currentTime < lastTime){
            waveCount++;
        }
        lastTime = currentTime;

        updateCamera();
        repaint();
    }

    /**
     * Starts the building placement mode for the specified building
     * Precondition: building is a valid Building object
     * Postcondition: building placement mode is initiated
     * @param building the building to be placed
     */
    public void startPlacement(Building building){
        this.placementBuilding = building;
        this.isPlacing = true;
        
        Point mouse = getMousePosition();
        if (mouse != null){
            updateBuildingPosition(mouse.x, mouse.y);
        }
    }

    /**
     * Cancels the current building placement mode
     * Precondition: N/A
     * Postcondition: building placement mode is cancelled
     */
    private void cancelPlacement(){
        this.isPlacing = false;
        this.placementBuilding = null;
        repaint();
    }

    /**
     * Updates the position of the building ghost based on mouse coordinates
     * Precondition: mouseX and mouseY are valid screen coordinates
     * Postcondition: ghostX and ghostY are updated to the nearest grid position isPlacementValid is updated based on placement validity
     * @param mouseX the x-coordinate of the mouse
     * @param mouseY the y-coordinate of the mouse
     */
    private void updateBuildingPosition(int mouseX, int mouseY){
        if (placementBuilding == null){
            return;
        }

        //************* Calculate real world position *************//
        double cameraX = mouseX - worldX;
        double cameraY = mouseY - worldY;

        this.ghostX = (int) (Math.floor(cameraX / GRID_SIZE) * GRID_SIZE);
        this.ghostY = (int) (Math.floor(cameraY / GRID_SIZE) * GRID_SIZE);
        Rectangle ghostRect = new Rectangle(ghostX, ghostY, placementBuilding.getWidth(), placementBuilding.getHeight());
        
        //************* Border Restrictions *************//
        int playStart = OFFSET + BORDER_THICKNESS;
        int playEnd = OFFSET + PLAY_AREA - BORDER_THICKNESS;
        int minX = playStart + BORDER_ZONE_BLOCKS;
        int minY = playStart + BORDER_ZONE_BLOCKS;
        int maxX = playEnd - BORDER_ZONE_BLOCKS;
        int maxY = playEnd - BORDER_ZONE_BLOCKS;
        
        boolean insideBounds = (ghostX >= minX && ghostX + placementBuilding.getWidth() <= maxX && ghostY >= minY && ghostY + placementBuilding.getHeight() <= maxY);

        //************* Collision Checks *************//
        Rectangle playerRect = new Rectangle((int)player.getX(), (int)player.getY(), player.getWidth(), player.getHeight());

        boolean collidesResource = CollisionSystem.checkResourceCollision(ghostRect, resourceSystem);
        boolean collidesBuilding = CollisionSystem.checkBuildingCollision(ghostRect, buildingSystem);
        boolean collidesPlayer = ghostRect.intersects(playerRect);

        //************* Range Checks *************//
        // Distance from player
        double buildingCenterX = ghostX + (placementBuilding.getWidth() / 2.0);
        double buildingCenterY = ghostY + (placementBuilding.getHeight() / 2.0);
        double distance = Math.sqrt(Math.pow(buildingCenterX - player.getCenterX(), 2) + Math.pow(buildingCenterY - player.getCenterY(), 2));
        boolean inPlayerRange = distance <= MAX_PLACEMENT_DISTANCE;

        //Distance from Stash
        boolean inStashRange = true;
        Building stash = buildingSystem.getActiveStash();
        if (stash != null && !placementBuilding.isUnlocker()){
            double sCenterX = stash.getX() + (stash.getWidth() / 2.0);
            double sCenterY = stash.getY() + (stash.getHeight() / 2.0);
            double distanceStash = Math.sqrt(Math.pow(buildingCenterX - sCenterX, 2) + Math.pow(buildingCenterY - sCenterY, 2));
            inStashRange = distanceStash <= MAX_STASH_RANGE;
        }

        boolean limitReached = buildingSystem.isLimitReached(placementBuilding); // Check building number limit

        this.isPlacementValid = insideBounds && !collidesResource && !collidesBuilding && !collidesPlayer && inPlayerRange && inStashRange && !limitReached; // Final result
    }

    /**
     * Places the building at the ghost position if placement is valid
     * Precondition: isPlacementValid is true, placementBuilding is not null
     * Postcondition: building is placed, resources are deducted, placement mode is updated
     */
    private void placeBuilding(){
        if (!isPlacementValid || placementBuilding == null){
            System.out.println("BenumZombsGame.java - Invalid placement attempted");
            return;
        }

        //************* Deduct Resources *************//
        resourceSystem.addWood(-placementBuilding.getWoodCost());
        resourceSystem.addStone(-placementBuilding.getStoneCost());

        //************* Place Building *************//
        Building newBuilding = buildingSystem.createBuilding(placementBuilding, ghostX, ghostY);
        buildingSystem.placeBuilding(newBuilding);

        //************* Check for Limit, Gold Stash, and Resources *************//
        boolean limitReached = buildingSystem.isLimitReached(placementBuilding);
        if (placementBuilding.isUnlocker() || limitReached){
            cancelPlacement();
            return;
        }
        
        if (resourceSystem.getWoodCount() < placementBuilding.getWoodCost() || resourceSystem.getStoneCount() < placementBuilding.getStoneCost()){
            cancelPlacement();
        }
    }


    /**
     * Paints the game panel including world, player, night overlay, and HUD
     * Precondition: g is a valid Graphics object
     * Postcondition: game elements are drawn on the panel
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g){
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

        resourceSystem.draw(g2d); // Draw resources
        buildingSystem.draw(g2d); // Draw buildings
        player.drawProjectiles(g2d);

        //************* Draw Building Placement Ghost with Transparency Effects *************//
        if (isPlacing && placementBuilding != null){
            
            Building ghost = buildingSystem.createBuilding(placementBuilding, ghostX, ghostY);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            if (isPlacementValid){
                ghost.draw(g2d);
            } else {
                ghost.draw(g2d);
                
                g2d.setColor(new Color(255, 0, 0, 100)); 
                g2d.fillRect(ghostX, ghostY, ghost.getWidth(), ghost.getHeight());
            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Reset alpha
        }

        g2d.translate(-worldX, -worldY); // Reset translation for player and HUD drawing

        //************* Draw Player *************//
        double screenCenterX = getWidth() / 2 - player.getWidth() / 2;
        double screenCenterY = getHeight() / 2 - player.getHeight() / 2;
        
        Point mousePos = getMousePosition(); // Mouse position for player rotation
        if (mousePos != null){
            player.drawAt(g2d, (int)screenCenterX, (int)screenCenterY, mousePos.x, mousePos.y);
        } else {
            player.drawAt(g2d, (int)screenCenterX, (int)screenCenterY, getWidth()/2, getHeight()/2);
        }

        //************* Draw Heads Up Display and Night *************//
        drawNightOverlay(g2d);
        headUpDisplay.draw(g2d, getWidth(), getHeight(), toolSystem, resourceSystem, waveCount);
    }
}