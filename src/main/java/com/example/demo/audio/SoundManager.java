package com.example.demo.audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.IOException;

/**
 * Manages the background music and volume control for the game.
 * <p>
 * The {@link SoundManager} class is a singleton that handles the playback of background music
 * and allows for volume adjustments. It ensures that only one instance of the manager exists
 * throughout the game.
 * </p>
 */
public class SoundManager {

    private static SoundManager instance;
    private Clip clip;
    private FloatControl volumeControl;
    private boolean isPlaying = false;
    private static final float DEFAULT_VOLUME = -10.0f;

    /**
     * Retrieves the singleton instance of the {@link SoundManager}.
     * <p>
     * If an instance of {@link SoundManager} does not exist, a new one will be created.
     * </p>
     *
     * @return the singleton instance of the {@link SoundManager}.
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Plays the background music from the specified file path.
     * <p>
     * The music will loop continuously until {@link #stopMusic()} is called. The volume will
     * be set to a default level of {@link #DEFAULT_VOLUME}.
     * </p>
     *
     * @param path the relative path to the audio file to be played.
     * @throws IllegalArgumentException if the audio file is not found at the specified path.
     */
    public void PlayMusic(String path) {
        try {
            if (isPlaying) {
                return;
            }

            URL audioFile = getClass().getResource(path);
            if (audioFile == null) {
                throw new IllegalArgumentException("Audio file not found: " + path);
            }

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);

            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(DEFAULT_VOLUME);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the volume for the music.
     * <p>
     * This method adjusts the volume of the music to the specified value.
     * </p>
     *
     * @param value the desired volume level.
     */
    public void setVolume(float value) {
        if (volumeControl != null) {
            volumeControl.setValue(value);
        }
    }

    /**
     * Stops the background music playback.
     * <p>
     * If the music is currently playing, it will be stopped and marked as not playing.
     * </p>
     */
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
        }
    }

    /**
     * Increases the volume by the specified increment.
     * <p>
     * The volume will not exceed the maximum allowed by the {@link FloatControl}.
     * </p>
     *
     * @param increment the amount by which the volume will be increased.
     */
    public void increaseVolume(float increment) {
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            float newVolume = Math.min(currentVolume + increment, volumeControl.getMaximum());
            volumeControl.setValue(newVolume);
            System.out.println("Increased Volume: " + newVolume);
        } else {
            System.out.println("Volume control is not available. Cannot increase volume.");
        }
    }

    /**
     * Decreases the volume by the specified decrement.
     * <p>
     * The volume will not go below the minimum allowed by the {@link FloatControl}.
     * </p>
     *
     * @param decrement the amount by which the volume will be decreased.
     */
    public void decreaseVolume(float decrement) {
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            float newVolume = Math.max(currentVolume - decrement, volumeControl.getMinimum());
            volumeControl.setValue(newVolume);
            System.out.println("Decreased Volume: " + newVolume);
        } else {
            System.out.println("Volume control is not available. Cannot decrease volume.");
        }
    }

    /**
     * Retrieves the current volume level.
     *
     * @return the current volume level, or 0 if volume control is unavailable.
     */
    public float getVolume() {
        return volumeControl != null ? volumeControl.getValue() : 0;
    }
}
