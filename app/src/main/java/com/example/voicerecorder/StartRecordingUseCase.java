package com.example.voicerecorder;

public class StartRecordingUseCase {
    private final AudioRepository repository;

    public StartRecordingUseCase(AudioRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.startRecording();
    }
}
