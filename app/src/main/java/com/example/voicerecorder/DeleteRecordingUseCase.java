package com.example.voicerecorder;

public class DeleteRecordingUseCase {
    private final AudioRepository repository;

    public DeleteRecordingUseCase(AudioRepository repository) {
        this.repository = repository;
    }

    public void execute(Recording recording) {
        repository.deleteRecording(recording);
    }
}
