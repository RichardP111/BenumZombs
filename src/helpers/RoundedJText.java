/**
 * RoundedJText.java
 * Helper class for creating rounded JTextField components in BenumZombs
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-06
 */

package helpers;

import java.awt.*;
import javax.swing.*;

public class RoundedJText extends JTextField {
    /**
     * Constructor for RoundedJText
     * Precondition: text is a valid String
     * Postcondition: creates a RoundedJText with the specified text
     * @param text
     */
    public RoundedJText(String text) {
        super(text);
        setOpaque(false);
        setBorder(null);
    }

    /**
     * Paints the RoundedJText
     * Precondition: g is a valid Graphics object
     * Postcondition: paints the RoundedJText with rounded corners
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40); 
        super.paintComponent(g2);
        g2.dispose();
    }
}
