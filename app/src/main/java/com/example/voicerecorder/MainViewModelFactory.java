package com.example.voicerecorder;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    Context context;

    public MainViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        LocalAudioDataSource dataSource = new LocalAudioDataSource(context);
        AudioRepository repository = new AudioRepositoryImpl(dataSource);
        return (T) new MainViewModel(new StartRecordingUseCase(repository),
                new StopRecordingUseCase(repository),
                new GetRecordingsUseCase(repository),
                new DeleteRecordingUseCase(repository)
        );
    }
}
