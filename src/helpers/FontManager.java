/**
 * @author Richard Pu
 * @version 1.0
 * 2026-01-19
 * BenumZombs - Helper class for managing fonts
 */

package helpers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontManager { 
    public static Font googleSansFlex; // The Google Sans Flex font 

    /**
     * Loads the Google Sans Flex font
     * Precondition: none
     * Postcondition: googleSansFlex is loaded
     */
    public static void loadGoogleSansFlex() {
        try{
            googleSansFlex = Font.createFont(Font.TRUETYPE_FONT, new File("/assets/fonts/GoogleSansFlex-VariableFont_GRAD,ROND,opsz,slnt,wdth,wght.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(googleSansFlex);
        } catch (FontFormatException | IOException e) { //if font fails to load
            System.err.println("StartMenu.java - Font very broken pls fix: " + e.getMessage());
            googleSansFlex = new Font("Arial", Font.PLAIN, 48);
        }
    }
}
