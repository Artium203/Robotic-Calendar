package org.example.VoiceHandler;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class VoiceFilters extends FileFilter {
    private final String[] audioExtensions = {".mp3", ".wav", ".ogg", ".flac", ".aac"};
    private final String[] videoExtensions = {".mp4", ".avi", ".mov", ".mkv", ".webm"};

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;

        String name = f.getName().toLowerCase();
        for (String ext : audioExtensions) {
            if (name.endsWith(ext)) return true;
        }
        for (String ext : videoExtensions) {
            if (name.endsWith(ext)) return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Audio/Video Files (*.mp3, *.wav, *.mp4, etc.)";
    }
}
