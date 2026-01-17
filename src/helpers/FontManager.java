/**
 * FontManager.java
 * Helper class for managing fonts in BenumZombs
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-06
 */
package helpers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public class FontManager { 
    public static Font googleSansFlex; // The Google Sans Flex font 

    /**
     * Loads the Google Sans Flex font
     * Precondition: N/A
     * Postcondition: googleSansFlex is loaded
     */
    public static void loadGoogleSansFlex() {
        try{
            InputStream is = FontManager.class.getResourceAsStream("/assets/fonts/GoogleSansFlex-VariableFont_GRAD,ROND,opsz,slnt,wdth,wght.ttf");
            googleSansFlex = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(googleSansFlex);
        } catch (FontFormatException | IOException e) { 
            System.err.println("FontManager.java - Font very broken pls fix: " + e.getMessage());
            googleSansFlex = new Font("Arial", Font.PLAIN, 48);
        }
    }
}
