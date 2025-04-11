package com.example.voicerecorder;

import java.util.List;

public class GetRecordingsUseCase {
    private final AudioRepository repository;

    public GetRecordingsUseCase(AudioRepository repository) {
        this.repository = repository;
    }

    public List<Recording> execute() {
        return repository.getAllRecordings();
    }
}
