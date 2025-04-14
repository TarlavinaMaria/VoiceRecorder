package com.example.voicerecorder;

import java.util.List;

public interface AudioRepository {
    void startRecording();

    void stopRecording();

    List<Recording> getAllRecordings();

    void deleteRecording(Recording recording);
}
