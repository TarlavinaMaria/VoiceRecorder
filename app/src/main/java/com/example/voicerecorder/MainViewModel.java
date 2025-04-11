package com.example.voicerecorder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final StartRecordingUseCase startRecordingUseCase;
    private final StopRecordingUseCase stopRecordingUseCase;
    private final GetRecordingsUseCase getRecordingsUseCase;

    private final MutableLiveData<List<Recording>> recordings = new MutableLiveData<>();

    public MainViewModel(
            StartRecordingUseCase startRecordingUseCase,
            StopRecordingUseCase stopRecordingUseCase,
            GetRecordingsUseCase getRecordingsUseCase
    ) {
        this.startRecordingUseCase = startRecordingUseCase;
        this.stopRecordingUseCase = stopRecordingUseCase;
        this.getRecordingsUseCase = getRecordingsUseCase;
    }

    public void startRecording() {
        startRecordingUseCase.execute();
    }

    public void stopRecording() {
        stopRecordingUseCase.execute();
        loadRecordings();
    }

    public void loadRecordings() {
        recordings.setValue(getRecordingsUseCase.execute());
    }

    public LiveData<List<Recording>> getRecordings() {
        return recordings;
    }
}
