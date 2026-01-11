/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Main class
 */

package game;

import helpers.FontManager;
import java.net.URL;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		//Create main game window
		JFrame window = new JFrame("BenumZombs");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//Load Icon for the window
		URL iconURL = Main.class.getResource("/assets/images/appIcon.png"); 
		if (iconURL != null) {
			ImageIcon icon = new ImageIcon(iconURL);
			window.setIconImage(icon.getImage());
		} else {
			System.err.println("Main.java - App icon not found and your code is very broke");
		}
		
		FontManager.loadGoogleSansFlex(); // Load custom font

		//Load Start Menu
		StartMenu startMenu = new StartMenu(window);
		window.add(startMenu);
		window.setVisible(true);

		System.out.println("Main.java - Game started, window created.");
	}
}