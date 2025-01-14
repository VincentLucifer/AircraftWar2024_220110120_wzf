package com.example.aircraftwar2024_220110120_wzf.game;

import android.content.Context;
import android.os.Handler;

import com.example.aircraftwar2024_220110120_wzf.ImageManager;
import com.example.aircraftwar2024_220110120_wzf.aircraft.AbstractEnemyAircraft;

import java.util.LinkedList;
import java.util.List;


public class EasyGame extends BaseGame{

    public EasyGame(Context context, Handler handler, String musicPreference, boolean showEnemyScore) {
        super(context,handler,musicPreference,showEnemyScore);
        this.backGround = ImageManager.BACKGROUND1_IMAGE;
        this.enemyMaxNumber = 2;
    }

    @Override
    protected void tick() {
    }

    /**
     * 简单模式没有 boss
     * @return
     */
    @Override
    protected List<AbstractEnemyAircraft> produceBoss() {
        return new LinkedList<>();
    }


}
