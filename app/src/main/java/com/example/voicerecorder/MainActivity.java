package com.example.voicerecorder;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    MainViewModel viewModel;
    Button btnStart;
    Button btnStop;
    RecyclerView recyclerView;
    RecordingsAdapter recordingsAdapter;
    LocalAudioDataSource dataSource;
    AudioRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        recyclerView = findViewById(R.id.recycler_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.RECORD_AUDIO}, 1001);
            }
        }

        dataSource = new LocalAudioDataSource(this);
        repository = new AudioRepositoryImpl(dataSource);
        //Без DI-ошибка
//        viewModel = new MainViewModel(new StartRecordingUseCase(repository),
//                new StopRecordingUseCase(repository),new GetRecordingsUseCase(repository));
        viewModel = new ViewModelProvider(this, new MainViewModelFactory(this)).get(MainViewModel.class);
        recordingsAdapter = new RecordingsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recordingsAdapter);

        btnStart.setOnClickListener(v -> {
            viewModel.startRecording();
        });

        btnStop.setOnClickListener(v -> {
            viewModel.stopRecording();
        });
        viewModel.getRecordings().observe(this, recordings -> {
            recordingsAdapter.setData(recordings);
        });
        viewModel.loadRecordings();
        recordingsAdapter.setOnRecordingLongClickListener(recording -> {
            new AlertDialog.Builder(this)
                    .setTitle("Удалить запись?")
                    .setMessage("Точно удалить " + recording.getFileName() + "?")
                    .setPositiveButton("Удалить", (dialog, which) -> {
                        if (recordingsAdapter.isPlaying(recording)) {
                            recordingsAdapter.stopPlayback();
                        }
                        viewModel.deleteRecording(recording);
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        });
    }
}
