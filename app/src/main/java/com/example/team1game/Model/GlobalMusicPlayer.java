package com.example.team1game.Model;

import android.content.Context;
import android.media.MediaPlayer;

public class GlobalMusicPlayer {
    private static GlobalMusicPlayer instance;
    private MediaPlayer mediaPlayer;
    public GlobalMusicPlayer(Context context, int resourceId) {
        mediaPlayer = MediaPlayer.create(context, resourceId);
        mediaPlayer.setLooping(true);
    }
    // singleton
    public static synchronized GlobalMusicPlayer getInstance(Context context, int resourceId) {
        if (instance == null) {
            instance = new GlobalMusicPlayer(context.getApplicationContext(), resourceId);
        }
        return instance;
    }
    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}