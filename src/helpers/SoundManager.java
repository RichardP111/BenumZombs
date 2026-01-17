/**
 * SoundManager.java
 * Helper class for managing sounds in BenumZombs
 * @author Richard Pu
 * @version 1.0
 * @since 2026-01-07
 */

package helpers;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
    public static boolean isSoundOn = true; // Sound on/off boolean

    /**
     * Plays a sound from the specified sound file if sound is enabled.
     * Precondition: soundFile exists in /assets/sounds/
     * Postcondition: sound is played if isSoundOn is true
     * @param soundFile the name of the sound file to play
     */
    public static void playSound(String soundFile) {
        if (!isSoundOn) { //check if sound is enabled
            return;
        }

        try {
            String path = "/assets/sounds/" + soundFile;
            URL url = SoundManager.class.getResource(path);

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.err.println("SoundManager.java - why does your code keep breaking, error playing " + soundFile + ":" + e.getMessage());
        }
    }
}
