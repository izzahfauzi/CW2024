package com.example.demo.audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.IOException;

public class SoundManager {

    private Clip clip;
    private boolean isPlaying = false;

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

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
        }
    }

    public void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void resumeMusic() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

}
