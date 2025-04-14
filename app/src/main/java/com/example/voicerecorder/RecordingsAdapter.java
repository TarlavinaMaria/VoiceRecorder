package com.example.voicerecorder;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordingsAdapter extends RecyclerView.Adapter<RecordingsAdapter.ViewHolder> {

    private List<Recording> recordings = new ArrayList<>();
    private OnRecordingLongClickListener longClickListener;
    private MediaPlayer mediaPlayer = null;
    private int playingIndex = -1;

    public void setData(List<Recording> newData) {
        recordings = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recording recording = recordings.get(position);
        holder.textView.setText(recording.getFileName());

        // Подсветка проигрываемой записи
        holder.itemView.setBackgroundColor(
                playingIndex == position ? 0xFFE0E0E0 : 0xFFFFFFFF
        );

        holder.itemView.setOnClickListener(v -> {
            if (mediaPlayer != null && playingIndex == position) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                playingIndex = -1;
                notifyDataSetChanged();
                return;
            }

            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(recording.getFilePath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                playingIndex = position;
                notifyDataSetChanged();

                mediaPlayer.setOnCompletionListener(mp -> {
                    mediaPlayer.release();
                    mediaPlayer = null;
                    playingIndex = -1;
                    notifyDataSetChanged();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onRecordingLongClicked(recording);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return recordings.size();
    }

    public boolean isPlaying(Recording recording) {
        return playingIndex != -1 && recordings.get(playingIndex).equals(recording);
    }

    public void stopPlayback() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            playingIndex = -1;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    public interface OnRecordingLongClickListener {
        void onRecordingLongClicked(Recording recording);
    }

    public void setOnRecordingLongClickListener(OnRecordingLongClickListener listener) {
        this.longClickListener = listener;
    }
}
