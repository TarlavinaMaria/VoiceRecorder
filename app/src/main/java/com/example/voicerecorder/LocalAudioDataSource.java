package com.example.voicerecorder;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalAudioDataSource {

    private final Context context;
    private MediaRecorder recorder;
    private String currentFilePath;
    private long startTime;

    public LocalAudioDataSource(Context context) {
        this.context = context;
    }

    public void startRecording() {
        try {
            String fileName = "rec_" + System.currentTimeMillis() + ".3gp";
            File outputDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            File outputFile = new File(outputDir, fileName);

            currentFilePath = outputFile.getAbsolutePath();

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(currentFilePath);
            recorder.prepare();
            recorder.start();

            startTime = System.currentTimeMillis();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    public List<Recording> getAllRecordings() {
        List<Recording> recordings = new ArrayList<>();
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        if (dir != null && dir.exists()) {
            for (File file : dir.listFiles()) {
                long duration = file.length(); // Для простоты, но позже можно использовать MediaMetadataRetriever
                recordings.add(new Recording(file.getName(), duration, file.getAbsolutePath()));
            }
        }
        return recordings;
    }
}
