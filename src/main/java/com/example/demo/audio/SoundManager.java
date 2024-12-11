package com.example.demo.audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.IOException;

public class SoundManager {

    private static SoundManager instance;
    private Clip clip;
    private FloatControl volumeControl;
    private boolean isPlaying = false;
    private static final float DEFAULT_VOLUME = -10.0f;

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

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

    public void setVolume(float value) {
        if (volumeControl != null) {
            volumeControl.setValue(value);
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
        }
    }

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

    public float getVolume() {
        return volumeControl != null ? volumeControl.getValue() : 0;
    }
}
