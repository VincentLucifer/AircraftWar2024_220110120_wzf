package com.example.aircraftwar2024_220110120_wzf.music;

import android.media.MediaPlayer;
import android.content.Context;
import com.example.aircraftwar2024_220110120_wzf.R;

public class MyMediaPlayer {

    public static final int BACKGROUND_MUSIC_RES_ID = R.raw.bgm;
    public static final int BOSS_MUSIC_RES_ID = R.raw.bgm_boss;
    private final String musicPreference;
    private Context context;
    private MediaPlayer bgmMusic = null;
    private MediaPlayer bossMusic;
    private int currentPosition = 0; // 用于保存音乐暂停时的位置

    public MyMediaPlayer(Context context, String musicPreference){
        this.musicPreference = musicPreference;
        this.context = context;
    }

    public void startBgmMusic() {
        if(musicPreference.equals("enable") && bgmMusic == null) {
            try {
                bgmMusic = MediaPlayer.create(context, BACKGROUND_MUSIC_RES_ID);
                bgmMusic.setLooping(true);
                bgmMusic.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void pauseBackgroundMusic() {
        if (bgmMusic != null && bgmMusic.isPlaying()) {
            currentPosition = bgmMusic.getCurrentPosition();
            bgmMusic.pause();
        }
    }

    public void continueBackgroundMusic() {
        if (bgmMusic != null) {
            bgmMusic.seekTo(currentPosition);
            bgmMusic.start();
        } else if (musicPreference.equals("enable")) {
            // 如果bgmMusic为空且音乐偏好启用，则重新创建MediaPlayer并从当前位置播放
            try {
                bgmMusic = MediaPlayer.create(context, BACKGROUND_MUSIC_RES_ID);
                bgmMusic.setLooping(true);
                bgmMusic.seekTo(currentPosition);
                bgmMusic.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void finalStopBackgroundMusic() {
        if (bgmMusic != null && bgmMusic.isPlaying()) {
            bgmMusic.stop();
            bgmMusic.release();
            bgmMusic = null;
        }
    }

    public void startBossMusic() {
        if(musicPreference.equals("enable")) {
            pauseBackgroundMusic();
            try {
                bossMusic = MediaPlayer.create(context, BOSS_MUSIC_RES_ID);
                bossMusic.setLooping(true);
                bossMusic.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void stopBossMusic() {
        if (bossMusic != null && bossMusic.isPlaying()) {
            bossMusic.stop();
            bossMusic.release();
            bossMusic = null;
        }
    }
}
