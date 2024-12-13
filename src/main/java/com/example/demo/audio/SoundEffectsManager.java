package com.example.demo.audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.IOException;

/**
 * Manages the playback of sound effects in the game.
 * <p>
 * The {@link SoundEffectsManager} class is responsible for loading and playing audio
 * files in the game. It uses the {@link Clip} class to play sounds and handles
 * loading audio from resources.
 * </p>
 */
public class SoundEffectsManager {
    private Clip clip;

    /**
     * Plays a sound effect from the given file path.
     * <p>
     * The method loads an audio file from the provided path and plays it using
     * the {@link Clip} class. If the audio file cannot be found or an error occurs
     * during playback, an exception is printed to the console.
     * </p>
     *
     * @param path the relative path to the audio file to be played
     * @throws IllegalArgumentException if the audio file is not found at the specified path
     */
    public void playSound(String path) {
        try {
            URL audioFile = getClass().getResource(path);
            if (audioFile == null) {
                throw new IllegalArgumentException("Audio file not found: " + path);
            }

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);

            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
