package com.example.voicerecorder;

public class StopRecordingUseCase {
    private final AudioRepository repository;

    public StopRecordingUseCase(AudioRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.stopRecording();
    }
}
