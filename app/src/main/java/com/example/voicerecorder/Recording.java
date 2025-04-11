package com.example.voicerecorder;

public class Recording {
    private final String fileName;
    private final long duration;
    private final String filePath;

    public Recording(String fileName, long duration, String filePath) {
        this.fileName = fileName;
        this.duration = duration;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public long getDuration() {
        return duration;
    }

    public String getFilePath() {
        return filePath;
    }
}

