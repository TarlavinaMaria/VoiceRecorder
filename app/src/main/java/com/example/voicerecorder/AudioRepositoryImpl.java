package com.example.voicerecorder;

import java.util.List;

public class AudioRepositoryImpl implements AudioRepository {
    private final LocalAudioDataSource localDataSource;

    public AudioRepositoryImpl(LocalAudioDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    @Override
    public void startRecording() {
        localDataSource.startRecording();
    }

    @Override
    public void stopRecording() {
        localDataSource.stopRecording();
    }

    @Override
    public List<Recording> getAllRecordings() {
        return localDataSource.getAllRecordings();
    }
}
