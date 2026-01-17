/**
 * TextFormatter.java
 * Helper class for formating numbers in BenumZombs
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-15
 */
package helpers;

public class TextFormatter { 
    /**
     * Formats an integer value into a more readable string with 'k' for thousands and 'M' for millions and 'B' for billions
     * Precondition: value is a non-negative integer
     * Postcondition: returns a formatted string
     * @param value the integer value to format
     * @return the formatted string
     */
    public static String formatValue(int value) {
        if (value >= 1000000000) {
            return String.format("%.1fB", (double) value / 1000000000);
        } else if (value >= 1000000) {
            return String.format("%.1fM", (double) value / 1000000);
        } else if (value >= 1000) {
            return String.format("%.1fk", (double) value / 1000);
        } else {
            return String.valueOf(value);
        }
    }
}
