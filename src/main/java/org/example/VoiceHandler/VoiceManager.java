package org.example.VoiceHandler;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VoiceManager {
    private static final List<Clip> activeClips = new ArrayList<>();

    public static void playSound(long startMicroseconds, long durationMillis) throws Exception {
        stopAll();

        File audioFile = new File("src/main/Python_.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.setMicrosecondPosition(startMicroseconds);
        clip.start();

        activeClips.add(clip);

        new Thread(() -> {
            try {
                Thread.sleep(durationMillis);
                clip.stop();
                clip.close();
                activeClips.remove(clip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void stopAll() {
        for (Clip clip : activeClips) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.close();
        }
        activeClips.clear();
    }
}
