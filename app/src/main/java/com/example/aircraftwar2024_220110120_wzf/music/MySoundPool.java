package com.example.aircraftwar2024_220110120_wzf.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.aircraftwar2024_220110120_wzf.R;
import com.example.aircraftwar2024_220110120_wzf.activity.ActivityManager;

import java.util.HashMap;

public class MySoundPool {
    public static final int GAME_OVER_MUSIC_RES_ID = R.raw.game_over;
    public static final int BULLET_HIT_RES_ID = R.raw.bullet_hit;
    public static final int GET_SUPPLY_RES_ID = R.raw.get_supply;
    public static final int BOMB_EXPLOSION_RES_ID = R.raw.bomb_explosion;
    private final String musicPreference;

    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    private AudioAttributes audioAttributes = null;

    public MySoundPool(Context context, String musicPreference) {
        this.musicPreference = musicPreference;
        // 初始化 SoundPool
        audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();
        soundPoolMap = new HashMap<Integer, Integer>();

        // 加载音效到 SoundPool
        soundPoolMap.put(GAME_OVER_MUSIC_RES_ID, soundPool.load(context, GAME_OVER_MUSIC_RES_ID, 1));
        soundPoolMap.put(BULLET_HIT_RES_ID, soundPool.load(context, BULLET_HIT_RES_ID, 1));
        soundPoolMap.put(GET_SUPPLY_RES_ID, soundPool.load(context, GET_SUPPLY_RES_ID, 1));
        soundPoolMap.put(BOMB_EXPLOSION_RES_ID, soundPool.load(context, BOMB_EXPLOSION_RES_ID, 1));
    }

    public void playSound(int soundResId) {
        if(musicPreference.equals("enable")) {
            Integer soundId = soundPoolMap.get(soundResId);
            if (soundId != null) {
                soundPool.play(soundId, 1, 1, 1, 0, 1f);
            }
        }
    }

    public void playGameOverSound() {
        if(musicPreference.equals("enable")) {
            Integer soundId = soundPoolMap.get(GAME_OVER_MUSIC_RES_ID);
            if (soundId != null) {
                soundPool.play(soundId, 1, 1, 1, 0, 1f);
            }
        }
    }

    public void playBulletHitSound() {
        if(musicPreference.equals("enable")) {
            Integer soundId = soundPoolMap.get(BULLET_HIT_RES_ID);
            if (soundId != null) {
                soundPool.play(soundId, 1, 1, 1, 0, 1f);
            }
        }
    }

    public void playGetSupplySound() {
        if(musicPreference.equals("enable")) {
            Integer soundId = soundPoolMap.get(GET_SUPPLY_RES_ID);
            if (soundId != null) {
                soundPool.play(soundId, 1, 1, 1, 0, 1f);
            }
        }
    }

    public void playBombExplosionSound() {
        if(musicPreference.equals("enable")) {
            Integer soundId = soundPoolMap.get(BOMB_EXPLOSION_RES_ID);
            if (soundId != null) {
                soundPool.play(soundId, 1, 1, 1, 0, 1f);
            }
        }
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
