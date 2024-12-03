package com.example.demo.audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.IOException;

public class SoundEffectsManager {
    private Clip clip;

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