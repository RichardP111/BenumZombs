/**
 * TextFormatter.java
 * Helper class for formating numbers in BenumZombs
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-15
 */
package helpers;

import java.util.ArrayList;
import java.util.Arrays;

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

    /**
     * Wraps text into multiple lines based on maximum characters per line
     * Precondition: text is a valid String, maxCharacters is a positive integer
     * Postcondition: returns an ArrayList of wrapped text lines
     * @param input the input text to be wrapped
     * @param n the maximum number of characters per line
     * @return an ArrayList of wrapped text lines
     */
    public static ArrayList<String> wrapText(String input, int n) {      
        // Based on https://www.baeldung.com/java-wrap-string-number-characters-word-wise 
        StringBuilder stringBuilder = new StringBuilder(input);
        int index = 0;
        while(stringBuilder.length() > index + n) {
            index = stringBuilder.lastIndexOf(" ", index + n);    
            stringBuilder.replace(index, index + 1, "\n");
            index++; 
        }
        
        ArrayList<String> lines = new ArrayList<>();
        lines.addAll(Arrays.asList(stringBuilder.toString().split("\n")));
        return lines;
    }
}
