package com.example.aircraftwar2024_220110120_wzf.factory.enemy_factory;

import com.example.aircraftwar2024_220110120_wzf.ImageManager;
import com.example.aircraftwar2024_220110120_wzf.activity.GameActivity;
import com.example.aircraftwar2024_220110120_wzf.aircraft.AbstractEnemyAircraft;
import com.example.aircraftwar2024_220110120_wzf.aircraft.MobEnemy;

public class MobFactory implements EnemyFactory {

    private int mobHp = 30;
    private int speedY = 10;

    @Override
    public AbstractEnemyAircraft createEnemyAircraft(double level) {
        return new MobEnemy(
                (int) ( Math.random() * (GameActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * GameActivity.screenHeight * 0.05),
                0,
                (int) (this.speedY * level),
                (int) (this.mobHp * level));
    }

}
