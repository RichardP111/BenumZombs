/**
 * Main.java
 * The main class to start the BenumZombs game application
 * Anknowledgements: https://docs.google.com/document/d/1JccsGbz9-viOxhGuASJULYrJF2vKoy3G_eF5oNd1vVI/edit?usp=sharing 
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-04
 */

package game;

import helpers.FontManager;
import java.awt.CardLayout;
import java.net.URL;
import javax.swing.*;

public class Main {
	public static JPanel mainPanel;
    public static CardLayout cardLayout;
    public static SettingsScreen settingsScreen;
	public static ShopScreen shopScreen;
	public static BenumZombsGame gamePanel;

	public static void main(String[] args) {
		//Create main game window
		JFrame window = new JFrame("BenumZombs");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//Load Icon for the window
		try {
			URL iconURL = Main.class.getResource("/assets/images/appIcon.png"); 
			ImageIcon icon = new ImageIcon(iconURL);
			window.setIconImage(icon.getImage());
		} catch (Exception e) {
			System.err.println("Main.java - App icon not found and your code is very broke: " + e.getMessage());
		}
		
		FontManager.loadGoogleSansFlex(); // Load custom font

		//Create main panel with CardLayout to switch between screens
		cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
		settingsScreen = new SettingsScreen();
		shopScreen = new ShopScreen();

		mainPanel.add(new StartMenu(), "MENU");
        mainPanel.add(settingsScreen, "SETTINGS");
		mainPanel.add(shopScreen, "SHOP");

        window.add(mainPanel);
        window.setVisible(true);

		System.out.println("Main.java - Game started, window created.");
	}

	/**
	 * Switches the visible screen in the main window
	 * Precondition: screenName corresponds to a valid screen added to mainPanel
	 * Postcondition: The visible screen is changed to the specified screen
	 * @param screenName
	 */
	public static void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }
}